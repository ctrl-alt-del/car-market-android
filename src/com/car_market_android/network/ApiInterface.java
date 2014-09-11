package com.car_market_android.network;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.Callback;

import java.util.List;

import com.car_market_android.model.ApiKey;
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


	/**
	 * Method to get user through API
	 * 
	 * @param vehicle_id the vehicle_id
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-08
	 * @version 1.0
	 * */
	@GET("/vehicles/{vehicle_id}/listing.json")
	void getVehicleListing(@Path("vehicle_id") long vehicle_id, Callback<Listing> callback);


	/**
	 * Method to sign in user through API
	 * 
	 * @param email the email
	 * @param password the password
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-11
	 * @version 1.0
	 * */
	@FormUrlEncoded
	@POST("/users/signin.json")
	void signin(
			@Field("user[email]") String email, 
			@Field("user[password]") String password, 
			Callback<ApiKey> callback);
	

	/**
	 * Method to create user through API
	 * 
	 * @param email the email
	 * @param password the password
	 * @param callback the asynchronous call back
	 * 
	 * @since 2014-09-11
	 * @version 1.0
	 * */
	@FormUrlEncoded
	@POST("/users.json")
	void createUser(
			@Field("user[nickname]") String nickname, 
			@Field("user[first_name]") String first_name,
			@Field("user[last_name]") String last_name, 
			@Field("user[email]") String email, 
			@Field("user[password]") String password, 
			@Field("user[password_confirmation]") String password_confirmation, 
			@Field("user[status]") String status, 
			Callback<User> callback);
}
