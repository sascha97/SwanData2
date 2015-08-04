package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.lutzenberger.sascha.activity.DataEditor;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swan.SwanData;

import java.util.List;

/**
 * The activity for displaying a single SwanData item.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class DisplaySwanData extends DataEditor {
    private boolean inList = false;
    private List<Data> dataList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if there is room.
        getMenuInflater().inflate(R.menu.menu_swan_data, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //Hide search sample if a new data is created
        MenuItem sample = menu.findItem(R.id.menu_search_sample);

        if(!inList) {
            sample.setVisible(false);
        }

        //called so that the base class can hide it as well
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This here just handles the new items of the menu which are not handled by the base class
        //already
        if(item.getItemId() == R.id.menu_search_sample){
            Intent intent = new Intent(this, DisplaySwanCodesList.class);
            intent.putExtra(getString(R.string.intent_darvic), data.getDarvic());

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Data getData(int fieldPosition) {
        dataList = DataFileReader.getSwanDataList();

        //Data is already in the list...
        if(fieldPosition < dataList.size()) {
            inList = true;
            return dataList.get(fieldPosition);
        }
        //Get new data
        return SwanData.getEmptyData(); //size() - 1 is the last element
    }

    @Override
    protected void onDelete(int index) {
        if(inList)
            dataList.remove(index);
    }

    @Override
    protected void onUpdate(Data data) {
        if(inList)
            return;

        //Set the index of the data to add
        data.setIndex(dataList.size());
        //Add the data to the data list
        dataList.add(data);
        inList = true;
    }
}
