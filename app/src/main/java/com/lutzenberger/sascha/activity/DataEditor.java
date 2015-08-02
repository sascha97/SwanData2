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
public abstract class DataEditor extends ActionBarActivity {
    private LayoutInflater inflater;
    private SharedPreferences pref;
    private boolean oneItemDisplayed;
    private boolean newData;

    protected Data data;
    private boolean hiddenEmpty;
    private EditText[] editTexts;
    private View[] views;
    private TextView message;
    private LinearLayout layout;

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

        layout = (LinearLayout) findViewById(R.id.editor);

        this.data = getData(fieldPosition);
        hiddenEmpty = getHiddenEmpty();

        editTexts = new EditText[data.getNumberOfAttribues()];
        views = new View[data.getNumberOfAttribues()];
        for(int i=0;i<data.getNumberOfAttribues();i++) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_save:
                saveButtonClicked();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

    private void refreshView(){
        hiddenEmpty = getHiddenEmpty();
        oneItemDisplayed = false;

        for(int i=0;i<views.length;i++){
            String attributeName = data.getAttributeNameAt(i);
            boolean visible = pref.getBoolean("show_" + attributeName, true);

            //Set all items visible
            views[i].setVisibility(View.VISIBLE);

            if(hiddenEmpty) {
                if(getTrimmedString(editTexts[i]).isEmpty()) {
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

    private View getView(int index) {
        View view = inflater.inflate(R.layout.layout_display_data_item, null);
        TextView header = (TextView) view.findViewById(R.id.data_item_title);
        EditText content = (EditText) view.findViewById(R.id.data_item_text);

        //Add EditText to editTexts list
        editTexts[index] = content;

        String attributeName = this.data.getAttributeNameAt(index);
        String data = this.data.getDataAt(index).trim();

        String headerText = pref.getString("name_" + attributeName, "not defined...");

        if (attributeName.contains("comment")) {
            content.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            content.setMaxLines(5);
        }

        header.setText(headerText);
        content.setText(data);

        return view;
    }

    private void saveButtonClicked() {
        for(int i=0;i<editTexts.length;i++) {
            data.setDataAtIndex(i, getTrimmedString(editTexts[i]));
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
