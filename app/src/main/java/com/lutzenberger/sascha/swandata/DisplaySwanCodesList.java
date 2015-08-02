package com.lutzenberger.sascha.swandata;

import android.util.Log;

import com.lutzenberger.sascha.activity.DataListView;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.task.SearchTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is the activity for displaying a SwanCodes result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class DisplaySwanCodesList extends DataListView {
    @Override
    protected List<Data> getMatchingData(String darvic) {
        List<Data> resultList = null;
        try{
            //Gets all the matching data in a AsyncTask so that the UI doesn't freeze
            List<Data> dataList = DataFileReader.getSwanCodesList();
            resultList = new SearchTask<>(dataList).execute(darvic).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("ERROR", e.getMessage());
        }

        return resultList;
    }

    @Override
    protected String getMessageNoData() {
        return getString(R.string.message_no_smaples_found);
    }

    @Override
    protected void showSingleItem(int listPosition) {
        //TODO: later
    }
}
