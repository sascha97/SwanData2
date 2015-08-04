package com.lutzenberger.sascha.custom;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the DeleteDialog. If this class is used by any activity the activity needs
 * to implement the DialogListener. The DialogListener methods are called when the user clicks
 * a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 03.08.2015
 */
public class DeleteDialogFragment extends DialogFragment {
    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the BuilderClass for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setMessage("This will delete the selected data from your device. This can not be undone.");
        builder.setTitle("Delete data?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Delete now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogPositiveClick();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogNegativeClick();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            dialogListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
