package com.car_market_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.car_market_android.model.ApiKey;
import com.car_market_android.util.EventsBus;
import com.car_market_android.util.PostRequest;
import com.car_market_android.util.PostRequestResultEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import android.view.ViewGroup.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserAuth extends Activity implements OnClickListener {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private EditText Email;
	private EditText Password;
	private Button Sign_in;
	private Button Cacnel;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_auth);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		EventsBus.getInstance().register(this);

		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		this.Email = (EditText) this.findViewById(R.id.email_auth);
		this.Password = (EditText) this.findViewById(R.id.password_auth);
		this.Sign_in = (Button) this.findViewById(R.id.sign_in_auth);
		this.Cacnel = (Button) this.findViewById(R.id.cancel_auth);

		this.Sign_in.setOnClickListener(this);
		this.Cacnel.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		EventsBus.getInstance().unregister(this);
		super.onDestroy();
	}

	private ProgressDialog dialog;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.sign_in_auth:
			String email = this.Email.getText().toString();
			String password = this.Password.getText().toString();

			if (!EMAIL_VALIDATOR.isValid(email)) {
				Toast.makeText(this, "Email: " + email + " is not a valid email...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (password.length() < 6) {
				Toast.makeText(this, "password is not valid...", Toast.LENGTH_SHORT).show();
				return;
			}

			List<NameValuePair> contents = new ArrayList<NameValuePair>();

			contents.add(new BasicNameValuePair("user[email]", email));
			contents.add(new BasicNameValuePair("user[password]", password));

			new PostRequest(R.id.sign_in_auth, contents).execute(getString(R.string.CM_API_ADDRESS) + "/users/signin");
			
			this.dialog = new ProgressDialog(this);
			this.dialog.setMessage("Signing in...");
			this.dialog.show();
			
			break;
		case R.id.cancel_auth:
			this.onBackPressed();
		default:
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();
		switch (event.getCaller()) {
		case R.id.sign_in_auth:

			ApiKey apiKey = gson.fromJson(event.getResult(), ApiKey.class);

			if (dialog.isShowing()) {
                dialog.dismiss();
            }
			
			if (apiKey.getToken() == null) {
				Toast.makeText(this, "unable to sign in, make sure your email and password are correct.", Toast.LENGTH_SHORT).show();
			} else {
				this.sharedPreferences.edit().putString(getString(R.string.CM_API_TOKEN), apiKey.getToken()).commit();
				this.sharedPreferences.edit().putLong(getString(R.string.CM_API_USER_ID), apiKey.getUser_id()).commit();
				this.onBackPressed();
			}

			break;
		default:
			break;
		}
	}
}
