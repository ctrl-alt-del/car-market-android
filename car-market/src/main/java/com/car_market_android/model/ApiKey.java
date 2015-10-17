package com.car_market_android.model;

import com.google.gson.annotations.SerializedName;

public class ApiKey {
	
	private String token;
	
	@SerializedName("user_id")
	private String userId;
	
	private String message;

	public String getToken() {
		return this.token;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getMessage() {
		return this.message;
	}
	
	public ApiKey setMessage(String message) {
		this.message = message;
		return this;
	}
}
