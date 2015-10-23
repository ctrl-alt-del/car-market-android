package com.car_market_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.car_market_android.R;
import com.car_market_android.application.Session;
import com.car_market_android.model.ApiKey;

public class SessionUtils {

    private static final String SESSION_KEY = "SESSION_KEY";
    private static final String ACCOUNT_KEY = "ACCOUNT_KEY";
    private static final String API_KEY = "API_KEY";

    public static void saveApiKey(Context context, ApiKey apiKey) {
        String serializedApiKey = GsonUtils.getGson().toJson(apiKey);
        SharedPreferences.Editor edit = context.getSharedPreferences(ACCOUNT_KEY, Context.MODE_PRIVATE).edit();
        edit.putString(API_KEY, serializedApiKey);
        edit.apply();
    }

    public static void restoreApiKey(Context context, Session session) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACCOUNT_KEY, Context.MODE_PRIVATE);
        String serializedApiKey = sharedPreferences.getString(API_KEY, StringUtils.EMPTY);
        ApiKey apiKey = TextUtils.isEmpty(serializedApiKey) ? null : GsonUtils.getGson().fromJson(serializedApiKey, ApiKey.class);
        session.setApiKey(apiKey);
    }

    public static void logout(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(ACCOUNT_KEY, Context.MODE_PRIVATE).edit();
        edit.clear().apply();
    }
}
