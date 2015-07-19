package com.car_market_android.model;

public class User {
	private String first_name;
	private String last_name;
	private String nickname;
	private String email;
	private String created_at;
	private String updated_at;
	private String message;
	
	public String getFirst_name() {
		return this.first_name;
	}
	public User setFirst_name(String first_name) {
		this.first_name = first_name;
		return this;
	}
	public String getLast_name() {
		return this.last_name;
	}
	public User setLast_name(String last_name) {
		this.last_name = last_name;
		return this;
	}
	public String getNickname() {
		return this.nickname;
	}
	public User setNickname(String nickname) {
		this.nickname = nickname;
		return this;
	}
	public String getEmail() {
		return this.email;
	}
	public User setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getCreated_at() {
		return this.created_at;
	}
	public User setCreated_at(String created_at) {
		this.created_at = created_at;
		return this;
	}
	public String getUpdated_at() {
		return this.updated_at;
	}
	public User setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	public String getMessage() {
		return this.message;
	}
	public User setMessage(String message) {
		this.message = message;
		return this;
	}
}
