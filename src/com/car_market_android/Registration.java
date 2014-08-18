package com.car_market_android;

import com.car_market_android.util.EventsBus;
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

public class Registration extends Activity implements OnClickListener {

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		EventsBus.getInstance().register(this);

		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
			break;
		default:
			break;
		}
	}
}
