package com.car_market_android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }
}
