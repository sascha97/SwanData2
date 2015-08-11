package com.lutzenberger.sascha.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lutzenberger.sascha.activity.BaseActivity;
import com.lutzenberger.sascha.custom.dialog.DialogListener;
import com.lutzenberger.sascha.custom.dialog.ExportSettingsDialogFragment;
import com.lutzenberger.sascha.custom.dialog.ImportSettingsDialogFragment;
import com.lutzenberger.sascha.custom.dialog.ResetSettingsDialogFragment;
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
                ExportSettingsDialogFragment exportSettingsDialogFragment = new
                        ExportSettingsDialogFragment();
                exportSettingsDialogFragment.addDialogListener(exportSettingsListener);
                exportSettingsDialogFragment.show(getFragmentManager(), "export_settings");
            }
        });
        importSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportSettingsDialogFragment importSettingsDialogFragment = new
                        ImportSettingsDialogFragment();
                importSettingsDialogFragment.addDialogListener(importSettingsListener);
                importSettingsDialogFragment.show(getFragmentManager(), "import_settings");
            }
        });
        resetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetSettingsDialogFragment resetSettingsDialogFragment = new
                        ResetSettingsDialogFragment();
                resetSettingsDialogFragment.addDialogListener(resetSettingsListener);
                resetSettingsDialogFragment.show(getFragmentManager(), "reset_settings");
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

    private DialogListener exportSettingsListener = new DialogListener() {
        @Override
        public void onDialogPositiveClick() {
            PreferenceFileUtils.exportSharedPreferences();
        }

        @Override
        public void onDialogNegativeClick() {
        }
    };

    private DialogListener importSettingsListener = new DialogListener() {
        @Override
        public void onDialogPositiveClick() {
            PreferenceFileUtils.importSharedPreferences();
        }

        @Override
        public void onDialogNegativeClick() {
        }
    };

    private DialogListener resetSettingsListener = new DialogListener() {
        @Override
        public void onDialogPositiveClick() {
            PreferenceFileUtils.resetDefaultSettings();
        }

        @Override
        public void onDialogNegativeClick() {
        }
    };
}
