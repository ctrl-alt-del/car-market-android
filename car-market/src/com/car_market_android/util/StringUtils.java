package com.car_market_android.util;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

public class StringUtils {

    public final static String EMPTY = "";

    public static String getEditTextString(EditText editText) {
        String text = editText.getText().toString();
        return text.trim();
    }

    public static String getEditTextString(Activity activity, int editTextResourceId) {
        View view = activity.findViewById(editTextResourceId);
        return (view instanceof EditText) ? getEditTextString((EditText) view) : EMPTY;
    }
}

