package com.lutzenberger.sascha.settings;

import android.content.Context;
import android.preference.PreferenceActivity.Header;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lutzenberger.sascha.swandata.R;

import java.util.List;

/**
 * Own list adapter to match the headers to the view, with a custom layout for the header
 *
 * @author Sascha Lutzenberger
 * @version 1.01 - 08.08.2015
 */
class PreferenceHeaderAdapter extends ArrayAdapter<Header> {
    //The layout inflater to inflate the layout
    private final LayoutInflater inflater;

    public PreferenceHeaderAdapter(Context context, List<Header> objects) {
        super(context, 0, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView title; //The title of the header
        TextView summary; //The content of the header

        //Loads the layout
        if(convertView == null)
            convertView = inflater.inflate(R.layout.pref_header_item_layout, parent, false);

        //Initializes the components
        title = (TextView) convertView.findViewById(android.R.id.title);
        summary = (TextView) convertView.findViewById(android.R.id.summary);

        //Get the data
        Header header = getItem(position);

        //Sets the data to the components
        title.setText(header.getTitle(getContext().getResources()));
        summary.setText(header.getSummary(getContext().getResources()));

        return convertView;
    }
}
