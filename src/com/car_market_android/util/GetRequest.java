package com.car_market_android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

/**
 * Method to send a HTTP GET request to the specified url and grab its response
 *
 * To access the localhost or 127.0.0.1 of your local server,such as
 * WAMP, MAMP, or LAMP, you need to route through 10.0.2.2 because
 * localhost and 127.0.0.1 is the emulator or the android device itself.
 * <br><br>
 * For more details, you can visit the official document in
 * <a href="http://developer.android.com/tools/devices/emulator.html#networkaddresses">here</a>
 *
 * @since 2014-07-26
 * @version 1.0
 * */
public class GetRequest extends AsyncTask<String, Void, GetRequestResultEvent> {

	private final int caller;
	private String authToken;

	public GetRequest(int caller) {
		this.caller = caller;
	}
	
	public GetRequest setAuthToken(String authToken) {
		this.authToken = authToken;
		return this;
	}


	@Override
	protected GetRequestResultEvent doInBackground(String... params) {
		String link = params[0];

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(link);
		request.addHeader("accept", "application/json");
		request.addHeader("content-type", "application/x-www-form-urlencoded");
		
		
		if (!StringUtils.isBlank(this.authToken)) {
			request.addHeader("authorization", "Token " + this.authToken);
		}

		String result = "";
		try {
			HttpResponse response = client.execute(request);

			if (response == null) {
				result = "No Response";
				return new GetRequestResultEvent(this.caller, result);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				result = "Failed : HTTP error code : " + response.getStatusLine().getStatusCode();
				return new GetRequestResultEvent(this.caller, result);
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line;

			while ((line = br.readLine()) != null) {
				result += line;
			}

			return new GetRequestResultEvent(this.caller, result);

		} catch (IOException e) {
			e.printStackTrace();
			return new GetRequestResultEvent(this.caller, e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void onPostExecute(GetRequestResultEvent event) {
		EventsBus.getInstance().post(event);
	}
}
