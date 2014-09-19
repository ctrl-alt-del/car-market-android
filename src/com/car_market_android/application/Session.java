package com.car_market_android.application;

import android.app.Activity;
import android.content.Context;

public class Session {
	
	private Activity mActivity;
	private Context mContext;
	private String mApiToken;
	
	public Session(Activity activity) {
		mActivity = activity;
		mContext = activity.getBaseContext();
    }
	
	public Activity getActivity() {
		return mActivity;
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
}
