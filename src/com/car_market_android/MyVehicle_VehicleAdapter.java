package com.car_market_android;
import java.util.List;


import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.R;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.JsonDB;


public class MyVehicle_VehicleAdapter extends BaseAdapter implements View.OnClickListener{

	Activity activity;
	List<Vehicle> vehicles;
	private SharedPreferences sharedPreferences;

	public MyVehicle_VehicleAdapter(Activity activity, List<Vehicle> rows) {
		this.activity = activity;
		this.vehicles = rows;
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vehicles.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return vehicles.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub



		return convertView;
	}

	@Override
	public void onClick(View view) {

	}

}
