package com.lutzenberger.sascha.swandata;

import android.widget.Toast;

import com.lutzenberger.sascha.activity.DataListView;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;

import java.util.List;

/**
 * This is the activity for displaying a SwanCodes result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class DisplaySwanCodesList extends DataListView {
    @Override
    protected List<Data> getDataList() {
        return DataFileReader.getSwanCodesList();
    }

    @Override
    protected String getMessageNoData() {
        return getString(R.string.message_no_smaples_found);
    }

    @Override
    protected void showSingleItem(int listPosition) {
        //TODO: later
        Toast.makeText(Constants.context, "item at " + listPosition + " pressed", Toast.LENGTH_LONG).show();
    }
}
