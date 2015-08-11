package com.lutzenberger.sascha.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lutzenberger.sascha.activity.BaseActivity;
import com.lutzenberger.sascha.file.PreferenceFileUtils;
import com.lutzenberger.sascha.swandata.R;

/**
 * This is the activity letting the user select what to do with the preferences.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 11.08.2015
 */
public class SaveSettingsActivity extends BaseActivity {
    private Button exportSettings;
    private Button importSettings;
    private Button resetSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_settings);

        exportSettings = (Button) findViewById(R.id.button_export_settings);
        importSettings = (Button) findViewById(R.id.button_import_settings);
        resetSettings = (Button) findViewById(R.id.button_reset_settings);

        exportSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceFileUtils.exportSharedPreferences();
            }
        });
        importSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceFileUtils.importSharedPreferences();
            }
        });
        resetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceFileUtils.resetDefaultSettings();
            }
        });

        setToolbar();

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
