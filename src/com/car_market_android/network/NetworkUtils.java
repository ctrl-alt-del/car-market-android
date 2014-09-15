package com.car_market_android.network;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NetworkUtils {
	
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
