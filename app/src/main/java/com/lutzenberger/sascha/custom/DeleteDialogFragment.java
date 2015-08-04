package com.lutzenberger.sascha.custom;


import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the DeleteDialog. If this class is used by any activity the activity needs
 * to implement the DialogListener. The DialogListener methods are called when the user clicks
 * a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.1 - 04.08.2015
 *
 */
public class DeleteDialogFragment extends AbstractDialogFragment {
    @Override
    int getIconId() {
        return R.drawable.ic_delete;
    }

    @Override
    String getTitle() {
        return "Delete data?";
    }

    @Override
    String getMessage() {
        return "This will delete the selected data from your device. This can not be undone.";
    }

    @Override
    String getPositiveButtonText() {
        return "Delete now";
    }

    @Override
    String getNegativeButtonText() {
        return "Cancel";
    }
}
