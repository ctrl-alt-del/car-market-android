package com.car_market_android;
import java.util.List;


import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.R;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.JsonDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MyVehicle_VehicleAdapter extends BaseAdapter implements View.OnClickListener{

	Activity activity;
	List<Vehicle> vehicles;
	private SharedPreferences sharedPreferences;
	private boolean dataChanged = false;

	public MyVehicle_VehicleAdapter(Activity activity, List<Vehicle> rows) {
		this.activity = activity;
		this.vehicles = rows;
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
	}
	
	public boolean isDataChanged() {
		return this.dataChanged;
	}

	public void setDataChanged(boolean dataChanged) {
		this.dataChanged = dataChanged;
	}
	
	public void saveDataChanegs() {
		if (vehicles == null || sharedPreferences == null) {
			return;
		}
		
		JsonDB.setVehiclesToJsonDB(this.activity, 
				this.activity.getString(R.string.CM_USER_WISHLIST), vehicles);
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

		Vehicle vehicle = vehicles.get(position);

		VehicleIndexRowViewHolder holder;
		if (convertView == null) {

			holder = new VehicleIndexRowViewHolder();

			convertView = View.inflate(this.activity, R.layout.my_vehicles_row, null);
			holder.Title = (TextView) convertView.findViewById(R.id.my_vehicle_row_mmy);
			holder.Vin = (TextView) convertView.findViewById(R.id.my_vehicle_row_vin);
			holder.Listing_switch = (Switch) convertView.findViewById(R.id.my_vehicle_row_switch);

			convertView.setTag(holder);
		} else {
			holder = (VehicleIndexRowViewHolder) convertView.getTag();
		}

		holder.Title.setText(vehicle.getManufacturer() + ", " + vehicle.getModel() + ", " + vehicle.getYear());
		holder.Vin.setText(vehicle.getVin());

		

		VehicleIndexRowButtonActionHolder switchAH = new VehicleIndexRowButtonActionHolder(ButtonAction.LISTING_SWITCH, vehicle);
		holder.Listing_switch.setTag(switchAH);
		holder.Listing_switch.setOnClickListener(this);


		vehicleId2ListPosition.put(vehicle.getId(), position);
		return convertView;
	}

	@Override
	public void onClick(View view) {

		// User reflection to verify the casting is appropriate
		if (view.getTag() instanceof VehicleIndexRowButtonActionHolder) {

			VehicleIndexRowButtonActionHolder btnActionHolder = (VehicleIndexRowButtonActionHolder) view.getTag();

			Vehicle vehicle = btnActionHolder.getVehicle();

			switch (btnActionHolder.getButtonAction()) {
			case LISTING_SWITCH:
				Toast.makeText(this.activity, "Switch is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
				
				
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Class to hold the views of vehicle_index_row.xml.
	 * 
	 * @since 2014-08-23
	 * @version 1.0
	 * */
	private class VehicleIndexRowViewHolder {

		protected TextView Title;
		protected TextView Vin;
	}

	/**
	 * Class to hold vehicle information along with button action, so button
	 * action can be identified by OnClickListener.

	 * @param buttonAction identifies which button perform the action
	 * @param vehicle stores the {@link Vehicle} information 
	 * 
	 * @since 2014-08-23
	 * @version 1.0
	 * */
	private class VehicleIndexRowButtonActionHolder {

		private final ButtonAction buttonAction;
		private final Vehicle vehicle;

		public VehicleIndexRowButtonActionHolder(ButtonAction buttonAction, Vehicle vehicle) {
			this.buttonAction = buttonAction;
			this.vehicle = vehicle;
		}

		public ButtonAction getButtonAction() {
			return this.buttonAction;
		}

		public Vehicle getVehicle() {
			return this.vehicle;
		}
	}
}
