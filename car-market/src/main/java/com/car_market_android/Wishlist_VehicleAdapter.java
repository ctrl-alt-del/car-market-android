package com.car_market_android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;
import com.car_market_android.model.Vehicle;
import com.car_market_android.network.ButtonAction;

import java.util.List;


public class Wishlist_VehicleAdapter extends BaseAdapter {

    private final Session mSession;
    Activity activity;
    List<Vehicle> vehicles;
    private SharedPreferences sharedPreferences;
    private boolean dataChanged = false;

    public Wishlist_VehicleAdapter(Activity activity, List<Vehicle> rows) {
        this.activity = activity;
        mSession = ((CarMarketApplication) activity.getApplicationContext()).getSession();
        this.vehicles = rows;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.activity);
    }

//    public boolean isDataChanged() {
//        return this.dataChanged;
//    }
//
//    public void setDataChanged(boolean dataChanged) {
//        this.dataChanged = dataChanged;
//    }
//
//    public void saveDataChanegs() {
//        if (vehicles == null || sharedPreferences == null) {
//            return;
//        }
//        mSession.setVehicleWishList(vehicles);
//    }

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

        final Vehicle vehicle = vehicles.get(position);

        VehicleIndexRowViewHolder holder;
        if (convertView == null) {

            holder = new VehicleIndexRowViewHolder();

            convertView = View.inflate(this.activity, R.layout.wishlist_fragment_row, null);
            holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_wishlist_row_mmy);
            holder.Vin = (TextView) convertView.findViewById(R.id.vehicle_index_wishlist_row_vin);
            holder.Delete = (Button) convertView.findViewById(R.id.vehicle_index_wishlist_row_delete);

            convertView.setTag(holder);
        } else {
            holder = (VehicleIndexRowViewHolder) convertView.getTag();
        }

        holder.Title.setText(vehicle.getManufacturer() + ", " + vehicle.getModel() + ", " + vehicle.getYear());
        holder.Vin.setText(vehicle.getVin());

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Delete is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();

                mSession.removeVehicleToWishList(vehicle.getVin());
                vehicles.remove(vehicle);
                notifyDataSetChanged();
            }
        });


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
        protected Button Delete;
    }
}
