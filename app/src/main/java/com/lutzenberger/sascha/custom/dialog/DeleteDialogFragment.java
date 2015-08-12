package com.lutzenberger.sascha.custom.dialog;


import android.support.annotation.NonNull;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the DeleteDialog. If this class is used by any activity the activity needs
 * to implement the DialogListener. The DialogListener methods are called when the user clicks
 * a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.11 - 08.08.2015
 *
 */
public class DeleteDialogFragment extends AbstractDialogFragment {
    @Override
    int getIconId() {
        return R.drawable.ic_delete;
    }

    @NonNull
    @Override
    String getTitle() {
        return getString(R.string.dialog_delete_title);
    }

    @NonNull
    @Override
    String getMessage() {
        return getString(R.string.dialog_delete_message);
    }

    @NonNull
    @Override
    String getPositiveButtonText() {
        return getString(R.string.dialog_delete_positive_button);
    }

    @NonNull
    @Override
    String getNegativeButtonText() {
        return getString(R.string.dialog_cancel);
    }
}
