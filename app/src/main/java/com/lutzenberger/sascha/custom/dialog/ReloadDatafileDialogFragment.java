package com.lutzenberger.sascha.custom.dialog;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the ReloadDatafile Dialog. If this class is used by any activity the
 * activity needs to implement the DialogListener. The DialogListener methods are called when
 * the user clicks a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 12.08.2015
 *
 */
public class ReloadDatafileDialogFragment extends AbstractDialogFragment {
    @DrawableRes
    @Override
    int getIconId() {
        return R.drawable.ic_refresh;
    }

    @NonNull
    @Override
    String getTitle() {
        return getString(R.string.dialog_reload_title);
    }

    @NonNull
    @Override
    String getMessage() {
        return getString(R.string.dialog_reload_message);
    }

    @NonNull
    @Override
    String getPositiveButtonText() {
        return getString(R.string.dialog_reload_positive_button);
    }
}
