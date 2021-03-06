package com.lutzenberger.sascha.custom.dialog;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the ResetSettings Dialog. If this class is used by any activity the
 * activity needs to implement the DialogListener. The DialogListener methods are called when
 * the user clicks a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 11.08.2015
 *
 */
public class ResetSettingsDialogFragment extends AbstractDialogFragment {
    @DrawableRes
    @Override
    int getIconId() {
        return R.drawable.ic_delete;
    }

    @NonNull
    @Override
    String getTitle() {
        return getString(R.string.reset_default_settings);
    }

    @NonNull
    @Override
    String getMessage() {
        return getString(R.string.reset_default_settings_summary);
    }

    @NonNull
    @Override
    String getPositiveButtonText() {
        return getString(R.string.reset_default_settings);
    }
}
