/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing.xmlParsing;

import org.jevis.commons.parsing.GeneralMappingParser;
import org.jevis.commons.parsing.inputHandler.InputHandler;


/**
 *
 * @author bf
 */
public class DatapointFixXMLParsing implements GeneralMappingParser {

    private boolean _isInFile;
    private Long _datapoint;

    public DatapointFixXMLParsing(boolean incsv, Long datapoint) {
        _isInFile = incsv;
        _datapoint = datapoint;
    }

    @Override
    public boolean isInFile() {
        return _isInFile;
    }

    @Override
    public Long getDatapoint() {
        return _datapoint;
    }

    @Override
    public void parse(InputHandler ic) {
        //no parsing necessary
    }

    @Override
    public String getMappingValue() {
        return null; //no mapping value
    }

    @Override
    public boolean isMappingSuccessfull() {
        return true; //no mapping needed
    }
}
