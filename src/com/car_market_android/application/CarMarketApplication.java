package com.car_market_android.application;

import com.squareup.picasso.Picasso;

import android.app.Application;

public class CarMarketApplication extends Application {

	private Picasso mPicasso;
	private Session mSession;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// TODO: add cache and download client
		mPicasso = new Picasso.Builder(this).build();
	}

	public Picasso getPicasso() {
        return mPicasso;
    }
	
	public Session getSession() {
		return mSession;
	}
	
	public void setSession(Session session) {
		mSession = session;
	}
}