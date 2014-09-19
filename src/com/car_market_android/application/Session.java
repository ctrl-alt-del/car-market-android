package com.car_market_android.application;

import android.content.Context;

public class Session {
	
	private Context mContext;
	private String mApiToken;
	private long mUserId;
	
	public Session(Context context) {
		mContext = context;
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
}
