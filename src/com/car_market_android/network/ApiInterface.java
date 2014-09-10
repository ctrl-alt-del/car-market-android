package com.car_market_android.network;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.Callback;

import java.util.List;

import com.car_market_android.model.Listing;
import com.car_market_android.model.User;
import com.car_market_android.model.Vehicle;

/**
 * Interface to define REST API calls
 * 
 * @since 2014-09-07
 * @version 1.0
 * */
public interface ApiInterface {
	
	/**
	 * Method to get listings through API
	 * 
	 * @param limit the number of items to retrieve
	 * @param offset the offset
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-07
	 * @version 1.0
	 * */
	@GET("/listings.json")
    void getListings(@Query("limit") int limit, @Query("offset") int offset, Callback<List<Listing>> callback);


	/**
	 * Method to get vehicles of a user through API
	 * 
	 * @param user_id the user_id
	 * @param authorization the token with "Token " append in front of it
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-07
	 * @version 1.0
	 * */
	@GET("/users/{user_id}/vehicles.json")
    void getVehicles(@Path("user_id") long user_id, @Header("authorization") String authorization, Callback<List<Vehicle>> callback);

	
	/**
	 * Method to get user through API
	 * 
	 * @param user_id the user_id
	 * @param authorization the token with "Token " append in front of it
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-08
	 * @version 1.0
	 * */
	@GET("/users/{user_id}.json")
    void getUser(@Path("user_id") long user_id, @Header("authorization") String authorization, Callback<User> callback);

}