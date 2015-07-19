package com.car_market_android.application;

import com.car_market_android.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CarMarketActivity extends Activity {

	protected Session mSession;
	protected ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public CarMarketApplication getCarMarketApplication() {
		return Utilities.getCarMarketApplication(this);
	}

	public Session getSession() {
		return getCarMarketApplication().getSession();
	}
	
	public Context getContext() {
		return getCarMarketApplication().getContext();
	}

	protected void showProgressDialog(int resourceId) {
		Utilities.showProgressDialog(getContext() , mProgressDialog, resourceId);
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	protected void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = this.getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(
            		currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
