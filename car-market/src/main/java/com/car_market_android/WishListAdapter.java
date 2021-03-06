package com.car_market_android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;
import com.car_market_android.model.Vehicle;

import java.util.List;

public class WishListAdapter extends BaseAdapter {

    private final Session mSession;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    List<Vehicle> vehicles;

    public WishListAdapter(Activity activity, List<Vehicle> rows) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mSession = ((CarMarketApplication) activity.getApplicationContext()).getSession();
        this.vehicles = rows;
    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int arg0) {
        return vehicles.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Vehicle vehicle = vehicles.get(position);

        VehicleIndexRowViewHolder holder;
        if (convertView == null) {

            holder = new VehicleIndexRowViewHolder();
            convertView = mInflater.inflate(R.layout.wishlist_fragment_row, parent, false);
            holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_wishlist_row_mmy);
            holder.Vin = (TextView) convertView.findViewById(R.id.vehicle_index_wishlist_row_vin);
            holder.Delete = (Button) convertView.findViewById(R.id.vehicle_index_wishlist_row_delete);

            convertView.setTag(holder);
        } else {
            holder = (VehicleIndexRowViewHolder) convertView.getTag();
        }

        final String title = mActivity.getString(R.string.marketplace_car_title, vehicle.getManufacturer(), vehicle.getModel(), vehicle.getYear());
        holder.Title.setText(title);
        holder.Vin.setText(vehicle.getVin());

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "Delete is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();

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
