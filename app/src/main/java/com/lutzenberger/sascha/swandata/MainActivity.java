package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lutzenberger.sascha.activity.BaseActivity;
import com.lutzenberger.sascha.custom.DialogListener;
import com.lutzenberger.sascha.custom.SaveChangesDialogFragment;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.file.DataFileWriter;
import com.lutzenberger.sascha.file.Directories;
import com.lutzenberger.sascha.settings.SettingsActivity;
import com.lutzenberger.sascha.task.FileTask;

import java.io.IOException;

public class MainActivity extends BaseActivity implements DialogListener {
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

        setToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Constants.changed) {
            SaveChangesDialogFragment saveChangesDialogFragment = new SaveChangesDialogFragment();
            saveChangesDialogFragment.show(getFragmentManager(), "save_changes");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if there is room.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        String darvic = this.darvic.getText().toString().trim();

        switch (item.getItemId()) {
            case R.id.menu_reload_files:
                reloadDataFiles();
                return true;
            case R.id.menu_new_swan_code:
                intent = new Intent(this, DisplaySwanCode.class);
                intent.putExtra(getString(R.string.intent_field_id), DataFileReader.getSwanCodesList().size());
                intent.putExtra(getString(R.string.intent_new_data), true);
                startActivity(intent);
                return true;
            case R.id.menu_new_swan_data:
                intent = new Intent(this, DisplaySwanData.class);
                intent.putExtra(getString(R.string.intent_field_id),DataFileReader.getSwanDataList().size());
                intent.putExtra(getString(R.string.intent_new_data), true);
                startActivity(intent);
                return true;
            case R.id.menu_save:
                updateDataFiles();
                return true;
            case R.id.menu_search_data:
                intent = new Intent(this, DisplaySwanDataList.class);
                intent.putExtra(getString(R.string.intent_darvic), darvic);
                startActivity(intent);
                return true;
            case R.id.menu_search_sample:
                intent = new Intent(this, DisplaySwanCodesList.class);
                intent.putExtra(getString(R.string.intent_darvic), darvic);
                startActivity(intent);
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogNegativeClick() {
    }

    @Override
    public void onDialogPositiveClick() {
        updateDataFiles();

        Constants.changed = false;
    }

    //Reloads the data files
    private void reloadDataFiles() {
        //Reload the data from the files
        new LoadingFiles().execute();
        //Reset the change to the default value
        Constants.changed = false;
    }

    //Updates the data files
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
