package com.car_market_android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.model.Listing;
import com.car_market_android.model.Vehicle;
import com.car_market_android.network.ButtonAction;
import com.car_market_android.util.SharePreferencesUtils;
import com.car_market_android.util.StringUtils;


public class CarListAdapter extends BaseAdapter implements View.OnClickListener {

    Activity mActivity;
    List<Listing> mCars;
    LayoutInflater mInflater;

    public CarListAdapter(Activity activity, List<Listing> cars) {
        mActivity = activity;
        mCars = cars;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Listing getItem(int position) {
        return mCars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Listing listing = this.getItem(position);
        Vehicle vehicle = listing.getVehicle();

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

        holder.Title.setText(vehicle.getManufacturer() + ", " + vehicle.getModel() + ", " + vehicle.getYear());
        holder.Vin.setText(vehicle.getVin());
        holder.Price.setText(listing.getCurrency() + StringUtils.EMPTY + listing.getPrice());

        int p = Integer.valueOf(listing.getPrice());
        holder.Price.setTextColor(mActivity.getResources().getColor(this.getColorByPrice(p)));

        String state = !TextUtils.isEmpty(listing.getState()) ? ", " + listing.getState() : StringUtils.EMPTY;
        holder.Location.setText(listing.getCity() + state);

        VehicleIndexRowButtonActionHolder likeAH = new VehicleIndexRowButtonActionHolder(ButtonAction.LIKE, vehicle);

        holder.Like.setTag(likeAH);
        holder.Like.setOnClickListener(this);

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

                    LinkedList<Vehicle> data = SharePreferencesUtils.getVehiclesFromJsonDB(mActivity,
                            mActivity.getString(R.string.CM_USER_WISHLIST));

                    // TODO: this is O(n), will find a better solution later.
                    for (Vehicle each : data) {
                        if (each.getVin().equals(vehicle.getVin())) {
                            Toast.makeText(mActivity, vehicle.getVin() + "\n is already in your wishlish", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    data.add(vehicle);

                    SharePreferencesUtils.setVehiclesToJsonDB(mActivity,
                            mActivity.getString(R.string.CM_USER_WISHLIST), data);

                    Toast.makeText(mActivity, "Like is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();

                    break;
                case BUY:
                    Toast.makeText(mActivity, "Buy is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
                    break;
                case REVIEW:
                    Toast.makeText(mActivity, "Review is clicked!\n" + vehicle.getVin(), Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
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
     * Class to hold vehicle information along with button action, so button
     * action can be identified by OnClickListener.
     *
     * @version 1.0
     * @since 2014-08-23
     */
    private class VehicleIndexRowButtonActionHolder {

        private final ButtonAction buttonAction;
        private final Vehicle vehicle;

        /**
         * @param buttonAction identifies which button perform the action
         * @param vehicle      stores the {@link Vehicle} information
         */
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

    /**
     * Method to get the color theme base on price
     *
     * @param price
     * @version 1.0
     * @since 2014-08-30
     */
    private int getColorByPrice(int price) {
        if (price <= 3000) {
            return R.color.dark_red;
        }

        if (price <= 6000) {
            return R.color.dark_yellow;
        }

        if (price <= 9000) {
            return R.color.dark_blue;
        }

        return R.color.dark_green;
    }
}
