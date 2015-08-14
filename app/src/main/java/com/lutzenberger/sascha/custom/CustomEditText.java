package com.lutzenberger.sascha.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


/**
 * This CustomEditText prevents the scrolling if the Text field is enabled, so that
 * it is not possible to scroll on the selected field.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 01.08.2015
 *
 */
public class CustomEditText extends EditText {
    /**
     * Constructor
     *  @param context  The current context.
     *
     */
    public CustomEditText(Context context) {
        super(context);
        setOnTouchListener(onTouchListener);
    }

    /**
     * Constructor
     *  @param context  The current context.
     *  @param attrs    A collection of attributes.
     *
     */
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(onTouchListener);
    }

    /**
     * Constructor
     *  @param context      The current context.
     *  @param attrs        A collection of attributes.
     *  @param defStyleAttr The style attribute.
     *
     */
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(onTouchListener);
    }

    //No lambda notation is used on purpose here to make sure the project compiles with JDK 1.7
    private static final View.OnTouchListener onTouchListener = new View.OnTouchListener(){
        /**
         * Called when a touch event is dispatched to a view. This allows listeners to
         * get a chance to respond before the target view.
         *
         * This is used to disable the scrolling of the ScrollView if a EditText field is focused.
         * This enables the functionality of being able to scroll in the EditText without scrolling
         * the scroll view.
         *
         * @param v     The view the touch event has been dispatched to.
         * @param event The MotionEvent object containing full information about
         *              the event.
         * @return True if the listener has consumed the event, false otherwise.
         */
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(v.isFocused()){
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }

            return false;
        }
    };
}
