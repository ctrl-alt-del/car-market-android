package com.car_market_android;


import org.apache.commons.validator.routines.EmailValidator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.car_market_android.application.CarMarketActivity;
import com.car_market_android.model.ApiKey;
import com.car_market_android.network.ApiInterface;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.util.EventsBus;

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

public class UserAuth extends CarMarketActivity implements OnClickListener {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private EditText Email;
	private EditText Password;
	private Button Sign_in;
	private Button Cacnel;
	private SharedPreferences mSharedPreferences;
	private Activity activity;
	private ApiInterface mCarMarketClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_auth);

		this.activity = this;
		
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		mCarMarketClient = CarMarketClient.getInstance(this);
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

			this.dialog = new ProgressDialog(this);
			this.dialog.setMessage("Signing in...");
			this.dialog.show();


			mCarMarketClient.signin(
					email, password, new Callback<ApiKey>() {

						@Override
						public void success(ApiKey apiKey, Response response) {

							if (dialog.isShowing()) {
								dialog.dismiss();
							}

							if (apiKey.getToken() == null) {
								Toast.makeText(activity, "unable to sign in, make sure your email and password are correct.", Toast.LENGTH_SHORT).show();
							} else {
								SharedPreferences.Editor edit = mSharedPreferences.edit();
								edit.putString(getString(R.string.CM_API_TOKEN), apiKey.getToken());
								edit.putLong(getString(R.string.CM_API_USER_ID), apiKey.getUserId());
								edit.apply();

								getSession().saveApiToken(apiKey.getToken());
								getSession().saveUserId(apiKey.getUserId());
								finish();
							}
						}

						@Override
						public void failure(RetrofitError retrofitError) {
							/**
							 * This message is for debug mode.
							 * */
							Toast.makeText(activity, retrofitError.getMessage(), Toast.LENGTH_LONG).show();
							/**
							 * This message is for production mode.
							 * */
							Toast.makeText(activity, "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();

							if (dialog.isShowing()) {
								dialog.dismiss();
					}
				}

			});
			
			break;
		case R.id.cancel_auth:
			this.onBackPressed();
		default:
			break;
		}
	}
}
