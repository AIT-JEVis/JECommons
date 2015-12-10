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
package org.jevis.commons.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jevis.api.JEVisClass;
import org.jevis.api.JEVisDataSource;
import org.jevis.api.JEVisException;
import org.jevis.api.JEVisObject;
import org.jevis.api.JEVisSample;
import org.jevis.api.JEVisType;
import org.jevis.commons.DatabaseHelper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 *
 * @author bf
 */
public class JEVisImporter implements Importer {

    private JEVisDataSource _client = null;
    private JEVisObject httpObject;
    private DateTimeZone _timezone;
    private DateTime _latestDateTime;

    @Override
    public synchronized boolean importResult(List<Result> results) {
        try {
            Logger.getLogger(JEVisImporter.class.getName()).log(Level.INFO, "Number of imported Data overall: " + results.size());
            if (results.isEmpty()) {
                return false;
            }

            //look into all results and map the sample to the online node
            Map<JEVisObject, List<JEVisSample>> onlineToSampleMap = new HashMap<JEVisObject, List<JEVisSample>>();
            for (Result s : results) {
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
                    onlineData = _client.getObject(onlineID);
                    onlineToSampleMap.put(onlineData, new ArrayList<JEVisSample>());
                }
                List<JEVisSample> samples = onlineToSampleMap.get(onlineData);
                DateTime convertedDate = TimeConverter.convertTime(_timezone, s.getDate());
                JEVisSample sample = onlineData.getAttribute("Value").buildSample(convertedDate, s.getValue(), "");
                samples.add(sample);
            }

            for (JEVisObject o : onlineToSampleMap.keySet()) {
                List<JEVisSample> samples = onlineToSampleMap.get(o);
                Logger.getLogger(JEVisImporter.class.getName()).log(Level.INFO, samples.size() + " Samples import into ID");
                o.getAttribute("Value").addSamples(samples);
                if (_latestDateTime == null || o.getAttribute("Value").getLatestSample().getTimestamp().isBefore(_latestDateTime)) {
                    _latestDateTime = o.getAttribute("Value").getLatestSample().getTimestamp();
                }
                Logger.getLogger(JEVisImporter.class.getName()).log(Level.INFO, samples.size() + " Samples imported into ID");
            }
            Logger.getLogger(JEVisImporter.class.getName()).log(Level.INFO, " Finish imported into ID");
        } catch (JEVisException ex) {
            Logger.getLogger(JEVisImporter.class.getName()).log(Level.ERROR, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public void initialize(JEVisObject dataSource) {
        try {
            _client = dataSource.getDataSource();
            JEVisClass dataSourceClass = _client.getJEVisClass(DataCollectorTypes.DataSource.NAME);
            JEVisType timezoneType = dataSourceClass.getType(DataCollectorTypes.DataSource.TIMEZONE);
            String timezone = DatabaseHelper.getObjectAsString(dataSource, timezoneType);
            _timezone = DateTimeZone.forID(timezone);
        } catch (JEVisException ex) {
            java.util.logging.Logger.getLogger(JEVisImporter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public DateTime getLatestDatapoint() {
        return _latestDateTime;
    }

}
