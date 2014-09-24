package com.car_market_android.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.car_market_android.R;

public class Session {
	
	private Context mContext;
	private String mApiToken;
	private long mUserId;
    private SharedPreferences sharedPreferences;
	
	public Session(Context context) {
		mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
	
	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) mContext;
	}
	
	public void saveApiToken(String apiToken) {
		mApiToken = apiToken;
	}
	
	public String getApiToken() {
		return mApiToken;
	}
	
	public void saveUserId(long userId) {
		mUserId = userId;
	}
	
	public long getUserId() {
		return mUserId;
	}

    public boolean isUserSignIn() {
        return sharedPreferences.contains(mContext.getString(R.string.CM_API_TOKEN));
    }
}
