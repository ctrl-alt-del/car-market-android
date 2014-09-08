package com.car_market_android.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import retrofit.RestAdapter;

public class ApiClient {

	private static final String API_ENDPOINT = "http://car-market.herokuapp.com/api/v1";
	private static ApiInterface sTwitchTvService;

    public static ApiInterface getApiClient() {
    	
        if (sTwitchTvService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_ENDPOINT)
                    .build();

            sTwitchTvService = restAdapter.create(ApiInterface.class);
        }

        return sTwitchTvService;
    }
    
    /**
     * Method to check if network is connected
     * 
     * @param activity the {@link Activity}
     * @return true if network is connected
     * 
     * @since 2014-09-8
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
