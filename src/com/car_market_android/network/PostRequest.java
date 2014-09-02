package com.car_market_android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.car_market_android.util.EventsBus;

import android.os.AsyncTask;

/**
 * Method to send a HTTP POST request to the specified url and grab its response
 *
 * @since 2014-08-07
 * @version 1.0
 * */
public class PostRequest extends AsyncTask<String, Void, PostRequestResultEvent> {

	private final int caller;
	private final List<NameValuePair> contents;

	public PostRequest(int caller, List<NameValuePair> contents) {
		this.caller = caller;
		this.contents = contents;
	}

	@Override
	protected PostRequestResultEvent doInBackground(String... params) {
		String link = params[0];

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(link);
		request.addHeader("accept", "application/json");
		request.addHeader("content-type", "application/x-www-form-urlencoded");


		String result = "";
		try {
			request.setEntity(new UrlEncodedFormEntity(this.contents));

			HttpResponse response = client.execute(request);

			if (response == null) {
				result = "No Response";
				return new PostRequestResultEvent(this.caller, result);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				result = "Failed : HTTP error code : " + response.getStatusLine().getStatusCode();
				return new PostRequestResultEvent(this.caller, result);
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));

			String line;

			while ((line = br.readLine()) != null) {
				result += line;
			}

			return new PostRequestResultEvent(this.caller, result);

		} catch (IOException e) {
			e.printStackTrace();
			return new PostRequestResultEvent(this.caller, e.getMessage());
		} finally {
			client.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void onPostExecute(PostRequestResultEvent event) {
		EventsBus.getInstance().post(event);
	}
}