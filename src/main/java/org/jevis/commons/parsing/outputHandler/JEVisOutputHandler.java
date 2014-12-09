/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing.outputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jevis.api.JEVisDataSource;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisObject;
import org.jevis.api.JEVisSample;
import org.jevis.commons.parsing.ParsingRequest;
import org.jevis.commons.parsing.Result;
import org.jevis.commons.parsing.TimeConverter;
import org.joda.time.DateTime;

/**
 *
 * @author bf
 */
public class JEVisOutputHandler extends OutputHandler {

    @Override
    public void writeOutput(ParsingRequest request, List<Result> results) {
        JEVisDataSource client = request.getClient();
        try {
            Logger.getLogger(JEVisOutputHandler.class.getName()).log(Level.INFO, "Start JEVis OutputHandler");
//            Map<JEVisObject, List<JEVisSample>> onlineToSampleMap = new HashMap<JEVisObject, List<JEVisSample>>();

            //extract all online nodes and save them in a map
//            for (NewDataPoint dp : request.getDataPoints()) {
//                Long onlineID = dp.getOnlineID();
//                JEVisObject onlineData = Launcher.getClient().getObject(onlineID);
//                onlineToSampleMap.put(onlineData, new ArrayList<JEVisSample>());
//            }

//            JEVisClass onlineNode = online.getDataSource().getJEVisClass(OnlineData.ONLINE_DATAROW);
//            JEVisType rawAttributeType = onlineNode.getType(OnlineData.SAMPLE_ATTRIBUTE);
//            JEVisAttribute attribute = online.getAttribute(rawAttributeType);
//            List<JEVisSample> sampleList = new ArrayList<JEVisSample>();


            //look into all results and map the sample to the online node
            Map<JEVisObject, List<JEVisSample>> onlineToSampleMap = new HashMap<JEVisObject, List<JEVisSample>>();
            for (Result s : results) {
                //                DateTime time = s.getCal();
                //                System.out.println("value " + s.getVal());
                //                System.out.println("cal " + s.getCal());
                //                sampleList.add(attribute.buildSample(time, s.getVal()));
                long onlineID = s.getOnlineID();
                JEVisObject onlineData = null;
                //look for the correct jevis object
                for (JEVisObject j : onlineToSampleMap.keySet()) {
                    if (j.getID() == onlineID) {
                        onlineData = j;
                        break;
                    }
                }
                //jevis object is not in the map
                if (onlineData == null) {
                    onlineData = client.getObject(onlineID);
                    onlineToSampleMap.put(onlineData, new ArrayList<JEVisSample>());
                }
                List<JEVisSample> samples = onlineToSampleMap.get(onlineData);
                DateTime convertedDate = TimeConverter.convertTime(request.getTimezone(), s.getDate());
                JEVisSample sample = onlineData.getAttribute("Value").buildSample(convertedDate, s.getValue(), "Imported by JEVisDataCollector");
                samples.add(sample);
            }

            for (JEVisObject o : onlineToSampleMap.keySet()) {
                List<JEVisSample> samples = onlineToSampleMap.get(o);
                Logger.getLogger(JEVisOutputHandler.class.getName()).log(Level.INFO, "ID: " + o.getID());
                Logger.getLogger(JEVisOutputHandler.class.getName()).log(Level.INFO, "Number of imported Samples: " + samples.size());
                o.getAttribute("Value").addSamples(samples);
            }
        } catch (JEVisException ex) {
            Logger.getLogger(JEVisOutputHandler.class.getName()).log(Level.ERROR, null, ex);
        }
    }
}