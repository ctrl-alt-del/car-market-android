package com.car_market_android;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CarMarketFragment extends Fragment {
	
	protected ProgressDialog mProgressDialog;
	
	public CarMarketApplication getCarMarketApplication() {
		return Utilities.getCarMarketApplication(getActivity());
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(
            		currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
