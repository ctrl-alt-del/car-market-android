package com.car_market_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.car_market_android.application.Session;
import com.car_market_android.model.ApiKey;
import com.car_market_android.model.Vehicle;

import java.util.List;

public class SessionUtils {

    private static final String CAR_MARKET_SESSION = "CAR_MARKET_SESSION_";
    private static final String ACCOUNT_KEY = CAR_MARKET_SESSION + "ACCOUNT_KEY";
    private static final String API_KEY = CAR_MARKET_SESSION + "API_KEY";
    private static final String WISH_LIST_KEY = CAR_MARKET_SESSION + "WISH_LIST_KEY";

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

    public static void saveVehicleWishList(Context context, List<Vehicle> vehicleWishList) {
        String serializedVehicles = GsonUtils.getGson().toJson(vehicleWishList);
        SharedPreferences.Editor edit = getCMPreferencesEditor(context, ACCOUNT_KEY);
        edit.putString(WISH_LIST_KEY, serializedVehicles);
        edit.apply();
    }

    public static void restoreVehicleWishList(Context context, Session session) {
        SharedPreferences sharedPreferences = getCMPreferences(context, ACCOUNT_KEY);
        String serializedVehicles = sharedPreferences.getString(WISH_LIST_KEY, StringUtils.EMPTY);
        Vehicle[] vehicles = TextUtils.isEmpty(serializedVehicles) ? null : GsonUtils.getGson().fromJson(serializedVehicles, Vehicle[].class);
        session.setVehicleWishList(vehicles);
    }
}
