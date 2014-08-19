package com.car_market_android;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.car_market_android.model.ApiKey;
import com.car_market_android.model.User;
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

public class Registration extends Activity implements OnClickListener {

	private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
	private EditText Nickname;
	private EditText Email;
	private EditText Password;
	private EditText Password_Confirmation;
	private Button Terms_and_policy;
	private Button Sign_up;
	private Button Cacnel;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		EventsBus.getInstance().register(this);

		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		this.Nickname = (EditText) this.findViewById(R.id.nickname_register);
		this.Email = (EditText) this.findViewById(R.id.email_register);
		this.Password = (EditText) this.findViewById(R.id.password_register);
		this.Password_Confirmation = (EditText) this.findViewById(R.id.password_confirmation_register);
		this.Terms_and_policy = (Button) this.findViewById(R.id.terms_and_policy_register);
		this.Sign_up = (Button) this.findViewById(R.id.sign_up_register);
		this.Cacnel = (Button) this.findViewById(R.id.cancel_register);

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
		case R.id.sign_up_register:

			String nickname = this.Nickname.getText().toString();
			String email = this.Email.getText().toString();
			String password = this.Password.getText().toString();
			String password_confirmation = this.Password_Confirmation.getText().toString();

			if (StringUtils.isBlank(nickname)) {
				Toast.makeText(this, "nickname cannot be empty...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!EMAIL_VALIDATOR.isValid(email)) {
				Toast.makeText(this, "Email: " + email + " is not a valid email...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (password.length() < 6) {
				Toast.makeText(this, "password is not valid...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (password_confirmation.length() < 6) {
				Toast.makeText(this, "password confirmation is not valid...", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!password.equals(password_confirmation)) {
				Toast.makeText(this, "password and password confirmation do not match...", Toast.LENGTH_SHORT).show();
				return;
			}


			List<NameValuePair> contents = new ArrayList<NameValuePair>();

			contents.add(new BasicNameValuePair("user[nickname]", nickname));
			contents.add(new BasicNameValuePair("user[first_name]", "n/a"));
			contents.add(new BasicNameValuePair("user[last_name]", "n/a"));
			contents.add(new BasicNameValuePair("user[email]", email));
			contents.add(new BasicNameValuePair("user[password]", password));
			contents.add(new BasicNameValuePair("user[password_confirmation]", password_confirmation));
			contents.add(new BasicNameValuePair("user[status]", "active"));


			new PostRequest(R.id.sign_up_register, contents).execute(getString(R.string.CM_API_ADDRESS) + "/users");

			this.dialog = new ProgressDialog(this);
			this.dialog.setMessage("Signing up...");
			this.dialog.show();

			break;
		case R.id.terms_and_policy_register:
			break;
		case R.id.cancel_register:
			this.onBackPressed();
		default:
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();
		switch (event.getCaller()) {
		case R.id.sign_up_register:

			ApiKey apiKey = gson.fromJson(event.getResult(), ApiKey.class);

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			if (!StringUtils.isBlank(apiKey.getMessage())) {
				Toast.makeText(this, apiKey.getMessage(), Toast.LENGTH_LONG).show();
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
