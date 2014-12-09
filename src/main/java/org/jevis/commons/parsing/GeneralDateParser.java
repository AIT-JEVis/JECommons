/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing;

import org.joda.time.DateTime;

/**
 *
 * @author broder
 */
public interface GeneralDateParser extends GeneralParser {

    public String getTimeFormat();

    public String getDateFormat();

    public DateTime getDateTime();
    
//    public DateTimeZone getTimeZone();
}