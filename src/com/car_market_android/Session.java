package com.car_market_android;

import android.app.Activity;
import android.content.Context;

public class Session {
	
	private Activity mActivity;
	private Context mContext;
	
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

}
