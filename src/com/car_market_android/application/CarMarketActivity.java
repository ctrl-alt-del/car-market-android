package com.car_market_android.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class CarMarketActivity extends Activity {

	protected Session mSession;
	protected ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public CarMarketApplication getCarMarketApplication() {
		return (CarMarketApplication) this.getApplication();
	}

	public Session getSession() {
		return getCarMarketApplication().getSession();
	}

	public void showProgressDialog(int resourceId) {
		String message = getString(resourceId);
		if (mProgressDialog == null) {
			mProgressDialog = ProgressDialog.show(this, "", message);
		} else {
			mProgressDialog.setMessage(message);
		}
	}

	public void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

}
