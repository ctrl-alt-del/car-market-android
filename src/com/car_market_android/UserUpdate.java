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

public class UserUpdate extends Activity implements OnClickListener {

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_update);

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

		default:
			this.dialog = new ProgressDialog(this);
			this.dialog.setMessage("Loading...");
			this.dialog.show();
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();
		
		switch (event.getCaller()) {
		default:
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			break;
		}
	}
}
