package com.car_market_android;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Callback;

import java.util.List;

import com.car_market_android.model.Listing;

public interface CarMarketApiInterface {
	
	/**
	 * Method to get listings through API
	 * 
	 * @param limit the number of items to retrieve
	 * @param offset the offset
	 * @param callback the async call back
	 * 
	 * @version 1.0
	 * */
	@GET("/listings.json")
    void getListings(@Query("limit") int limit, @Query("offset") int offset, Callback<List<Listing>> callback);

}
