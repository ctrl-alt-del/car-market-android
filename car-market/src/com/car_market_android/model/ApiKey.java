package com.car_market_android.model;

import com.google.gson.annotations.SerializedName;

public class ApiKey {
	
	private String token;
	
	@SerializedName("user_id")
	private long userId;
	
	private String message;

	public String getToken() {
		return this.token;
	}

	public ApiKey setToken(String token) {
		this.token = token;
		return this;
	}
	
	public long getUserId() {
		return this.userId;
	}

	public ApiKey setUserId(long userId) {
		this.userId = userId;
		return this;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public ApiKey setMessage(String message) {
		this.message = message;
		return this;
	}
}
