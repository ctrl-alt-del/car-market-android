package com.car_market_android;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.car_market_android.R;
import com.car_market_android.model.Vehicle;


public class VehicleAdapter extends BaseAdapter {

	Activity activity;
	List<Vehicle> vehicles;

	public VehicleAdapter(Activity activity, List<Vehicle> rows) {
		this.activity = activity;
		this.vehicles = rows;
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

			convertView = View.inflate(this.activity, R.layout.vehicle_index_row, null);
			holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_row_mmy);
			holder.Vin = (TextView) convertView.findViewById(R.id.vehicle_index_row_vin);
			holder.Like = (Button) convertView.findViewById(R.id.vehicle_index_row_like);
			holder.Save = (Button) convertView.findViewById(R.id.vehicle_index_row_save);
			holder.Review = (Button) convertView.findViewById(R.id.vehicle_index_row_review);

			convertView.setTag(holder);
		} else {
			holder = (VehicleIndexRowViewHolder) convertView.getTag();
		}

		holder.Title.setText(vehicle.getManufacturer() + ", " + vehicle.getModel() + ", " + vehicle.getYear());
		holder.Vin.setText(vehicle.getVin());


		VehicleIndexRowButtonActionHolder likeAH = new VehicleIndexRowButtonActionHolder();
		likeAH.buttonAction = ButtonAction.LIKE;
		likeAH.vehicle = vehicle;

		holder.Like.setTag(likeAH);
		return convertView;
	}

	private class VehicleIndexRowViewHolder {

		protected TextView Title;
		protected TextView Vin;
		protected Button Like;
		protected Button Review;
		protected Button Save;
	}

	private class VehicleIndexRowButtonActionHolder {
		protected ButtonAction buttonAction;
		protected Vehicle vehicle;
	}
}
