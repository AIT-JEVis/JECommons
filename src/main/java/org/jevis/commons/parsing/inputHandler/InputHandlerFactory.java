/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing.inputHandler;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.xml.soap.SOAPMessage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Broder
 */
public class InputHandlerFactory {

    public static InputHandler getInputConverter(Object input) {
        System.out.println("class," + input.getClass().toString());
        if (input instanceof List) {
            List tmp = (List) input;
            if (tmp.isEmpty()) {
                return null;
            }
            Object o = tmp.get(0);
            if (o instanceof String) {
                return new StringInputHandler((List<String>) input);
            }

            if (o instanceof List) {
                return new StringInputHandler((List<String>) o);
            }

            if (o instanceof SOAPMessage) {
                return new SOAPMessageInputHandler((List<SOAPMessage>) input);
            }
            return null;
        }
        if (input instanceof Object[]) {
            return new ArrayInputHandler((Object[]) input);
        }
        if (input instanceof File) {
            return new FileInputHandler((File) input);
        }
        if (input instanceof InputStream) {
            Logger.getLogger(InputHandlerFactory.class.getName()).log(Level.INFO, "Inputstrema this is");
            return new InputStreamHandler((InputStream) input);
        }
        return null;


    }
}
