package com.car_market_android.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import retrofit.RestAdapter;

/**
 * Class to connect with REST API client defined in {@link ApiInterface} 
 * 
 * @since 2014-09-07
 * @version 1.0
 * */
public class ApiClient {

	/**
	 * the end-point of targeting REST API
	 * */
	private static final String API_ENDPOINT = "http://car-market.herokuapp.com/api/v1";

	private static ApiInterface ApiClient;

	/**
	 * Method to create or get an running instance to {@link RestAdapter}
	 * 
	 * @param activity the {@link Activity}
	 * @return an {@link ApiInterface} if network is connected, otherwise null
	 * 
	 * @since 2014-09-07
	 * @version 1.0
	 * */
	public static ApiInterface getApiClient(Activity activity) {

		/*
		 * check if device is connected to network
		 * */
		if (!isNetworkConnected(activity)) {
			Toast.makeText(activity, "Please connect to internet...", Toast.LENGTH_SHORT).show();
			return null;
		}

		/*
		 * if {@link ApiInterface} has not been created yet, create one
		 * */
		if (ApiClient == null) {
			RestAdapter restAdapter = new RestAdapter.Builder()
			.setEndpoint(API_ENDPOINT)
			.build();
			
//			restAdapter.setRequestInterceptor(new RequestInterceptor() {
//				@Override
//				public void intercept(RequestFacade request) {
//					request.addHeader("accept", "application/json");
//					request.addHeader("content-type", "application/x-www-form-urlencoded");
//				}
//			})

			ApiClient = restAdapter.create(ApiInterface.class);
		}

		return ApiClient;
	}


	/**
	 * Method to check if network is connected
	 * 
	 * @param activity the {@link Activity}
	 * @return true if network is connected
	 * 
	 * @since 2014-09-08
	 * @version 1.0
	 * */
	private static boolean isNetworkConnected(Activity activity) {

		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

		if (activeNetwork == null) {
			return false;
		}

		return activeNetwork.isConnectedOrConnecting();
	}
}
