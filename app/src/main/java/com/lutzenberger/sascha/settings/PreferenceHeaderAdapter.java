package com.lutzenberger.sascha.settings;

import android.content.Context;
import android.preference.PreferenceActivity.Header;
import android.support.annotation.NonNull;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //The ViewHolder which is holding all the UI elements
        ViewHolder holder;
        View view;

        //If no View exists load it from the file
        if(convertView == null) {
            view = inflater.inflate(R.layout.pref_header_item_layout, parent, false);
            //Set up the ViewHolder
            holder = new ViewHolder();

            //Initializes the TextViews
            holder.title = (TextView) view.findViewById(android.R.id.title);
            holder.summary = (TextView) view.findViewById(android.R.id.summary);

            //Set the view holder to the view
            view.setTag(holder);
        } else {
            view = convertView;
            //Get the ViewHolder from the View
            holder = (ViewHolder) view.getTag();
        }

        //Get the data
        Header header = getItem(position);

        //Sets the data to the components
        holder.title.setText(header.getTitle(getContext().getResources()));
        holder.summary.setText(header.getSummary(getContext().getResources()));

        return view;
    }

    private static class ViewHolder {
        private TextView title; //The title of the header
        private TextView summary; //The content of the header
    }
}
