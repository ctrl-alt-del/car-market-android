package com.car_market_android.util;


import android.content.Context;
import android.widget.Toast;

public class MessageUtils {

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
