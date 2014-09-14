package com.car_market_android;

import android.content.Context;

public class Session {
	
	private Context mContext;
	
	public Session(Context context) {
        mContext = context;
    }
	
	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) mContext;
	}

}
