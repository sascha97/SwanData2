package com.lutzenberger.sascha.custom;

/**
 * This is the Listener which has to be implemented by every activity if the activity wants to
 * react on an event from the Dialog.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 03.08.2015
 *
 */
public interface DialogListener {
    /**
     * This method will be called if the positive button of a Dialog will be pressed.
     */
    void onDialogPositiveClick();

    /**
     * This method will be called if the negative button of a Dialog will be pressed.
     */
    void onDialogNegativeClick();
}
