package com.lutzenberger.sascha.swandata;

import android.widget.TextView;
import android.widget.Toast;

import com.lutzenberger.sascha.activity.DataListView;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.task.SearchTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is the activity for displaying a SwanData result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class DisplaySwanDataList extends DataListView {
    @Override
    protected List<Data> getDataList() {
        return DataFileReader.getSwanDataList();
    }

    @Override
    protected String getMessageNoData() {
        return getString(R.string.message_no_data_found);
    }

    @Override
    protected void showSingleItem(int listPosition) {
        //TODO: later
        Toast.makeText(Constants.context, "item at " + listPosition + " pressed", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void setTextOfNumberSampled(TextView noSamples, String darvic) {
        int noOfSamples = 0;
        try {
            noOfSamples = new SearchTask<>(DataFileReader.getSwanCodesList()).
                    execute(darvic).get().size();
        } catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
        noSamples.setText(getString(R.string.message_number_of_samples, noOfSamples));
    }
}
