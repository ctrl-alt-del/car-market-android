package com.car_market_android.network;

public class GetRequestResultEvent {

	private final int caller;
	private final String result;

	public GetRequestResultEvent(int caller, String result) {
		this.caller = caller;
		this.result = result;
	}

	public int getCaller() {
		return this.caller;
	}
	
	public String getResult() {
		return this.result;
	}
}