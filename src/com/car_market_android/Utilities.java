package com.car_market_android;

import android.app.ProgressDialog;
import android.content.Context;

public class Utilities {
	
	public static void showProgressDialog(Context context, ProgressDialog progressDialog, int resourceId) {
		String message = context.getString(resourceId);
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(context, "", message);
		} else {
			progressDialog.setMessage(message);
		}
	}

	protected static void dismissProgressDialog(ProgressDialog progressDialog) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
}
