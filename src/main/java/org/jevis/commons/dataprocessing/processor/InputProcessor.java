/**
 * Copyright (C) 2015 Envidatec GmbH <info@envidatec.com>
 *
 * This file is part of JECommons.
 *
 * JECommons is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation in version 3.
 *
 * JECommons is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * JECommons. If not, see <http://www.gnu.org/licenses/>.
 *
 * JECommons is part of the OpenJEVis project, further project information are
 * published at <http://www.OpenJEVis.org/>.
 */
package org.jevis.commons.dataprocessing.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jevis.api.JEVisAttribute;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisObject;
import org.jevis.api.JEVisSample;
import org.jevis.commons.dataprocessing.DataProcessor;
import org.jevis.commons.dataprocessing.Options;
import org.jevis.commons.dataprocessing.Task;
import org.joda.time.DateTime;

/**
 *
 * @author Florian Simon <florian.simon@envidatec.com>
 */
public class InputProcessor implements DataProcessor {

    public final static String NAME = "Input";

    public final static String OBJECT_ID = "object-id";
    public final static String ATTRIBUTE_ID = "attribute-id";
    private List<JEVisSample> _result = null;

    @Override

    public List<JEVisSample> getResult(Task task) {
        if (_result != null) {
            return _result;
        } else {
            _result = new ArrayList<>();

            if (task.getOptions().containsKey(OBJECT_ID) && task.getOptions().containsKey(ATTRIBUTE_ID)) {

                try {
                    long oid = Long.valueOf(task.getOptions().get(OBJECT_ID));
                    JEVisObject object = task.getJEVisDataSource().getObject(oid);
                    JEVisAttribute att = object.getAttribute(task.getOptions().get(ATTRIBUTE_ID));

                    DateTime start = null;
                    DateTime end = null;
                    DateTime[] startEnd = Options.getStartAndEnd(task);
                    System.out.println("start: " + start + " end: " + end);

                    _result = att.getSamples(startEnd[0], startEnd[1]);
                    System.out.println("Input result: " + _result.size());
                } catch (JEVisException ex) {
                    Logger.getLogger(InputProcessor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Missing options " + OBJECT_ID + " and " + ATTRIBUTE_ID);
            }
        }
        return _result;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
