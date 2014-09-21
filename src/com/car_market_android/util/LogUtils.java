package com.car_market_android.util;

import android.util.Log;

public class LogUtils {

    public static final String LOG_TAG = "CarMarket";

    public static void debug(String message) {
        if (message != null) {
            Log.d(LOG_TAG, message);
        }
    }

    public static void error(String message) {
        if (message != null) {
            Log.e(LOG_TAG, message);
        }
    }
}

