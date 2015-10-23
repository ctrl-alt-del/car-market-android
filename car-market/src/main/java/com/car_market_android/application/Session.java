package com.car_market_android.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.car_market_android.R;
import com.car_market_android.model.ApiKey;
import com.car_market_android.util.GsonUtils;
import com.car_market_android.util.LogUtils;
import com.car_market_android.util.SessionUtils;
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
            SessionUtils.restoreApiKey(mContext, this);
        } catch (Exception e) {
            LogUtils.debug(e.getMessage());
        }
    }

    public CarMarketApplication getCarMarketApplication() {
        return (CarMarketApplication) mContext;
    }

    public void saveApiKey(ApiKey apiKey) {
        setApiKey(apiKey);
        SessionUtils.saveApiKey(mContext, mApiKey);
    }

    public void setApiKey(ApiKey apiKey) {
        mApiKey = apiKey;
    }

    public boolean hasSignedInUser() {
        return mApiKey != null;
    }

    public ApiKey getApiKey() {
        return mApiKey;
    }

    public void logout() {
        mApiKey = null;
        SessionUtils.logout(mContext);
    }
}
