package com.car_market_android.util;


import android.content.Context;
import android.widget.Toast;

public class MessageUtils {

    public static void showToastShort(Context context, int resourceId) {
        showToastShort(context, context.getString(resourceId));
    }

    public static void showToastLong(Context context, int resourceId) {
        showToastLong(context, context.getString(resourceId));
    }

    public static void showToastShort(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    private static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }
}
