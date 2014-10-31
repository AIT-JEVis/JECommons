/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jevis.commons.parsing.csvParsing;

import org.jevis.commons.parsing.GeneralMappingParser;
import org.jevis.commons.parsing.inputHandler.InputHandler;

/**
 *
 * @author broder
 */
public class MappingFixCSVParser implements GeneralMappingParser {

    private boolean _inFile;
    private int _index;
    private Long _datapoint;

    public MappingFixCSVParser(boolean incsv, Long datapoint) {
        _inFile = incsv;
        _datapoint = datapoint;
    }

    public int getDatapointIndex() {
        return _index;
    }

    @Override
    public boolean isInFile() {
        return _inFile;
    }

    @Override
    public Long getDatapoint() {
        return _datapoint;
    }

    @Override
    public void parse(InputHandler ic) {
        // no parsing necessary
    }

    @Override
    public String getMappingValue() {
        return null; //has no mapping value, cause the onlineId is fix
    }

    @Override
    public boolean isMappingSuccessfull() {
        return true;
    }

    @Override
    public boolean isValueValid() {
        return true;
    }
}
