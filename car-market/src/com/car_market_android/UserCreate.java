package com.car_market_android;

import org.apache.commons.validator.routines.EmailValidator;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.car_market_android.application.CarMarketActivity;
import com.car_market_android.model.ApiKey;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.util.EventsBus;

import android.text.TextUtils;
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

public class UserCreate extends CarMarketActivity implements OnClickListener {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private EditText Nickname;
	private EditText Email;
	private EditText Password;
	private EditText Password_Confirmation;
	private Button Terms_and_policy;
	private Button Sign_up;
	private Button Cacnel;
	private SharedPreferences sharedPreferences;
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_create);
		
		this.activity = this;

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		EventsBus.getInstance().register(this);

		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		this.Nickname = (EditText) this.findViewById(R.id.nickname_user_create);
		this.Email = (EditText) this.findViewById(R.id.email_user_create);
		this.Password = (EditText) this.findViewById(R.id.password_user_create);
		this.Password_Confirmation = (EditText) this.findViewById(R.id.password_confirmation_user_create);
		this.Terms_and_policy = (Button) this.findViewById(R.id.terms_and_policy_user_create);
		this.Sign_up = (Button) this.findViewById(R.id.sign_up_user_create);
		this.Cacnel = (Button) this.findViewById(R.id.cancel_user_create);

		this.Terms_and_policy.setOnClickListener(this);
		this.Sign_up.setOnClickListener(this);
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
		case R.id.sign_up_user_create:

			String nickname = this.Nickname.getText().toString();
			String email = this.Email.getText().toString();
			String password = this.Password.getText().toString();
			String password_confirmation = this.Password_Confirmation.getText().toString();

			if (TextUtils.isEmpty(nickname)) {
				Toast.makeText(this, "nickname cannot be empty...", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (TextUtils.isEmpty(email)) {
				Toast.makeText(this, "email cannot be empty...", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "password cannot be empty...", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (TextUtils.isEmpty(password_confirmation)) {
				Toast.makeText(this, "password confirmation cannot be empty...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!EMAIL_VALIDATOR.isValid(email)) {
				Toast.makeText(this, "Email: " + email + " is not a valid email...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (password.length() < 6) {
				Toast.makeText(this, "password must be at least 6 characters long...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!password.equals(password_confirmation)) {
				Toast.makeText(this, "password and password confirmation do not match...", Toast.LENGTH_SHORT).show();
				return;
			}


			this.dialog = new ProgressDialog(this);
			this.dialog.setMessage("Signing up...");
			this.dialog.show();
			
			CarMarketClient.getInstance(this).createUser(nickname, "n/a", "n/a", 
					email, password, password_confirmation, "active", 
					new Callback<ApiKey>() {

						@Override
						public void failure(RetrofitError arg0) {

							if (dialog.isShowing()) {
								dialog.dismiss();
							}
						}

						@Override
						public void success(ApiKey apiKey, Response arg1) {
							if (dialog.isShowing()) {
								dialog.dismiss();
							}		
							
							if (!TextUtils.isEmpty(apiKey.getMessage())) {
								Toast.makeText(activity, apiKey.getMessage(), Toast.LENGTH_LONG).show();
							} else {
								sharedPreferences.edit().putString(getString(R.string.CM_API_TOKEN), apiKey.getToken()).commit();
								sharedPreferences.edit().putLong(getString(R.string.CM_API_USER_ID), apiKey.getUserId()).commit();
								getSession().saveApiToken(apiKey.getToken());
								getSession().saveUserId(apiKey.getUserId());
								onBackPressed();
							}
						}
						
					});

			break;
		case R.id.terms_and_policy_user_create:
			break;
		case R.id.cancel_user_create:
			this.onBackPressed();
			break;
		default:
			break;
		}
	}
}
