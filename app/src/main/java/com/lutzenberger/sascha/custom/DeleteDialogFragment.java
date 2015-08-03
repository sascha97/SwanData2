package com.lutzenberger.sascha.custom;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the DeleteDialog
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
        builder.setMessage("Delete this data?");
        builder.setTitle("Confirm delete");
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
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
            System.out.println("NOT SUPPORTED");
        }
    }
}
