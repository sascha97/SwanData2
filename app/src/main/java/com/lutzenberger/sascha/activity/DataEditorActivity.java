package com.lutzenberger.sascha.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lutzenberger.sascha.custom.dialog.DeleteDialogFragment;
import com.lutzenberger.sascha.custom.dialog.DialogListener;
import com.lutzenberger.sascha.settings.SettingsActivity;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;

/**
 * This class builds an abstract basis for displaying the Data for the data files.
 *
 * This class already handles the display and updating of the data entry.
 *
 * @author Sascha Lutzenberger
 * @version 1.1 - 09.08.2015
 *
 */
public abstract class DataEditorActivity extends BaseActivity {
    private LayoutInflater inflater;
    private SharedPreferences pref;
    private boolean newData;

    protected Data data;
    private boolean hiddenEmpty;
    private View[] views;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_editor);

        //Initializing the activity
        final Intent intent = getIntent();
        //Getting the necessary data to be able to tell which data should be displayed
        int fieldPosition = intent.getIntExtra(getString(R.string.intent_field_id), 0);
        newData = intent.getBooleanExtra(getString(R.string.intent_new_data), false);
        //Setting up the inflater for loading layouts later
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Load the preferences
        pref = PreferenceManager.getDefaultSharedPreferences(Constants.context);

        //Get the layout from the root view
        LinearLayout layout = (LinearLayout) findViewById(R.id.editor);

        //Set the data
        data = getData(fieldPosition);
        //gets the boolean flag to indicate if empty data should be hidden
        hiddenEmpty = getHiddenEmpty();

        //initialize the view array, so that just enough views are stored here
        views = new View[data.getNumberOfAttributes()];
        for(int i=0;i<data.getNumberOfAttributes();i++) {
            //Gets the created view
            View view = getView(i);
            //Adds the view to the root element
            layout.addView(view);
            //Adds the view to the array views
            views[i] = view;
        }
        //Setup message view if no data will be displayed
        message = new TextView(this);
        message.setText(getString(R.string.message_change_settings_to_display));
        //Add the view to the root element
        layout.addView(message);

        //refresh the view, this only changes visibility and the texts to be displayed
        refreshView();

        setToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
        //refresh the view, if resumed to make sure to respond to any changes in visibility
        refreshView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if there is room.
        getMenuInflater().inflate(R.menu.menu_data_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //Get menu items which are not necessary to be displayed in a edit view
        MenuItem delete = menu.findItem(R.id.menu_delete);
        MenuItem edit = menu.findItem(R.id.menu_edit);
        MenuItem settings = menu.findItem(R.id.menu_settings);

        //If in edit view, hide those menu items
        if(newData) {
            delete.setVisible(false);
            edit.setVisible(false);
            settings.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_save:
                saveButtonClicked();
                return true;
            case R.id.menu_edit:
                editClicked(item);
                return true;
            case R.id.menu_delete:
                deleteClicked();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is used to delete data from the list.
     * Activity will be ended with finish() afterwards.
     *
     * @param index The index at which data should be deleted
     */
    protected abstract void onDelete(int index);

    /**
     * This method is used to pass in the data item which has to be displayed.
     *
     * @return The data item which has to be displayed
     */
    @NonNull
    protected abstract Data getData(int fieldPosition);

    /**
     * This method is here that the activity can specify what happens if the
     * update is being executed. It is only called if update is valid.
     *
     * Is used for adding the new data to the list.
     */
    protected void onUpdate(@NonNull Data data){
    }

    private DialogListener deleteDialogListener = new DialogListener() {
        @Override
        public void onDialogNegativeClick() {
        }

        @Override
        public void onDialogPositiveClick() {
            onDelete(data.getIndex());
            Toast.makeText(Constants.context, Constants.context.getString(R.string.message_deleted_data),
                    Toast.LENGTH_LONG).show();
            //Set the flag that something has changed
            Constants.hasChanged();
            finish();
        }
    };

    //Used to decide if hidden elements have to be shown
    private boolean getHiddenEmpty(){
        return !newData && pref.getBoolean("show_non_empty", true);
    }

    //This method handles when delete is clicked
    private void deleteClicked() {
        //Displays the delete dialog, the handling of the actions is done over an interface
        //which is already implemented in this activity.
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.addDialogListener(deleteDialogListener);
        deleteDialogFragment.show(getFragmentManager(), "delete_dialog");
    }

    //This method handles when edit is clicked
    private void editClicked(MenuItem item) {
        newData = true;
        //Hide the edit button afterwards
        item.setVisible(false);

        refreshView();
    }

    //This menu creates a view for an data entry
    private View getView(int index) {
        //Inflates the view from its layout file (suppresses warning)
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.layout_display_data_item, null);

        //Creates a new ViewHolder to hold all the elements of the UI
        ViewHolder holder = new ViewHolder();
        holder.header = (TextView) view.findViewById(R.id.data_item_title);
        holder.content = (EditText) view.findViewById(R.id.data_item_text);

        String attributeName = data.getAttributeNameAt(index);
        String data = this.data.getDataAt(index).trim();

        if (attributeName.contains("comment")) {
            holder.content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            holder.content.setMaxLines(5);
        }

        holder.content.setText(data);
        view.setTag(holder);

        return view;
    }

    private void refreshView(){
        //Redraw the options menu
        invalidateOptionsMenu();

        //refreshes hiddenEmpty
        hiddenEmpty = getHiddenEmpty();
        //Set oneItemDisplayed to false because at the moment no item will be shown
        boolean oneItemDisplayed = false;

        //Iterate through all the views
        for(int i=0;i<views.length;i++){
            //Load the ViewHolder which holds all UI elements
            ViewHolder holder = (ViewHolder) views[i].getTag();

            //Get the attribute name of the column
            String attributeName = data.getAttributeNameAt(i);
            //Checks if this column is visible
            boolean visible = pref.getBoolean("show_" + attributeName, true);

            //Loads the name for the attribute which should be displayed as heading
            String headerText = pref.getString("name_" + attributeName,
                    getString(R.string.message_column_header_not_defined));
            //Set the heading
            holder.header.setText(headerText);

            //Set all items visible
            views[i].setVisibility(View.VISIBLE);

            //If empty data should be hidden
            if(hiddenEmpty) {
                //checks if data is empty
                if(getTrimmedString(holder.content).isEmpty()) {
                    //hides the data
                    views[i].setVisibility(View.GONE);
                }
            }
            //Hides the data if it should not be visible
            if(!visible) {
                views[i].setVisibility(View.GONE);
            }

            //This changes oneItemDisplayed to true if no item was displayed yet and one will be
            //visible
            if(!oneItemDisplayed && views[i].getVisibility() == View.VISIBLE)
                oneItemDisplayed = true;
        }

        //If no item is displayed show a message no item is displayed
        if(!oneItemDisplayed) {
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
        }
    }

    //Action if save button is clicked
    private void saveButtonClicked() {
        //Update the data item by updating the data with the corresponding String of the EditText
        for(int i=0;i<views.length;i++) {
            //Gets the ViewHolder which is holding all the UI elements
            ViewHolder holder = (ViewHolder) views[i].getTag();

            //Get a valid trimmed CSV string
            data.setDataAtIndex(i, getTrimmedString(holder.content));
        }

        //If data has updated show a Toast to notify the user
        Toast.makeText(Constants.context, getString(R.string.message_entry_updated),
                Toast.LENGTH_LONG).show();

        //Call on update
        onUpdate(data);
        //set the changed flag
        Constants.hasChanged();
        //make sure new data is no new data anymore and refresh the view to hide not wanted columns
        newData = false;
        refreshView();
    }

    //This method returns a trimmed String with all ',' replaced by ';'
    @NonNull
    private String getTrimmedString(EditText editText){
        return editText.getText().toString().trim().replaceAll(",",";");
    }

    //This is recommended by the android API guides to reduce the calls of findViewById()
    private static class ViewHolder {
        private TextView header;
        private EditText content;
    }
}
