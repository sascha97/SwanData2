package com.lutzenberger.sascha.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.lutzenberger.sascha.activity.AppCompatPreferenceActivity;
import com.lutzenberger.sascha.swandata.R;

import java.util.List;

/**
 * Android settings activity to show the settings
 *
 * @author Sascha Lutzenberger
 * @version 1.05 - 08.08.2015
 *
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    private List<Header> headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up toolbar for action bar.
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.pref_layout,
                null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        //Setup toolbar with back button
        Toolbar toolbar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        //Using the AppCompat way of setting up the toolbar
        setSupportActionBar(toolbar);

        //Just react when the navigation icon is clicked
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setListAdapter(ListAdapter adapter){
        //Set own list adapter so that my custom layout will be loaded
        if(adapter == null){
            super.setListAdapter(null);
        } else {
            super.setListAdapter(new PreferenceHeaderAdapter(this, headers));
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        headers = target;
        //Load the headers from the resource file
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    //isValidFragment() is required since a vulnerability was fixed
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingsFragment.class.getName().equals(fragmentName);
    }

    /**
     * Settings fragment to load the settings for the Settings Activity
     *
     * @author Sascha Lutzenberger
     * @version 1.0 - 29.07.2015
     *
     */
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SettingsActivity activity = (SettingsActivity) getActivity();
            String title = "";

            //Loading the preferences and the title
            String settings = getArguments().getString("settings");
            if(settings != null) {
                if ("general".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_general);
                    title = getString(R.string.settings_general_settings);
                } else if ("data_file".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_data_file);
                    title = getString(R.string.settings_data_file_settings);
                } else if ("swan_codes".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_codes);
                    title = getString(R.string.settings_swan_code_settings);
                } else if ("swan_data".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_data);
                    title = getString(R.string.settings_swan_data_settings);
                } else if("swan_codes_columns".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_codes_columns);
                    title = getString(R.string.settings_column_name_settings);
                } else if("swan_data_columns".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_data_columns);
                    title = getString(R.string.settings_column_name_settings);
                }
            }

            //Using the AppCompat way of updating the title
            activity.getSupportActionBar().setTitle(title);
        }
    }
}
