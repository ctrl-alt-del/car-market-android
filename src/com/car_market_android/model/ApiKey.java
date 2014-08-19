package com.car_market_android.model;

public class ApiKey {
	
	private String token;
	private long user_id;
	private String message;

	public String getToken() {
		return this.token;
	}

	public ApiKey setToken(String token) {
		this.token = token;
		return this;
	}
	
	public long getUser_id() {
		return this.user_id;
	}

	public ApiKey setUser_id(long user_id) {
		this.user_id = user_id;
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
