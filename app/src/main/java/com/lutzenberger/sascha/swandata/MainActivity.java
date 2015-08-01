package com.lutzenberger.sascha.swandata;

import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

import com.lutzenberger.sascha.file.CSVReader;
import com.lutzenberger.sascha.file.Directories;
import com.lutzenberger.sascha.task.FileTask;


import java.io.IOException;

public class MainActivity extends ActionBarActivity {

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if there is room.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                CSVReader.getSwanDataList(fileDirectory + "/" + swanDataFileName);
            } catch (IOException e) {
                errorOccurred();
            }

            try {
                //SwanCodes file needs to have the name declared in the settings
                CSVReader.getSwanCodesList(fileDirectory + "/" + swanCodesFileName);
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
