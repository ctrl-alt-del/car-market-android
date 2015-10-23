package com.car_market_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.car_market_android.application.Session;
import com.car_market_android.model.ApiKey;

public class SessionUtils {

    private static final String CAR_MARKET_SESSION = "CAR_MARKET_SESSION_";
    private static final String ACCOUNT_KEY = CAR_MARKET_SESSION + "ACCOUNT_KEY";
    private static final String API_KEY = CAR_MARKET_SESSION + "API_KEY";

    private static SharedPreferences getCMPreferences(Context context, @NonNull String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getCMPreferencesEditor(Context context, @NonNull String name) {
        return getCMPreferences(context, name).edit();
    }

    public static void saveApiKey(Context context, ApiKey apiKey) {
        String serializedApiKey = GsonUtils.getGson().toJson(apiKey);
        SharedPreferences.Editor edit = getCMPreferencesEditor(context, ACCOUNT_KEY);
        edit.putString(API_KEY, serializedApiKey);
        edit.apply();
    }

    public static void restoreApiKey(Context context, Session session) {
        SharedPreferences sharedPreferences = getCMPreferences(context, ACCOUNT_KEY);
        String serializedApiKey = sharedPreferences.getString(API_KEY, StringUtils.EMPTY);
        ApiKey apiKey = TextUtils.isEmpty(serializedApiKey) ? null : GsonUtils.getGson().fromJson(serializedApiKey, ApiKey.class);
        session.setApiKey(apiKey);
    }

    public static void logout(Context context) {
        SharedPreferences.Editor edit =  getCMPreferencesEditor(context, ACCOUNT_KEY);
        edit.clear().apply();
    }
}
