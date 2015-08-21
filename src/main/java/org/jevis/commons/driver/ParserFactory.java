/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.driver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jevis.api.JEVisClass;
import org.jevis.api.JEVisDataSource;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisFile;
import org.jevis.api.JEVisObject;
import org.jevis.api.JEVisType;
import org.jevis.commons.DatabaseHelper;

/**
 *
 * @author Broder
 */
public class ParserFactory {

    private static Map<String, Class> _parserClasses = new HashMap<>();

    public static void initializeParser(JEVisDataSource client) {
        try {
            JEVisClass serviceClass = client.getJEVisClass(JEVisDriverTypes.JEDataCollector.NAME);
            JEVisClass driverDirClass = client.getJEVisClass(JEVisDriverTypes.ParserDriverDirectory.NAME);
            JEVisClass parserClass = client.getJEVisClass(JEVisDriverTypes.Driver.ParserDriver.NAME);
            List<JEVisObject> dataCollectorServices = client.getObjects(serviceClass, false);
            if (dataCollectorServices.size() == 1) {
                JEVisObject dataCollectorService = dataCollectorServices.get(0);
                List<JEVisObject> driverDirs = dataCollectorService.getChildren(driverDirClass, false);
                if (driverDirs.size() == 1) {
                    JEVisObject driverDir = driverDirs.get(0);
                    for (JEVisObject parserDriver : driverDir.getChildren(parserClass, true)) {
                        JEVisType fileType = parserDriver.getJEVisClass().getType(JEVisDriverTypes.Driver.SOURCE_FILE);
                        JEVisFile file = DatabaseHelper.getObjectAsFile(parserDriver, fileType);

                        JEVisType classType = parserDriver.getJEVisClass().getType(JEVisDriverTypes.Driver.MAIN_CLASS);
                        String className = DatabaseHelper.getObjectAsString(parserDriver, classType);

                        JEVisType jevisType = parserDriver.getJEVisClass().getType(JEVisDriverTypes.Driver.JEVIS_CLASS);
                        String jevisName = DatabaseHelper.getObjectAsString(parserDriver, jevisType);
                        Class tmpClass = ByteClassLoader.loadDriver(file, className);
                        _parserClasses.put(jevisName, tmpClass);
                    }
                }
            }
        } catch (JEVisException | MalformedURLException | ClassNotFoundException ex) {
            Logger.getLogger(ParserFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Parser getParser(JEVisObject jevisParser) {
        Parser parser = null;
        try {
            String identifier = jevisParser.getJEVisClass().getName();
            Class parserClass = _parserClasses.get(identifier);
            parser = (Parser) parserClass.newInstance();

        } catch (JEVisException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ParserFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parser;
    }

}
