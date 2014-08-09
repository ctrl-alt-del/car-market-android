package com.car_market_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.car_market_android.util.EventsBus;
import com.car_market_android.util.GetRequest;
import com.car_market_android.util.GetRequestResultEvent;
import com.car_market_android.util.PostRequestResultEvent;
import com.squareup.otto.Subscribe;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AuthFragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private EditText Email;
	private EditText Password;
	private Button Sign_in;
	private Button Terms_and_policy;
	private TextView Json_result;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static AuthFragment newInstance(int sectionNumber) {
		AuthFragment fragment = new AuthFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AuthFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_auth, container, false);

		this.Email = (EditText) rootView.findViewById(R.id.email);
		this.Password = (EditText) rootView.findViewById(R.id.password);
		this.Sign_in = (Button) rootView.findViewById(R.id.sign_in);
		this.Terms_and_policy = (Button) rootView.findViewById(R.id.terms_and_policy);
		this.Json_result = (TextView) rootView.findViewById(R.id.json_result);

		this.Sign_in.setOnClickListener(this);
		this.Terms_and_policy.setOnClickListener(this);

		EventsBus.getInstance().register(this);

		return rootView;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.sign_in:

			String email = this.Email.getText().toString();
			String password = this.Password.getText().toString();

			if (!EMAIL_VALIDATOR.isValid(email)) {
				Toast.makeText(getActivity(), "Email: " + email + " is not a valid email...", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (email.length() < 6) {
				Toast.makeText(getActivity(), "password is not valid...", Toast.LENGTH_SHORT).show();
				return;
			}
			
			List<NameValuePair> contents = new ArrayList<NameValuePair>();

			contents.add(new BasicNameValuePair("user[email]", email));
			contents.add(new BasicNameValuePair("user[password]", password));

			new PostRequest(R.id.sign_in, contents).execute(getString(R.string.API_ADDRESS) + "/users/signin");
			break;
		case R.id.terms_and_policy:
			new GetRequest(R.id.terms_and_policy).execute(getString(R.string.API_ADDRESS) + "/");
			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void onDestroy() {
		EventsBus.getInstance().unregister(this);
		super.onDestroy();
	}

	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {
		switch (event.getCaller()) {
		case R.id.sign_in:
			this.Json_result.setText(event.getResult());
			break;
		case R.id.terms_and_policy:
			this.Json_result.setText(event.getResult());
			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	
	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {
		switch (event.getCaller()) {
		case R.id.sign_in:
			this.Json_result.setText(event.getResult());
			break;
		case R.id.terms_and_policy:
			this.Json_result.setText(event.getResult());
			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	
	/**
	 * Method to send a HTTP POST request to the specified url and grab its response
	 *
	 * @since 2014-08-07
	 * @version 1.0
	 * */
	private class PostRequest extends AsyncTask<String, Void, PostRequestResultEvent> {

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
}
