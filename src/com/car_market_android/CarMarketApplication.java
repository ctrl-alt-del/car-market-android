package com.car_market_android;

import com.squareup.picasso.Picasso;

import android.app.Application;

public class CarMarketApplication extends Application {

	private Picasso mPicasso;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// TODO: add cache and download client
		mPicasso = new Picasso.Builder(this).build();
	}

	public Picasso getPicasso() {
        return mPicasso;
    }
}