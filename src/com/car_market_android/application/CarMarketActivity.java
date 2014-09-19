package com.car_market_android.application;

import android.app.Activity;
import android.os.Bundle;

public class CarMarketActivity extends Activity {
	
	protected Session mSession;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) this.getApplication();
	}
	
	public Session getSession() {
		return getCarMarketApplication().getSession();
	}
}
