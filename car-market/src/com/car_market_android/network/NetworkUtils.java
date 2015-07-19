package com.car_market_android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import android.content.Context;

import com.car_market_android.R;
import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetworkUtils {

	public static Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("accept", "application/json");
		headers.put("content-type", "application/x-www-form-urlencoded");
		return headers;
	}

	public static void setHeaders(HttpRequestBase request) {
		Map<String, String> headers = getHeaders();
		for (String headerName : headers.keySet()) {
			request.setHeader(headerName, headers.get(headerName));
		}
	}
	
	public static void setHeaders(Context context, HttpRequestBase request) {
		setHeaders(request);
		Session session = ((CarMarketApplication) context).getSession();
		String authToken = session != null ? session.getApiToken() : "";
		if (!TextUtils.isEmpty(authToken)) {
			request.addHeader("authorization", "Token " + authToken);
		}
	}

	public static String composeHttpErrorMessage(Context context, HttpResponse response) {
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		int statusCode = response.getStatusLine().getStatusCode();
		return context.getResources().getString(R.string.http_error_message, reasonPhrase, statusCode);
	}
	
	public static String parseResponseContent(InputStream inputStream) throws IOException {
		String result = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		String line;
		while ((line = br.readLine()) != null) {
			result += line;
		}
		
		return result;
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

    /**
	 * Method to check if network is connected
	 *
	 * @param context the {@link android.content.Context}
	 * @return true if network is connected
	 *
	 * @since 2014-09-08
	 * @version 1.0
	 * */
	public static boolean isNetworkConnected(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

		if (activeNetwork == null) {
            activeNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		}

		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}
}
