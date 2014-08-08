package com.car_market_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.car_market_android.util.EventsBus;
import com.car_market_android.util.GetRequestResultEvent;
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

			if (!EMAIL_VALIDATOR.isValid(email)) {
				Toast.makeText(getActivity(), "Email: " + email + " is not a valid email...", Toast.LENGTH_SHORT).show();
				return;
			}

			new GetRequest(R.id.sign_in).execute("http://10.0.2.2/");

			break;
		case R.id.terms_and_policy:
			new GetRequest(R.id.terms_and_policy).execute("http://10.0.2.2/");
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
			Toast.makeText(this.getActivity(), event.getResult(), Toast.LENGTH_LONG).show();
			break;
		case R.id.terms_and_policy:
			Toast.makeText(this.getActivity(), event.getResult(), Toast.LENGTH_LONG).show();
			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}

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
	private class GetRequest extends AsyncTask<String, Void, GetRequestResultEvent> {

		private final int caller;

		public GetRequest(int caller) {
			this.caller = caller;
		}


		@Override
		protected GetRequestResultEvent doInBackground(String... params) {
			String link = params[0];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(link);
			request.addHeader("accept", "application/json");
			request.addHeader("content-type", "application/x-www-form-urlencoded");

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

	
}
