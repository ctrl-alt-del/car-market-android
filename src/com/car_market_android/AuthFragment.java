package com.car_market_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

		this.Sign_in.setOnClickListener(this);

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
			
			Toast.makeText(getActivity(), "Email: "+this.Email.getText()+"\nPassword: "+this.Password.getText(), Toast.LENGTH_SHORT).show();
			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}
	private class GetRequest extends AsyncTask<String, Void, HttpResponse> {
		@Override
		protected HttpResponse doInBackground(String... params) {
			String link = params[0];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(link);
			request.addHeader("accept", "application/json");
			request.addHeader("content-type", "application/json");

			try {
				return client.execute(request);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				client.getConnectionManager().shutdown();
			}
		}

		@Override
		protected void onPostExecute(HttpResponse response) {

			if (response == null) {
				Toast.makeText(getActivity(), "API response: null", Toast.LENGTH_SHORT).show();
				return;
			}

			if (response.getStatusLine().getStatusCode() != 200) {
				Toast.makeText(getActivity(), 
						"Failed : HTTP error code : " + response.getStatusLine().getStatusCode(), 
						Toast.LENGTH_SHORT).show();
				return;
			}

			try {
				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output = "";
				String line;

				while ((line = br.readLine()) != null) {
					output += line;
				}

				Toast.makeText(getActivity(), "API response: " + output, Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getActivity(), "API response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
