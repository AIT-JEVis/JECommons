package org.jevis.commons.parsing.csvParsing;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jevis.commons.parsing.GeneralMappingParser;
import org.jevis.commons.parsing.inputHandler.InputHandler;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * The config file for a simple csv file
 *
 * @author broder
 */
public class MappingCSVParser implements GeneralMappingParser {

    private boolean _inCSV;
    private int _indexDatapoint;
    private long _datapoint;
    private boolean _mappingIsSuccessfull;
    private String _mappingValue;

    public MappingCSVParser(boolean incsv, Long datapoint, String mappingValue, Integer indexDatapoint) {
        _inCSV = incsv;
        if (indexDatapoint != null) {
            _indexDatapoint = indexDatapoint - 1;
        }
        _mappingValue = mappingValue;
        _datapoint = datapoint;
    }

    public int getDatapointIndex() {
        return _indexDatapoint;
    }

    @Override
    public boolean isInFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long getDatapoint() {
        return _datapoint;
    }

    @Override
    public void parse(InputHandler ic) {
        _mappingIsSuccessfull = false;
        String[] line = ic.getCSVInput();
        String currentMappingValue = line[_indexDatapoint];
        Logger.getLogger(this.getClass().getName()).log(Level.ALL, "##Line: " + line);
        Logger.getLogger(this.getClass().getName()).log(Level.ALL, "##currentMappingValue: " + currentMappingValue);
        Logger.getLogger(this.getClass().getName()).log(Level.ALL, "##_mappingValue: " + _mappingValue);
        if (currentMappingValue.equals(_mappingValue)) {
            _mappingIsSuccessfull = true;
        }
    }

    @Override
    public String getMappingValue() {
        return _mappingValue;
    }

    @Override
    public boolean isMappingSuccessfull() {
        return _mappingIsSuccessfull;
    }
}
