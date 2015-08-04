package com.lutzenberger.sascha.custom;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the SaveChanges. If this class is used by any activity the activity needs
 * to implement the DialogListener. The DialogListener methods are called when the user clicks
 * a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 04.08.2015
 *
 */
public class SaveChangesDialogFragment extends AbstractDialogFragment {
    @Override
    int getIconId() {
        return R.drawable.ic_save;
    }

    @Override
    String getTitle() {
        return "Save changes?";
    }

    @Override
    String getMessage() {
        return "Do you want to save your changes now?";
    }

    @Override
    String getPositiveButtonText() {
        return "Save now";
    }

    @Override
    String getNegativeButtonText() {
        return "Save later";
    }
}
