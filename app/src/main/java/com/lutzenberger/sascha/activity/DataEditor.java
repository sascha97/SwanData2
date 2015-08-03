package com.lutzenberger.sascha.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lutzenberger.sascha.custom.DeleteDialogFragment;
import com.lutzenberger.sascha.custom.DialogListener;
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
 * @version 1.0 - 02.08.2015
 *
 */
public abstract class DataEditor extends ActionBarActivity implements DialogListener {
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

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pref = PreferenceManager.getDefaultSharedPreferences(Constants.context);

        LinearLayout layout = (LinearLayout) findViewById(R.id.editor);

        this.data = getData(fieldPosition);
        hiddenEmpty = getHiddenEmpty();

        views = new View[data.getNumberOfAttributes()];
        for(int i=0;i<data.getNumberOfAttributes();i++) {
            View view = getView(i);
            layout.addView(view);
            views[i] = view;
        }
        //Setup message view
        message = new TextView(DataEditor.this);
        message.setText("Please change settings to display an item...");
        layout.addView(message);

        refreshView();
    }

    @Override
    public void onResume() {
        super.onResume();

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
        MenuItem delete = menu.findItem(R.id.menu_delete);
        MenuItem edit = menu.findItem(R.id.menu_edit);
        MenuItem settings = menu.findItem(R.id.menu_settings);

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

    @Override
    public void onDialogNegativeClick() {
        Toast.makeText(Constants.context,"Item not deleted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogPositiveClick() {
        onDelete(data.getIndex());
        Toast.makeText(Constants.context,"Item deleted", Toast.LENGTH_LONG).show();
        finish();
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
    protected abstract Data getData(int fieldPosition);

    /**
     * This method is here that the activity can specify what happens if the
     * update is being executed. It is only called if update is valid.
     *
     * Is used for adding the new data to the list.
     */
    protected void onUpdate(Data data){
    }

    //Used to decide if hidden elements have to be shown
    private boolean getHiddenEmpty(){
        return !newData && pref.getBoolean("show_non_empty", true);
    }

    //This method handles when delete is clicked
    private void deleteClicked() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(getFragmentManager(), "delete_dialog");
    }

    //This method handles when edit is clicked
    private void editClicked(MenuItem item) {
        newData = true;
        //Hide the edit button afterwards
        item.setVisible(false);

        refreshView();
    }

    private View getView(int index) {
        View view = inflater.inflate(R.layout.layout_display_data_item, null);
        EditText content = (EditText) view.findViewById(R.id.data_item_text);

        String attributeName = this.data.getAttributeNameAt(index);
        String data = this.data.getDataAt(index).trim();

        if (attributeName.contains("comment")) {
            content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            content.setMaxLines(5);
        }

        content.setText(data);

        return view;
    }

    private void refreshView(){
        //Redraw the options menu
        invalidateOptionsMenu();

        hiddenEmpty = getHiddenEmpty();
        boolean oneItemDisplayed = false;

        for(int i=0;i<views.length;i++){
            TextView header = (TextView) views[i].findViewById(R.id.data_item_title);
            EditText content = (EditText) views[i].findViewById(R.id.data_item_text);

            String attributeName = data.getAttributeNameAt(i);
            boolean visible = pref.getBoolean("show_" + attributeName, true);

            String headerText = pref.getString("name_" + attributeName, "not defined...");
            header.setText(headerText);

            //Set all items visible
            views[i].setVisibility(View.VISIBLE);

            if(hiddenEmpty) {
                if(getTrimmedString(content).isEmpty()) {
                    views[i].setVisibility(View.GONE);
                } else {
                    oneItemDisplayed = true;
                }
            }

            if(!visible) {
                views[i].setVisibility(View.GONE);
            } else {
                oneItemDisplayed = true;
            }
        }

        if(!oneItemDisplayed) {
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
        }
    }

    private void saveButtonClicked() {
        for(int i=0;i<views.length;i++) {
            EditText content = (EditText) views[i].findViewById(R.id.data_item_text);
            data.setDataAtIndex(i, getTrimmedString(content));
        }

        Toast.makeText(Constants.context, getString(R.string.message_entry_updated),
                Toast.LENGTH_LONG).show();

        onUpdate(data);
    }

    @NonNull
    private String getTrimmedString(EditText editText){
        return editText.getText().toString().trim().replaceAll(",",";");
    }
}
