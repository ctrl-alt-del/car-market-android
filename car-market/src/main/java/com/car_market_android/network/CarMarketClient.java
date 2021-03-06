package com.car_market_android.network;

import android.content.Context;
import android.widget.Toast;

import com.car_market_android.application.Session;

import retrofit.RestAdapter;

/**
 * Class to connect with REST API client defined in {@link ApiInterface}
 *
 * @version 1.0
 * @since 2014-09-07
 */
public class CarMarketClient {

    /**
     * the end-point of targeting REST API
     */
    private static final String API_V1_ENDPOINT = "https://car-market.herokuapp.com/api/v1";

    private static ApiInterface sClient;

    /**
     * Method to create or get an running instance to {@link RestAdapter}
     *
     * @return an {@link ApiInterface} if network is connected, otherwise null
     * @version 1.0
     * @since 2014-09-07
     */
    public static ApiInterface getInstance() {

//		/*
//         * check if device is connected to an active network
//		 * */
//        if (!NetworkUtils.isNetworkConnected(context)) {
//            Toast.makeText(context, "Please connect to internet...", Toast.LENGTH_SHORT).show();
//            return null;
//        }

		/*
		 * if {@link ApiInterface} has not been created yet, create one
		 * */
        if (sClient == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_V1_ENDPOINT)
                    .build();

//			restAdapter.setRequestInterceptor(new RequestInterceptor() {
//				@Override
//				public void intercept(RequestFacade request) {
//					request.addHeader("accept", "application/json");
//					request.addHeader("content-type", "application/x-www-form-urlencoded");
//				}
//			})

            sClient = restAdapter.create(ApiInterface.class);
        }

        return sClient;
    }
}
