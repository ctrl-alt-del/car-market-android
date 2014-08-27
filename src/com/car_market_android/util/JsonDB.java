package com.car_market_android.util;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.car_market_android.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonDB {

	private static final Gson gson = new GsonBuilder().create();
	
	public static LinkedList<Vehicle> getVehiclesFromJsonDB(Activity activity, String JsonDBKey) {
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

		LinkedList<Vehicle> data = new LinkedList<Vehicle>();

		String JSON_DB = sharedPreferences.getString(JsonDBKey, "");

		if (StringUtils.isBlank(JSON_DB)) {
			return data;
		}

		Vehicle[] vehicles = gson.fromJson(JSON_DB, Vehicle[].class);

		for (Vehicle each : vehicles) {
			data.add(each);
		}

		return data;
	}
}
