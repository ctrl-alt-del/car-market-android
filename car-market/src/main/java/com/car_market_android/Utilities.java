package com.car_market_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.util.StringUtils;

public class Utilities {

    public static CarMarketApplication getCarMarketApplication(Activity activity) {
        return (CarMarketApplication) activity.getApplication();
    }

    public static ProgressDialog showProgressDialog(Activity activity, ProgressDialog progressDialog, int resourceId) {
        String message = activity.getString(resourceId);
        if (progressDialog != null) {
            progressDialog.setMessage(message);
            return progressDialog;
        } else {
            return ProgressDialog.show(activity, StringUtils.EMPTY, message);
        }
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
