package com.lutzenberger.sascha.custom.dialog;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the SaveChanges. If this class is used by any activity the activity needs
 * to implement the DialogListener. The DialogListener methods are called when the user clicks
 * a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.01 - 08.08.2015
 *
 */
public class SaveChangesDialogFragment extends AbstractDialogFragment {
    @Override
    int getIconId() {
        return R.drawable.ic_save;
    }

    @Override
    String getTitle() {
        return getString(R.string.dialog_save_title);
    }

    @Override
    String getMessage() {
        return getString(R.string.dialog_save_message);
    }

    @Override
    String getPositiveButtonText() {
        return getString(R.string.dialog_save_positive_button);
    }

    @Override
    String getNegativeButtonText() {
        return getString(R.string.dialog_save_negative_button);
    }
}
