package com.car_market_android.network;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NetworkUtils {
	
	public static Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("content-type", "application/x-www-form-urlencoded");
		return headers;
	}
	public static void pauseMainThreadForJob(long duration) {
		final CountDownLatch latch = new CountDownLatch(1);
		
		new Thread(new Runnable() {
            @Override
            public void run() {
                // do something
                latch.countDown();
            }
        }).start();
		
        try {
            latch.await(duration, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
		
	}

}
