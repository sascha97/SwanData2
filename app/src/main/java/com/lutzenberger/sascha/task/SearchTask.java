package com.lutzenberger.sascha.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import static com.lutzenberger.sascha.swandata.Constants.context;
import com.lutzenberger.sascha.swan.DarvicSearchable;
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
public class SearchTask<T extends DarvicSearchable> extends
        AsyncTask<String, Integer, List<T>> {
    private ProgressDialog progressDialog;
    private final List<T> dataList;

    public SearchTask(List<T> dataList){
        this.dataList = dataList;
    }

    //Start showing the ProgressDialog
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, context.getString(R.string.wait),
                context.getString(R.string.searching_matches));
    }

    //The actual searching is done in here, the first argument of the VARARGS is the DARVIC CODE
    @Override
    protected final List<T> doInBackground(String... params) {
        //Parameter search
        String darvic = params[0].trim();

        List<T> resultList = new ArrayList<>();

        for(T data : dataList)
            if(darvic.equalsIgnoreCase(data.getDarvic())) {
                //T is either of the Type SwanData or SwanCodes
                resultList.add(data);
            }

        return resultList;
    }

    //End the ProgressDialog
    @Override
    protected void onPostExecute(List<T> resultList) {
        super.onPostExecute(resultList);
        progressDialog.dismiss();
    }
}
