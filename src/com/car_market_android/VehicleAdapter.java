package com.car_market_android;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

			TextView title;
			if (convertView == null) {
				convertView = View.inflate(this.activity, R.layout.vehicle_index_row, null);
				title = (TextView) convertView.findViewById(R.id.vehicle_index_row_mmy);
				convertView.setTag(title);
			} else {
				title = (TextView) convertView.getTag();
			}

			title.setText("Row #" + rows.get(position));

			return convertView;
		}}
