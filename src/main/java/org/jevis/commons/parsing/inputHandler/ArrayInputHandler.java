/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing.inputHandler;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 *
 * @author Broder
 */
public class ArrayInputHandler extends InputHandler {

    public ArrayInputHandler(Object[] input) {
        super(input);
    }

    //rawInput ist Object[]
    @Override
    public void convertInput() {
        System.out.println("--Convert Array Input--");
        for (int i = 0; i < ((List) _rawInput).size(); i++) {
            StringBuilder builder = new StringBuilder();
            Object[] o = (Object[]) ((List) _rawInput).get(i);
            for (int j = 0; j < o.length; j++) {
                String s = (String) o[j];
                builder.append(s);
            }
            _inputStream.add(new ByteArrayInputStream(builder.toString().getBytes()));
        }
    }
}