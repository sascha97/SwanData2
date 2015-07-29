package com.lutzenberger.sascha.settings;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lutzenberger.sascha.swandata.R;

import java.util.List;

/**
 * Android settings activity to show the settings
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 29.07.2015
 *
 */
public class SettingsActivity extends PreferenceActivity {
    private Toolbar mToolbar;
    private List<Header> headers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        View content = root.getChildAt(0);
        LinearLayout toolbarContainer = (LinearLayout) View.inflate(this, R.layout.pref_layout,
                null);

        root.removeAllViews();
        toolbarContainer.addView(content);
        root.addView(toolbarContainer);

        mToolbar = (Toolbar) toolbarContainer.findViewById(R.id.toolbar);
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setListAdapter(ListAdapter adapter){
        if(adapter == null){
            super.setListAdapter(null);
        } else {
            super.setListAdapter(new PreferenceHeaderAdapter(this, headers));
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        this.headers = target;
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

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

            //Loading the preferences
            String settings = getArguments().getString("settings");
            if(settings != null) {
                if ("general".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_general);
                    title = "General Settings";
                } else if ("data_file".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_data_file);
                    title = "DataFile Settings";
                } else if ("swan_codes".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_codes);
                    title = "SwanCodes Settings";
                } else if ("swan_data".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_data);
                    title = "SwanData Settings";
                } else if("swan_codes_columns".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_codes_columns);
                    title = "SwanCodes Columns Settings";
                } else if("swan_data_columns".equals(settings)) {
                    addPreferencesFromResource(R.xml.pref_swan_data_columns);
                    title = "SwanData Columns Settings";
                }
            }

            activity.mToolbar.setTitle(title);
        }
    }
}
