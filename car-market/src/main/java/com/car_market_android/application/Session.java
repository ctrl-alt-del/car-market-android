package com.car_market_android.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.car_market_android.R;
import com.car_market_android.model.ApiKey;
import com.car_market_android.util.GsonUtils;
import com.car_market_android.util.LogUtils;
import com.car_market_android.util.StringUtils;

public class Session {

    private static final String SESSION_KEY = "SESSION_KEY";
    private static final String API_KEY = "API_KEY";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private ApiKey mApiKey;

    public Session(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences(SESSION_KEY,  Context.MODE_PRIVATE);
        restore();
    }

    private void restore() {
        try {
            String serializedApiKey = mSharedPreferences.getString(API_KEY, StringUtils.EMPTY);
            if (!TextUtils.isEmpty(serializedApiKey)) {
                mApiKey = GsonUtils.getGson().fromJson(serializedApiKey, ApiKey.class);
            }
        } catch (Exception e) {
            LogUtils.debug(e.getMessage());
        }
    }

    public CarMarketApplication getCarMarketApplication() {
        return (CarMarketApplication) mContext;
    }

    public void saveApiKey(Context context, ApiKey apiKey) {
        mApiKey = apiKey;

        String serializedApiKey = GsonUtils.getGson().toJson(mApiKey);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(API_KEY, serializedApiKey);
        edit.apply();
    }

    public boolean hasSignedInUser() {
        return mApiKey != null;
    }

    public ApiKey getApiKey() {
        return mApiKey;
    }

    public void logout(Context context) {
        mApiKey = null;

        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.remove(API_KEY);
        edit.remove(context.getString(R.string.CM_USER_NICKNAME));
        edit.remove(context.getString(R.string.CM_USER_EMAIL));
        edit.apply();
    }
}
