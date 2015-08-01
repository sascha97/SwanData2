package com.lutzenberger.sascha.task;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import static com.lutzenberger.sascha.swandata.Constants.context;
import com.lutzenberger.sascha.file.Directories;
import com.lutzenberger.sascha.swandata.Constants;
import com.lutzenberger.sascha.swandata.R;


/**
 * This class builds an abstract basis for the FileTasks of this application.
 *
 * The progess dialog is already done in here.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public abstract class FileTask extends AsyncTask<Void, Integer, Void> {
    private boolean successful = true; //Indicates if an error occurs
    private ProgressDialog progressDialog;

    protected final String fileDirectory;
    protected final String swanCodesFileName;
    protected final String swanDataFileName;

    public FileTask(){
        //Get the file path
        fileDirectory = Directories.SWAN_DATA_DIRECTORY.toString();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(
                Constants.context);

        String swanData = pref.getString("swan_data_file_name","SwanData");
        swanDataFileName = swanData + ".csv";

        String swanCodes = pref.getString("swan_codes_file_name","SwanCodes");
        swanCodesFileName = swanCodes + ".csv";
    }

    /**
     * This method is used to get the message dependent if operation was successfully completed.
     * @param successful if the operation was completed successfully.
     * @return The message for a successfully completed operation.
     */
    protected abstract String getMessage(boolean successful);

    /**
     * This method is used to get the message for the ProgressDialog.
     *
     * @return The message for the ProgressDialog
     */
    protected abstract String getMessage();

    //This method is called when an error occurs during file handling
    public final void errorOccurred(){
        this.successful = false;
    }

    //Start showing the ProgressDialog
    @Override
    protected final void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context,
                context.getString(R.string.message_wait), getMessage());
    }

    //End the ProgressDialog
    @Override
    protected final void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        String message = getMessage(successful);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}