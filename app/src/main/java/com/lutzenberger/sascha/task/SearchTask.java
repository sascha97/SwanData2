package com.lutzenberger.sascha.task;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import static com.lutzenberger.sascha.swandata.Constants.context;
import com.lutzenberger.sascha.swan.DarvicSearchable;
import com.lutzenberger.sascha.swan.Data;
import com.lutzenberger.sascha.swandata.R;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used for Searching the SwanData and SwanCodes in a class.
 *
 * This uses an AsyncTask so that the UI doesn't freeze when the searching data takes some time...
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public class SearchTask extends
        AsyncTask<String, Integer, List<Data>> {
    private ProgressDialog progressDialog;
    private final List<Data> dataList;

    public SearchTask(List<Data> dataList){
        this.dataList = dataList;
    }

    //Start showing the ProgressDialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        progressDialog.setTitle(context.getString(R.string.message_wait));
        progressDialog.setMessage(context.getString(R.string.message_searching_matches));
        progressDialog.show();
    }

    //The actual searching is done in here, the first argument of the VARARGS is the DARVIC CODE
    @Override
    protected final List<Data> doInBackground(String... params) {
        //Parameter search
        String darvic = params[0].trim();

        List<Data> resultList = new ArrayList<>();

        for(int i=0;i<dataList.size();i++) {
            Data data = dataList.get(i);
            if(darvic.equalsIgnoreCase(data.getDarvic())) {
                resultList.add(data);
                //Set the index of the current position to the data
                data.setIndex(i);
            }
        }

        return resultList;
    }

    //End the ProgressDialog
    @Override
    protected void onPostExecute(List<Data> resultList) {
        super.onPostExecute(resultList);
        progressDialog.dismiss();
    }
}
