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
        MenuItem sample = menu.findItem(R.id.menu_search_sample);

        if(!inList) {
            sample.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        dataList.remove(index);
    }

    @Override
    protected void onUpdate(Data data) {
        if(inList)
            return;

        dataList.add(data);
        inList = true;
    }
}
