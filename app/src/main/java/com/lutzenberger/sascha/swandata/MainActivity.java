package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lutzenberger.sascha.activity.DataEditor;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.file.DataFileWriter;
import com.lutzenberger.sascha.file.Directories;
import com.lutzenberger.sascha.settings.SettingsActivity;
import com.lutzenberger.sascha.task.FileTask;


import java.io.IOException;

public class MainActivity extends ActionBarActivity {
    private EditText darvic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SetUp the Basic Application needs
        Constants.context = this;
        //Load in all preferences
        loadInPreferencesDefaultValues();
        //Set up the directories
        Directories.setUpIfNotExistent();
        //Loading in the files
        LoadingFiles loadingFiles = new LoadingFiles();
        loadingFiles.execute();

        darvic = (EditText) findViewById(R.id.darvic_entered);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if there is room.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reload_files:
                reloadDataFiles();
                return true;
            case R.id.menu_new_swan_code:
                //TODO: action
                return true;
            case R.id.menu_new_swan_data:
                //TODO: action
                return true;
            case R.id.menu_save:
                updateDataFiles();
                return true;
            case R.id.menu_search_data:
                //TODO: action
                return true;
            case R.id.menu_search_sample:
                Intent intent = new Intent(this, DisplaySwanCodesList.class);
                String darvic = this.darvic.getText().toString().trim();
                intent.putExtra(getString(R.string.darvic), darvic);
                startActivity(intent);
                //TODO: action
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void reloadDataFiles() {
        new LoadingFiles().execute();
    }

    private void updateDataFiles() {
        new UpdatingFiles().execute();
    }

    //Updating the files, do it in another Task in the background just in case it could take some
    //Time and it needs to be done in the background to make sure it doesn't freeze the UI
    private class UpdatingFiles extends FileTask {
        //The actual updating is done in here, arguments are being ignored here
        @Override
        protected Void doInBackground(Void[] params) {
            //Is split into two try and catch statements to make sure attempt to write both files is
            //made
            try {
                //SwanData file needs to have the name declared in the settings
                DataFileWriter.updateSwanDataFile(fileDirectory + "/" + swanDataFileName);
            } catch (IOException e) {
                errorOccurred();
            }

            try{
                //SwanCodes file needs to have the name declared in the settings
                DataFileWriter.updateSwanCodesFile(fileDirectory + "/" + swanCodesFileName);
            } catch (IOException e){
                errorOccurred();
            }

            //No return needed
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getMessage() {
            return getString(R.string.message_files_updated);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getMessage(boolean successful) {
            if(successful)
                return getString(R.string.message_update_success);
            return getString(R.string.message_update_failed);
        }
    }

    //Loads the files in, do it in another Task in the background just in case it could take some
    //Time and it needs to be done in the background to make sure it doesn't freeze the UI
    private class LoadingFiles extends FileTask {
        //The actual reading in is done in here, arguments are being ignored here
        @Override
        protected Void doInBackground(Void[] params) {
            //Is split into two try and catch statements to make sure both lists are initialized
            try {
                //SwanData file needs to have the name declared in the settings
                DataFileReader.getSwanDataList(fileDirectory + "/" + swanDataFileName);
            } catch (IOException e) {
                errorOccurred();
            }

            try {
                //SwanCodes file needs to have the name declared in the settings
                DataFileReader.getSwanCodesList(fileDirectory + "/" + swanCodesFileName);
            } catch (IOException e){
                errorOccurred();
            }

            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getMessage() {
            return getString(R.string.message_files_processed);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected String getMessage(boolean successful){
            if(successful)
                return getString(R.string.message_read_success);
            return getString(R.string.message_read_failed);
        }
    }

    private void loadInPreferencesDefaultValues(){
        //Load in the default Settings, this does not reset preferences back to their default value
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_data_file, true);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_general, true);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_codes, true);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_codes_columns, true);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_data, true);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_data_columns, true);
    }
}
