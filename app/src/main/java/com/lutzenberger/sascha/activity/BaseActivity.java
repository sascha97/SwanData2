package com.lutzenberger.sascha.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class builds the base class every activity in this app must implement. This app replaces
 * the action bar with the toolbar.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 06.08.2015
 *
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;

    protected void setToolbar() {
        //Set Toolbar to act as Actionbar for this Activity
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        //To avoid java.lang.NullPointerException
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
