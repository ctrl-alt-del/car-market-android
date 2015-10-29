package com.car_market_android;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.application.CarMarketApplication;
import com.car_market_android.application.Session;
import com.car_market_android.model.Listing;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.StringUtils;

import java.util.List;


public class CarListAdapter extends BaseAdapter {

    Activity mActivity;
    List<Listing> mListings;
    LayoutInflater mInflater;
    private Session mSession;

    public CarListAdapter(Activity activity, List<Listing> listings) {
        mActivity = activity;
        mSession = ((CarMarketApplication) activity.getApplicationContext()).getSession();
        mListings = listings;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mListings.size();
    }

    @Override
    public Listing getItem(int position) {
        return mListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Listing listing = this.getItem(position);
        final Vehicle vehicle = listing.getVehicle();

        VehicleIndexRowViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.marketplace_fragment_row, parent, false);

            holder = new VehicleIndexRowViewHolder();
            holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_row_mmy);
            holder.Vin = (TextView) convertView.findViewById(R.id.vehicle_index_row_vin);
            holder.Price = (TextView) convertView.findViewById(R.id.vehicle_index_row_price);
            holder.Location = (TextView) convertView.findViewById(R.id.vehicle_index_row_location);
            holder.Like = (Button) convertView.findViewById(R.id.vehicle_index_row_like);
            //			holder.Buy = (Button) convertView.findViewById(R.id.vehicle_index_row_buy);
            //			holder.Review = (Button) convertView.findViewById(R.id.vehicle_index_row_review);

            convertView.setTag(holder);
        } else {
            holder = (VehicleIndexRowViewHolder) convertView.getTag();
        }

        final String title = mActivity.getString(R.string.marketplace_car_title, vehicle.getManufacturer(), vehicle.getModel(), vehicle.getYear());
        final String price = mActivity.getString(R.string.marketplace_car_price, listing.getCurrency(), listing.getPrice());

        holder.Title.setText(title);
        holder.Vin.setText(vehicle.getVin());
        holder.Price.setText(price);

        holder.Price.setTextColor(mActivity.getResources().getColor(this.getColorByPrice(listing)));

        String state = !TextUtils.isEmpty(listing.getState()) ? ", " + listing.getState() : StringUtils.EMPTY;
        holder.Location.setText(listing.getCity() + state);

        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSession.isVehicleInWishList(vehicle.getVin())) {
                    Toast.makeText(mActivity, vehicle.getVin() + "\n is already in your wishlish", Toast.LENGTH_LONG).show();
                } else {
                    mSession.addVehicleToWishList(vehicle);
                    mSession.setVehicleWishListUpdated(true);
                    Toast.makeText(mActivity, "Like is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
                }
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
        protected TextView Price;
        protected TextView Location;
        protected Button Like;
        //		protected Button Review;
        //		protected Button Buy;
    }

    /**
     * Method to get the color theme base on price
     *
     * @param listing
     * @version 1.0
     * @since 2014-08-30
     */
    private int getColorByPrice(Listing listing) {
        final int price = Integer.valueOf(listing.getPrice());

        if (price <= 3000) {
            return R.color.dark_red;
        } else if (price <= 6000) {
            return R.color.dark_yellow;
        } else if (price <= 9000) {
            return R.color.dark_blue;
        } else {
            return R.color.dark_green;
        }
    }
}
