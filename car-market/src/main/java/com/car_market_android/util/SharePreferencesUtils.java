package com.car_market_android.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.car_market_android.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SharePreferencesUtils {

	private static SharedPreferences sSharedPreferences;

    public static SharedPreferences getSharePreferences(Context context) {
        if (sSharedPreferences == null) {
            sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return sSharedPreferences;
    }

	private static final Gson gson = new GsonBuilder().create();
	
	public static LinkedList<Vehicle> getVehiclesFromJsonDB(Context context, String JsonDBKey) {
		
		LinkedList<Vehicle> data = new LinkedList<Vehicle>();

		String JSON_DB = getSharePreferences(context).getString(JsonDBKey, StringUtils.EMPTY);

		if (TextUtils.isEmpty(JSON_DB)) {
			return data;
		}

		Vehicle[] vehicles = gson.fromJson(JSON_DB, Vehicle[].class);

		for (Vehicle each : vehicles) {
			data.add(each);
		}

		return data;
	}
	
	public static void setVehiclesToJsonDB(Context context, String JsonDBKey, List<Vehicle> data) {
		
		String JSON_DB = gson.toJson(data.toArray(new Vehicle[]{}));
		
		getSharePreferences(context).edit().putString(JsonDBKey, JSON_DB).commit();
	}
}
