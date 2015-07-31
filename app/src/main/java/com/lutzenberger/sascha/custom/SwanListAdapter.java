package com.lutzenberger.sascha.custom;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swan.SwanCodes;
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;

/**
 * Own list adapter to match the data to a list view
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 31.07.2015
 *
 */
public class SwanListAdapter extends ArrayAdapter<Data> {
    private final LayoutInflater inflater;

    public SwanListAdapter() {
        super(Constants.context, 0);
        this.inflater = (LayoutInflater) Constants.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView heading; //Is used to display a "heading" on the ListItem
        TextView content; //Is used o display the "content" on the ListItem

        //If no View exists load it from the file
        if(convertView == null)
            convertView = inflater.inflate(R.layout.layout_swan_list_item, parent, false);

        //Initialising the TextViews
        heading = (TextView) convertView.findViewById(R.id.list_heading);
        content = (TextView) convertView.findViewById(R.id.list_content);

        //Get the ListItem
        Data data = getItem(position);

        //Get the preferences to load the preferences later
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(Constants.context);
        //if true only nonEmpty fields will be shown
        boolean showNonEmpty = pref.getBoolean("show_non_empty", true);

        String result = "";
        boolean next = false;
        for(int i=0;i<data.getNumberOfAttribues();i++){
            String attributeName = data.getAttributeNameAt(i);
            String attribute_header = pref.getString("name_" + attributeName, "");
            boolean visible = pref.getBoolean("show_" + attributeName, true);
            if(visible){
                if(showNonEmpty){
                    if(data.getDataAt(i).isEmpty())
                        continue;
                    //Adds the delimiter
                    if(next)
                        result = result + "\n";
                    //Adds the result
                    result = result + attribute_header + "='"+data.getDataAt(i)+"'";
                    next = true;
                }
            }
        }

        //If no data is to display, display this message
        if(result.isEmpty())
            result = "No displayable data";

        //Setting the heading and the content to the ListItem
        heading.setText(data.getSimpleName() + " " + (position + 1));
        content.setText(result);

        return convertView;
    }
}
