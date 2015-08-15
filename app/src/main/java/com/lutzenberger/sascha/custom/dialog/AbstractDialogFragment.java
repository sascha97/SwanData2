package com.lutzenberger.sascha.custom.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lutzenberger.sascha.swandata.R;


/**
 * This class is the abstract basis for every dialog used in this application.
 *
 * This class already handles the DialogListener and the building of the Dialog.
 * The subclasses only need to override the given methods to change what has to be displayed
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 04.08.2015
 *
 */
public abstract class AbstractDialogFragment extends DialogFragment {
    private DialogListener dialogListener;

    private static boolean dialogShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the BuilderClass for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
                AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle(getTitle());
        builder.setMessage(getMessage());
        builder.setIcon(getIconId());
        builder.setPositiveButton(getPositiveButtonText(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogPositiveClick();
            }
        });
        builder.setNegativeButton(getNegativeButtonText(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onDialogNegativeClick();
            }
        });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        //The current dialog is not shown anymore.
        dialogShown = false;
    }

    /**
     * This static method is used to make sure only one dialog is shown in the entire application at
     * once. If a second dialog has to be displayed it won't be displayed. Any dialog which can't be
     * displayed right away will be lost.
     *
     * @param dialog The dialog which needs to be displayed
     * @param listener The dialog listener which specifies the action taken if any button is clicked
     * @param fragmentManager The fragment manager which is needed to display the dialog
     * @param tag The tag of the dialog.
     */
    public static void showDialog(AbstractDialogFragment dialog, DialogListener listener,
                                  FragmentManager fragmentManager,  String tag) {
        //If a dialog is shown don't show any other dialog.
        if(dialogShown)
            return;

        dialog.addDialogListener(listener);
        dialog.show(fragmentManager, tag);

        //Dialog is shown
        dialogShown = true;
    }

    /**
     * This method sets the DialogListener, this method is needed to specify the dialog listener
     * added.
     *
     * @param dialogListener The dialog listener to add, non null only
     */
    public void addDialogListener(@NonNull DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    /**
     * This method has to be implemented by all subclasses to set the Dialog an icon
     *
     * @return the @DrawableRes of the icon which has to be displayed
     */
    abstract @DrawableRes int getIconId();

    /**
     * This method has to be implemented by all subclasses to set the Title of the Dialog
     *
     * @return A String representing the Title of the Dialog
     */
    @NonNull
    abstract String getTitle();


    /**
     * This method has to be implemented by all subclasses to set the Message of the Dialog
     *
     * @return A String representing the Message of the Dialog
     */
    @NonNull
    abstract String getMessage();

    /**
     * This method has to be implemented by all subclasses to set the text of the positive
     * Button of the Dialog
     *
     * @return The text of the positive button
     */
    @NonNull
    abstract String getPositiveButtonText();

    /**
     * This method has to be implemented by all subclasses to change the text of the negative
     * Button of the Dialog from "Cancel".
     *
     * @return The text of the negative button
     */
    @NonNull
    String getNegativeButtonText() {
        return getString(R.string.dialog_cancel);
    }
}
