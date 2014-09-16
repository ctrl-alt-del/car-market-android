package com.car_market_android.network;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class NetworkUtils {

	public static Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("content-type", "application/x-www-form-urlencoded");
		return headers;
	}

	public static void setHeaders(HttpEntityEnclosingRequestBase request) {
		Map<String, String> headers = getHeaders();
		for (String headerName : headers.keySet()) {
			request.setHeader(headerName, headers.get(headerName));
		}
	}

	public static String composeHttpErrorMessage(HttpResponse response) {
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		int statusCode = response.getStatusLine().getStatusCode();
		return String.format("Failed : %s HTTP error code : %d", reasonPhrase, statusCode);
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
