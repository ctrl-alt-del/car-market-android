package com.car_market_android.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtils {

    private static InputMethodManager getInputMethodManager(Activity activity) {
        return activity != null ? (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE) : null;
    }

    public static boolean hideSoftKeyboard(Activity activity) {
        return hideSoftKeyboard(activity, activity.getCurrentFocus());
    }

    public static boolean hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = getInputMethodManager(activity);

        if (inputMethodManager != null && view != null) {
            return inputMethodManager.hideSoftInputFromWindow(
                    view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    public static boolean showSoftKeyboard(Activity activity) {
        return showSoftKeyboard(activity, activity.getCurrentFocus());
    }

    public static boolean showSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = getInputMethodManager(activity);

        if (inputMethodManager != null && view != null) {
            return inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        return false;
    }
}
