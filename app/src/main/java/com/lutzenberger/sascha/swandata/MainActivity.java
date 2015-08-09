package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lutzenberger.sascha.activity.BaseActivity;
import com.lutzenberger.sascha.custom.dialog.DialogListener;
import com.lutzenberger.sascha.custom.dialog.SaveChangesDialogFragment;
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

        //If the data files have changed display a dialog and ask the user what to do
        if(Constants.isChanged()) {
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
            //If user clicks on the button for reloading the files reload them
            case R.id.menu_reload_files:
                reloadDataFiles();
                return true;
            //if user wants to add a new swan code start the activity to do so
            case R.id.menu_new_swan_code:
                intent = new Intent(this, SwanCodeEditorActivity.class);
                intent.putExtra(getString(R.string.intent_field_id),
                        DataFileReader.getSwanCodesList().size());
                intent.putExtra(getString(R.string.intent_new_data), true);
                startActivity(intent);
                return true;
            //if user wants to add new swan data start the activity to do so
            case R.id.menu_new_swan_data:
                intent = new Intent(this, SwanDataEditorActivity.class);
                intent.putExtra(getString(R.string.intent_field_id),
                        DataFileReader.getSwanDataList().size());
                intent.putExtra(getString(R.string.intent_new_data), true);
                startActivity(intent);
                return true;
            //This saves the data loaded from the files to the data files again
            case R.id.menu_save:
                updateDataFiles();
                return true;
            //search the data, if user clicks on the search SwanData button on the toolbar
            case R.id.menu_search_data:
                intent = new Intent(this, SwanDataListActivity.class);
                intent.putExtra(getString(R.string.intent_darvic), darvic);
                startActivity(intent);
                return true;
            //search the data, if user clicks on the search SwanCodes button on the toolbar
            case R.id.menu_search_sample:
                intent = new Intent(this, SwanCodeListActivity.class);
                intent.putExtra(getString(R.string.intent_darvic), darvic);
                startActivity(intent);
                return true;
            //Open the settings menu in a new activity
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
        //Write the data to the data files
        updateDataFiles();
        //No changes are made anymore
        Constants.resetChanged();
    }

    //Reloads the data files
    private void reloadDataFiles() {
        //Reload the data from the files
        new LoadingFiles().execute();
        //Reset the change to the default value
        Constants.resetChanged();
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

        @Override
        protected String getMessage() {
            return getString(R.string.message_files_updated);
        }

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

        @Override
        protected String getMessage() {
            return getString(R.string.message_files_processed);
        }

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
