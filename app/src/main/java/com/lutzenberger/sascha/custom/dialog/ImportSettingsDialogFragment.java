package com.lutzenberger.sascha.custom.dialog;

import com.lutzenberger.sascha.swandata.R;

/**
 * This class handles the ImportSettings Dialog. If this class is used by any activity the
 * activity needs to implement the DialogListener. The DialogListener methods are called when
 * the user clicks a on one button on the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 11.08.2015
 *
 */
public class ImportSettingsDialogFragment extends AbstractDialogFragment {
    @Override
    int getIconId() {
        return R.drawable.ic_settings_import;
    }

    @Override
    String getTitle() {
        return getString(R.string.import_settings);
    }

    @Override
    String getMessage() {
        return getString(R.string.import_settings_summary);
    }

    @Override
    String getPositiveButtonText() {
        return getString(R.string.import_settings);
    }

    @Override
    String getNegativeButtonText() {
        return getString(R.string.dialog_cancel);
    }
}
