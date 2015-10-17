package com.car_market_android.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.car_market_android.application.Session;
import com.car_market_android.util.EventsBus;
import com.car_market_android.util.StringUtils;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Method to send a HTTP POST request to the specified url and grab its response
 *
 * @since 2014-08-07
 * @version 1.0
 * */
public class PostRequest extends AsyncTask<String, Void, PostRequestResultEvent> {

	private Context mContext;
	private Session mSession;
	private final int caller;
	private final List<NameValuePair> contents;

	public PostRequest(Context context, int caller, List<NameValuePair> contents) {
		this.mContext = context;
		this.caller = caller;
		this.contents = contents;
	}
	
	public PostRequest(Session session, int caller, List<NameValuePair> contents) {
		this.mSession = session;
		this.mContext = session.getCarMarketApplication();
		this.caller = caller;
		this.contents = contents;
	}

	@Override
	protected PostRequestResultEvent doInBackground(String... params) {
		String link = params[0];

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(link);
		NetworkUtils.setHeaders(mContext, request);

		String result = StringUtils.EMPTY;
		try {
			request.setEntity(new UrlEncodedFormEntity(this.contents));

			HttpResponse response = client.execute(request);

			if (response == null) {
				result = "No Response";
				return new PostRequestResultEvent(this.caller, result);
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				result = NetworkUtils.composeHttpErrorMessage(mContext, response);
				return new PostRequestResultEvent(this.caller, result);
			}
			
			result = NetworkUtils.parseResponseContent(response.getEntity().getContent());

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