package com.lutzenberger.sascha.swandata;

import android.content.Intent;

import com.lutzenberger.sascha.activity.DataListActivity;
import com.lutzenberger.sascha.file.DataFileReader;
import com.lutzenberger.sascha.swan.Data;

import java.util.List;

/**
 * This is the activity for displaying a SwanCodes result list on the mobile device.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 02.08.2015
 *
 */
public class SwanCodeListActivity extends DataListActivity {
    @Override
    protected List<Data> getDataList() {
        return DataFileReader.getSwanCodesList();
    }

    @Override
    protected String getMessageNoData() {
        return getString(R.string.message_no_smaples_found);
    }

    @Override
    protected Intent prepareIntent(){
        return new Intent(this, SwanCodeEditorActivity.class);
    }

    @Override
    protected void onDelete(int index){
        DataFileReader.getSwanCodesList().remove(index);
    }
}
