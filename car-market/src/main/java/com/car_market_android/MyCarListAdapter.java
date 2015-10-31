package com.car_market_android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;
import com.car_market_android.model.Listing;
import com.car_market_android.model.Vehicle;
import com.car_market_android.network.CarMarketClient;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyCarListAdapter extends BaseAdapter {

    private final Session mSession;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private List<Vehicle> mVehicles;
    private HashMap<String, Integer> vehicleId2ListPosition = new HashMap<>();

    public MyCarListAdapter(Activity activity, List<Vehicle> vehicles) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mSession = ((CarMarketApplication) activity.getApplicationContext()).getSession();
        mVehicles = vehicles;
    }

    @Override
    public int getCount() {
        return mVehicles.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mVehicles.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Vehicle vehicle = mVehicles.get(position);

        VehicleIndexRowViewHolder holder;
        if (convertView == null) {

            holder = new VehicleIndexRowViewHolder();
            convertView = mInflater.inflate(R.layout.my_vehicles_row, parent, false);
            holder.Title = (TextView) convertView.findViewById(R.id.my_vehicle_row_mmy);
            holder.Vin = (TextView) convertView.findViewById(R.id.my_vehicle_row_vin);
            holder.Listing_switch = (Switch) convertView.findViewById(R.id.my_vehicle_row_switch);

            convertView.setTag(holder);
        } else {
            holder = (VehicleIndexRowViewHolder) convertView.getTag();
        }

        final String title = mActivity.getString(R.string.marketplace_car_title, vehicle.getManufacturer(), vehicle.getModel(), vehicle.getYear());

        holder.Title.setText(title);
        holder.Vin.setText(vehicle.getVin());

        holder.Listing_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Switch is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();

                CarMarketClient.getInstance(mActivity).getVehicleListing(vehicle.getId(), new Callback<Listing>() {

                    @Override
                    public void success(Listing listing, Response response) {

                        int position = vehicleId2ListPosition.get(listing.getVehicle().getId());

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        /**
                         * This message is for debug mode.
                         * */
                        Toast.makeText(mActivity, retrofitError.getMessage(), Toast.LENGTH_SHORT).show();
                        /**
                         * This message is for production mode.
                         * */
                        Toast.makeText(mActivity, "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
        vehicleId2ListPosition.put(vehicle.getId(), position);
        return convertView;
    }

    /**
     * Class to hold the views of vehicle_index_row.xml.
     *
     * @version 1.0
     * @since 2014-08-23
     */
    private class VehicleIndexRowViewHolder {

        protected TextView Title;
        protected TextView Vin;
        protected Switch Listing_switch;
    }
}
