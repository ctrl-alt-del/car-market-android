package com.car_market_android.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.car_market_android.R;
import com.car_market_android.model.ApiKey;
import com.car_market_android.util.StringUtils;

public class Session {
	
	private Context mContext;
    private SharedPreferences sharedPreferences;
	private ApiKey mApiKey;

	public Session(Context context) {
		mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
	
	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) mContext;
	}

	public void saveApiKey(ApiKey apiKey) {
		mApiKey = apiKey;
	}

	public String getApiToken() {
		return mApiKey != null ? mApiKey.getToken() : StringUtils.EMPTY;
	}

	public String getUserId() {
		return mApiKey != null ? mApiKey.getUserId() : StringUtils.EMPTY;
	}

    public boolean isUserSignIn() {
        return sharedPreferences.contains(mContext.getString(R.string.CM_API_TOKEN));
    }
}
