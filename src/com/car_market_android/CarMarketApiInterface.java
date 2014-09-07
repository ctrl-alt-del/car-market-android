package com.car_market_android;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Callback;

import java.util.List;

import com.car_market_android.model.Listing;

public interface CarMarketApiInterface {
	
	@GET("/listing.json")
    void getListings(@Query("limit") int limit, @Query("offset") int offset, Callback<List<Listing>> callback);

}
