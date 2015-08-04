package com.lutzenberger.sascha.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lutzenberger.sascha.custom.DeleteDialogFragment;
import com.lutzenberger.sascha.custom.DialogListener;
import com.lutzenberger.sascha.custom.SwanListAdapter;
import com.lutzenberger.sascha.settings.SettingsActivity;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;
import com.lutzenberger.sascha.task.SearchTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This is the abstract activity for displaying a result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public abstract class DataListView extends ActionBarActivity implements DialogListener {
    private SwanListAdapter arrayAdapter;
    private String darvic;
    private int index;
    private TextView noSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        noSamples = (TextView) findViewById(R.id.text_number_sampled);

        //Initializing the activity
        final Intent intent = getIntent();
        //Getting the necessary data to be able to display the data
        darvic = intent.getStringExtra(getString(R.string.intent_darvic));

        //Gets the ListView
        ListView listView = (ListView) findViewById(R.id.listView);
        //The adapter used to display the data in the list
        arrayAdapter = new SwanListAdapter();

        //decides which view has to be applied based on the search
        whatToDisplay();

        //set the adapter to the list view
        listView.setAdapter(arrayAdapter);
        //allow the list view to have a context menu
        registerForContextMenu(listView);

        //If list item is clicked make sure to show the item in new activity
        //no lambdas are used because project should also be compile when using JDK 1.7
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showSingleItem(arrayAdapter.getItem(position).getIndex());
            }
        });
    }

    //Hides the TextView for number of sampled
    //If needed this has to be overwritten
    protected void setTextOfNumberSampled(TextView noSamples, String darvic){
        noSamples.setVisibility(View.GONE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Inflates the context menu from the file
        getMenuInflater().inflate(R.menu.context_menu_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //handles the event when delete is clicked
        if(item.getItemId() == R.id.menu_delete) {
            deletedClicked(item);
            return true;
        //handles the even when open is clicked
        } else if(item.getItemId() == R.id.menu_open) {
            openClicked(item);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; and add icons to the action bar.
        getMenuInflater().inflate(R.menu.menu_list_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handles the event when settings is clicked
        if(item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Add items to adapter, set up the data in the adapter again
        //Also clears the array adapter
        whatToDisplay();
    }

    @Override
    public void onDialogNegativeClick() {
        //Nothing should happen when the Cancel button of delete is clicked
    }

    @Override
    public void onDialogPositiveClick() {
        //Call the method which is deleting the item
        onDelete(index);

        //Set the changes flag
        Constants.hasChanged();

        //Immediately refresh the view because the old one is not longer valid
        whatToDisplay();
    }

    //Search the data for matches
    private List<Data> searchData() {
        List<Data> result = null;
        try{
            //Gets all the matching data in a AsyncTask so that the UI doesn't freeze
            List<Data> dataList = getDataList();
            result = new SearchTask(dataList).execute(darvic).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("ERROR", e.getMessage());
        }

        return result;
    }

    //Will be executed if user clicks on delete in the context menu
    private void deletedClicked(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Get the index of the data in the list
        index = arrayAdapter.getItem(info.position).getIndex();

        //Displays the dialog to give the user a chance to cancel the action
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(getFragmentManager(), "delete_dialog");
    }

    //Will be executed if user clicks on open in the context menu
    private void openClicked(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //Get the index of the data in the list
        index = arrayAdapter.getItem(info.position).getIndex();
        //display single data
        showSingleItem(index);
    }

    //This method takes care of what will be displayed result is the data which has to be displayed
    private void whatToDisplay() {
        //Gets the list of what has to be displayed
        List<Data> result = searchData();

        //remove all items from the adapter view
        arrayAdapter.clear();

        //If nothing has to be displayed end activity
        if(result == null || result.size() == 0){
            //Display a message on the screen if no data found and end the activity
            Toast.makeText(Constants.context, getMessageNoData(), Toast.LENGTH_LONG).show();
            finish();
            return;
        //If one item has been found display this in the right activity
        } else if(result.size() == 1) {
            //If just one data item found, there is no point in displaying a list
            //Therefore data should be displayed in the right activity
            showSingleItem(result.get(0).getIndex());
            finish();
            return;
        }

        //Refreshes the String for displaying the number of samples just in case of deletions
        setTextOfNumberSampled(noSamples, darvic);
        //Add all results to the array adapter
        arrayAdapter.addAll(result);
    }

    //Start the activity of displaying a single item
    private void showSingleItem(int listIndex){
        //Gets the right intent do display the data into
        Intent intent = prepareIntent();

        //gives the intent the required information
        intent.putExtra(getString(R.string.intent_field_id), listIndex);
        intent.putExtra(getString(R.string.intent_new_data), false);

        startActivity(intent);
    }

    /**
     * This method is used to get a data list which has to be searched by a darvic code
     *
     * @return A data list with entries to search
     */
    protected abstract List<Data> getDataList();

    /**
     * Returns a String with the message which should be displayed when no data is found.
     *
     * @return The string to display when no data is found.
     */
    protected abstract String getMessageNoData();

    /**
     * This method is here to get the relevant Intent when a activity should be started
     */
    protected abstract Intent prepareIntent();

    /**
     * This method is used to delete data from the list.
     * Activity will be ended with finish() afterwards.
     *
     * @param index The index at which data should be deleted
     */
    protected abstract void onDelete(int index);
}
