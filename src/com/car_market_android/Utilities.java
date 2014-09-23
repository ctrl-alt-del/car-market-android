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

    public static void hideSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }

        Object systemService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        ((InputMethodManager) systemService).hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSoftKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }

        Object systemService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) systemService).showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
