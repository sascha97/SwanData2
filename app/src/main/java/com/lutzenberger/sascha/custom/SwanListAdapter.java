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
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;

/**
 * Own list adapter to match the data to a list view
 *
 * @author Sascha Lutzenberger
 * @version 1.1 - 09.08.2015
 *
 */
public class SwanListAdapter extends ArrayAdapter<Data> {
    private final LayoutInflater inflater;
    private final SharedPreferences pref;

    public SwanListAdapter() {
        super(Constants.context, 0);
        inflater = (LayoutInflater) Constants.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        //Get the preferences to load the preferences later
        pref = PreferenceManager.getDefaultSharedPreferences(Constants.context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //The ViewHolder which is holding all the UI elements
        ViewHolder holder;

        //If no View exists load it from the file
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_swan_list_item, parent, false);

            //Set up the ViewHolder
            holder = new ViewHolder();
            //Initializing the TextViews
            holder.heading = (TextView) convertView.findViewById(R.id.list_heading);
            holder.content = (TextView) convertView.findViewById(R.id.list_content);
            //Set the holder to the convert view
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Get the ListItem
        Data data = getItem(position);

        //if true only nonEmpty fields will be shown
        boolean showNonEmpty = pref.getBoolean("show_non_empty", true);

        //Set the string to display as an empty string
        String result = "";
        //if a previous item has been added to result
        boolean next = false;
        //Iterate over all attributes data has
        for(int i=0;i<data.getNumberOfAttributes();i++){
            //get the internal used attribute name, to load its settings
            String attributeName = data.getAttributeNameAt(i);
            //get the String what name is to display on the application based on the attribute name
            String attributeHeader = pref.getString("name_" + attributeName, "");
            //get the setting whether the attribute will be shown or not based on the attribute name
            boolean visible = pref.getBoolean("show_" + attributeName, true);
            //when visible look what has to be displayed
            if(visible){
                //if hide empty values is enabled check if value is empty
                if(showNonEmpty) {
                    //if value is empty go to next attribute
                    if (data.getDataAt(i).isEmpty())
                        continue;
                }
                //Adds the delimiter
                if(next)
                    result += "\n";
                //Adds the result
                result = result + attributeHeader + "='"+data.getDataAt(i)+"'";
                //the delimiter will be needed next time
                next = true;
            }
        }

        //If no data is to display, display this message
        if(result.isEmpty())
            result = Constants.context.getString(R.string.message_no_displayable_data);

        //Setting the heading and the content to the ListItem
        holder.heading.setText(data.getSimpleName() + " " + (position + 1));
        holder.content.setText(result);

        return convertView;
    }

    //This is recommended by the android API guides to reduce the calls of findViewById()
    private static class ViewHolder {
        private TextView heading; //Is used to display a "heading" on the ListItem
        private TextView content; //Is used o display the "content" on the ListItem
    }
}
