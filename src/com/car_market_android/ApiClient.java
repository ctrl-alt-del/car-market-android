package com.car_market_android;

import retrofit.RestAdapter;

public class ApiClient {

	private static final String API_ENDPOINT = "http://car-market.herokuapp.com/api/v1";
	private static CarMarketApiInterface sTwitchTvService;

    public static CarMarketApiInterface getApiClient() {
    	
        if (sTwitchTvService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_ENDPOINT)
                    .build();

            sTwitchTvService = restAdapter.create(CarMarketApiInterface.class);
        }

        return sTwitchTvService;
    }
}
