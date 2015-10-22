package com.car_market_android;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.util.StringUtils;

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
            progressDialog = ProgressDialog.show(context, StringUtils.EMPTY, message);
        } else {
            progressDialog.setMessage(message);
        }
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}