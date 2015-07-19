package com.car_market_android.util;

import com.squareup.otto.Bus;

public class EventsBus {
	
	private static final Bus BUS = new Bus();

	public static Bus getInstance() {
		return BUS;
	}
}
