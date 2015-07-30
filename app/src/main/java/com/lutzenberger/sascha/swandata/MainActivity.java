package com.lutzenberger.sascha.swandata;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.lutzenberger.sascha.settings.SettingsActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.context = this;

        startActivity(new Intent(this, SettingsActivity.class));
    }
}
