package com.car_market_android.application;

import android.app.Activity;

public class CarMarketActivity extends Activity {
	
	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) this.getApplication();
	}
	
	public Session getSession() {
		return getCarMarketApplication().getSession();
	}
}
