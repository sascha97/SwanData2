package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lutzenberger.sascha.settings.SettingsActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SetUp the Basic Application needs
        Constants.context = this;
        loadInPreferencesDefaultValues();


        Button setttings = (Button) findViewById(R.id.settings_button);
        Button test = (Button) findViewById(R.id.test);

        setttings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestActivity.class));
            }
        });
    }

    private void loadInPreferencesDefaultValues(){
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_data_file, false);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_codes, false);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_codes_columns, false);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_data, false);
        PreferenceManager.setDefaultValues(Constants.context, R.xml.pref_swan_data_columns, false);
    }
}
