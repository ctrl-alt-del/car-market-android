package com.car_market_android;

import com.car_market_android.model.Vehicle;
import com.car_market_android.util.EventsBus;
import com.car_market_android.util.GetRequest;
import com.car_market_android.util.GetRequestResultEvent;
import com.car_market_android.util.PostRequestResultEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MarketplaceFragment extends Fragment 
implements OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private Button Show_Vehicles;
	private TextView Json_result;
	private ListView Vehicle_Listview;
	private SwipeRefreshLayout swipeRefreshLayout;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static MarketplaceFragment newInstance(int sectionNumber) {
		MarketplaceFragment fragment = new MarketplaceFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MarketplaceFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		EventsBus.getInstance().register(this);

		View rootView = inflater.inflate(R.layout.vehicle_index, container, false);
		//		View rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);

		//		this.Show_Vehicles = (Button) rootView.findViewById(R.id.show_vehicles);
		//		this.Json_result = (TextView) rootView.findViewById(R.id.json_result);
		//		this.Vehicle_Listview = (ListView) rootView.findViewById(R.id.vehicle_list_marketplace);
		//
		//		this.Json_result.setText("MarketplaceFragment");
		//		this.Show_Vehicles.setOnClickListener(this);

		this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_vehicle_index);
		this.swipeRefreshLayout.setColorScheme(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
		this.swipeRefreshLayout.setEnabled(false);

		this.Vehicle_Listview = (ListView) rootView.findViewById(R.id.list);

		this.Vehicle_Listview.setAdapter(new BaseAdapter() {

			private String[] rows = new String[]{
					"01", "02", "03", "04", "05", 
					"06", "07", "08", "09", "10", 
					"11", "12"};

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return rows.length;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return rows[arg0];
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
					convertView = View.inflate(getActivity(), R.layout.row_vehicle, null);
					title = (TextView) convertView.findViewById(R.id.mmy_row_vehicle);
					convertView.setTag(title);
				} else {
					title = (TextView) convertView.getTag();
				}

				title.setText(rows[position]);

				return convertView;
			}});

		this.swipeRefreshLayout.setOnRefreshListener(this);

		this.Vehicle_Listview.setOnScrollListener(this);
		this.Vehicle_Listview.requestFocus();

		return rootView;
	}

	private ProgressDialog dialog;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.show_vehicles:

			new GetRequest(R.id.show_vehicles).execute(getString(R.string.CM_API_ADDRESS) + "/vehicles");
			this.dialog = new ProgressDialog(getActivity());
			this.dialog.setMessage("Loading Vehicles...");
			this.dialog.show();

			break;
		default:
			Toast.makeText(getActivity(), "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void onDestroy() {
		EventsBus.getInstance().unregister(this);
		super.onDestroy();
	}


	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		case R.id.show_vehicles:

			((MainActivity) getActivity()).setProfileResult(event.getResult());
			//			((MainActivity) getActivity()).getActionBar().setSelectedNavigationItem(1);

			Vehicle[] vehicles = gson.fromJson(event.getResult(), Vehicle[].class);

			String msg = "";
			for (Vehicle vehicle : vehicles) {
				msg += vehicle.getVin() + "\n";
			}

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			this.Json_result.setText(msg);
			break;
		default:
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		//Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		case R.id.show_vehicles:
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {

		this.swipeRefreshLayout.setRefreshing(true);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(false);

			}
		}, 3000);
	}

	@Override
	public void onScrollStateChanged(AbsListView absListView, int scrollState) {}

	@Override
	public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

		boolean loadMore = (firstVisibleItem + visibleItemCount >= totalItemCount);
		
		// TODO: add swipe to load more feature
		if(loadMore) {
			// 1. download additional data
			// 2. append new data to the current data that is given to the adapter
			// 3. run notifyDataSetChanged() for the adapter
		}
	}
}
