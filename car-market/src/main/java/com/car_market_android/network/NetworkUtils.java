package com.car_market_android.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept", "application/json");
        headers.put("content-type", "application/x-www-form-urlencoded");
        return headers;
    }

    /**
     * Method to check if network is connected
     *
     * @param context the {@link android.content.Context}
     * @return true if network is connected
     * @version 1.0
     * @since 2014-09-08
     */
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork == null) {
            activeNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
