package com.car_market_android;

import com.car_market_android.application.CarMarketApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utilities {

    public static CarMarketApplication getCarMarketApplication(Activity activity) {
        return (CarMarketApplication) activity.getApplication();
    }

    public static void showProgressDialog(Context context, ProgressDialog progressDialog, int resourceId) {
        String message = context.getString(resourceId);
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(context, "", message);
        } else {
            progressDialog.setMessage(message);
        }
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

    private static InputMethodManager getInputMethodManager(Activity activity) {
        return activity != null ? (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE) : null;
    }

}
