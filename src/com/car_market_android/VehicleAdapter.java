package com.car_market_android;
import java.util.LinkedList;
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
import com.car_market_android.model.Listing;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.JsonDB;


public class VehicleAdapter extends BaseAdapter implements View.OnClickListener{

	Activity activity;
	List<Listing> listings;
	private SharedPreferences sharedPreferences;

	public VehicleAdapter(Activity activity, List<Listing> rows) {
		this.activity = activity;
		this.listings = rows;
		this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listings.size();
	}

	@Override
	public Listing getItem(int position) {
		// TODO Auto-generated method stub
		return this.listings.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		Listing listing = this.getItem(position);
		Vehicle vehicle = listing.getVehicle();

		VehicleIndexRowViewHolder holder;
		if (convertView == null) {

			holder = new VehicleIndexRowViewHolder();

			convertView = View.inflate(this.activity, R.layout.vehicle_index_row, null);
			holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_row_mmy);
			holder.Vin = (TextView) convertView.findViewById(R.id.vehicle_index_row_vin);
			holder.Price = (TextView) convertView.findViewById(R.id.vehicle_index_row_price);
			holder.Like = (Button) convertView.findViewById(R.id.vehicle_index_row_like);
			holder.Buy = (Button) convertView.findViewById(R.id.vehicle_index_row_buy);
			holder.Review = (Button) convertView.findViewById(R.id.vehicle_index_row_review);

			convertView.setTag(holder);
		} else {
			holder = (VehicleIndexRowViewHolder) convertView.getTag();
		}

		holder.Title.setText(vehicle.getManufacturer() + ", " + vehicle.getModel() + ", " + vehicle.getYear());
		holder.Vin.setText(vehicle.getVin());
		holder.Price.setText(listing.getCurrency() + " " + listing.getPrice());

		VehicleIndexRowButtonActionHolder likeAH = new VehicleIndexRowButtonActionHolder(ButtonAction.LIKE, vehicle);

		holder.Like.setTag(likeAH);
		holder.Like.setOnClickListener(this);

		VehicleIndexRowButtonActionHolder buyAH = new VehicleIndexRowButtonActionHolder(ButtonAction.BUY, vehicle);

		holder.Buy.setTag(buyAH);
		holder.Buy.setOnClickListener(this);

		VehicleIndexRowButtonActionHolder reviewAH = new VehicleIndexRowButtonActionHolder(ButtonAction.REVIEW, vehicle);

		holder.Review.setTag(reviewAH);
		holder.Review.setOnClickListener(this);

		return convertView;
	}

	@Override
	public void onClick(View view) {

		// User reflection to verify the casting is appropriate
		if (view.getTag() instanceof VehicleIndexRowButtonActionHolder) {

			VehicleIndexRowButtonActionHolder btnActionHolder = (VehicleIndexRowButtonActionHolder) view.getTag();

			Vehicle vehicle = btnActionHolder.getVehicle();

			switch (btnActionHolder.getButtonAction()) {
			case LIKE:
				
				LinkedList<Vehicle> data = JsonDB.getVehiclesFromJsonDB(this.activity, 
						this.activity.getString(R.string.CM_USER_WISHLIST));
				
				// TODO: this is O(n), will find a better solution later.
				for (Vehicle each : data) {
					if (each.getVin().equals(vehicle.getVin())) {
						Toast.makeText(this.activity, vehicle.getVin() + "\n is already in your wishlish", Toast.LENGTH_LONG).show();
						return;
					}
				}

				data.add(vehicle);

				JsonDB.setVehiclesToJsonDB(this.activity, 
						this.activity.getString(R.string.CM_USER_WISHLIST), data);

				Toast.makeText(this.activity, "Like is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();

				break;
			case BUY:
				Toast.makeText(this.activity, "Buy is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
				break;
			case REVIEW:
				Toast.makeText(this.activity, "Review is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
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
		protected TextView Price;
		protected Button Like;
		protected Button Review;
		protected Button Buy;
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
