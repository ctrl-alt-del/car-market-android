package com.car_market_android;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.car_market_android.R;


public class VehicleAdapter extends BaseAdapter {

	Activity activity;
	List<Integer> rows;

	public VehicleAdapter(Activity activity, List<Integer> rows) {
		this.activity = activity;
		this.rows = rows;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return rows.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		VehicleIndexRowViewHolder holder;
		if (convertView == null) {

			holder = new VehicleIndexRowViewHolder();

			convertView = View.inflate(this.activity, R.layout.vehicle_index_row, null);
			holder.Title = (TextView) convertView.findViewById(R.id.vehicle_index_row_mmy);
			convertView.setTag(holder);
		} else {
			holder = (VehicleIndexRowViewHolder) convertView.getTag();
		}

		holder.Title.setText("Row #" + rows.get(position));

		return convertView;
	}

	private class VehicleIndexRowViewHolder {

		protected TextView Title;
		protected TextView Vin;
		protected Button Like;
		protected Button Review;
		protected Button Save;
	}
}
