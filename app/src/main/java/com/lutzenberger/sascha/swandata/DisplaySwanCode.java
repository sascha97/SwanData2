package com.lutzenberger.sascha.swandata;

import com.lutzenberger.sascha.activity.DataEditor;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swan.SwanData;

import java.util.List;

/**
 * The activity for displaying a single SwanData item.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class DisplaySwanCode extends DataEditor {
    private boolean inList = false;
    private List<Data> dataList;

    @Override
    protected Data getData(int fieldPosition) {
        dataList = DataFileReader.getSwanCodesList();

        //Data is already in the list...
        if(fieldPosition < dataList.size()) {
            inList = true;
            return dataList.get(fieldPosition);
        }
        //Get new data
        return SwanCodes.getEmptyData(dataList.size()); //size() - 1 is the last element
    }

    @Override
    protected void onUpdate(Data data) {
        if(inList)
            return;

        System.out.println("fieldPosition: " + data.getIndex());

        dataList.add(data.getIndex(), data);
        inList = true;
    }
}
