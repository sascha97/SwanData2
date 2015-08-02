package com.lutzenberger.sascha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lutzenberger.sascha.custom.SwanListAdapter;
import com.lutzenberger.sascha.settings.SettingsActivity;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;

import java.util.List;

/**
 * This is the abstract activity for displaying a result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public abstract class DataListView extends ActionBarActivity {
    private SwanListAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        //Initializing the activity
        final Intent intent = getIntent();
        //Getting the necessary data to be able to display the data
        String darvic = intent.getStringExtra(getString(R.string.darvic));

        //Gets the ListView
        ListView listView = (ListView) findViewById(R.id.listView);

        List<Data> result = getMatchingData(darvic);

        if(result == null || result.size() == 0){
            //Display a message on the screen if no data found and end the activity
            Toast.makeText(Constants.context, getMessageNoData(), Toast.LENGTH_LONG).show();
            finish();
        } else if(result.size() == 1) {
            //If just one data item found, there is no point in displaying a list
            //Therefore data should be displayed in the right activity
            showSingleItem(result.get(0).getIndex());
            finish();
        }

        //The adapter used to display the data in the list
        arrayAdapter = new SwanListAdapter();
        arrayAdapter.addAll(result);

        listView.setAdapter(arrayAdapter);

        //If list item is clicked make sure to show the item in new activity
        //no lambdas are used because project should also be compile when using JDK 1.7
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSingleItem(arrayAdapter.getItem(position).getIndex());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; and add icons to the action bar.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Update the List just in case anything has changed
        arrayAdapter.notifyDataSetChanged();
    }

    /**
     * This method is used to get the matching data to a DARVIC code
     *
     * @param darvic The darvic code
     *
     * @return A list with all data matching the darvic code
     */
    protected abstract List<Data> getMatchingData(String darvic);

    /**
     * Returns a String with the message which should be displayed when no data is found.
     *
     * @return The string to display when no data is found.
     */
    protected abstract String getMessageNoData();

    /**
     * This method is here to start a new activity when just one result has to be displayed.
     *
     * @param listPosition The list position of the data item
     */
    protected abstract void showSingleItem(int listPosition);
}
