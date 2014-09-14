package com.car_market_android;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.car_market_android.model.Vehicle;
import com.car_market_android.network.ApiClient;
import com.car_market_android.network.GetRequestResultEvent;
import com.car_market_android.util.EventsBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

public class MyVehicle extends Activity 
implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

	private ListView MyVehicle_Listview;
	private SwipeRefreshLayout swipeRefreshLayout;
	private MyVehicle_VehicleAdapter adapter;
	private LinkedList<Vehicle> data = new LinkedList<Vehicle>();
	private Activity activity;

	/** 
	 * isLoadingMore is used to prevent the LOAD_MORE action being perform again
	 * while it is performing.
	 * */
	private boolean isLoadingMore = false;

	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.activity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_vehicles);

		EventsBus.getInstance().register(this);
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);


		this.swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.my_vehicle_swipe);
		this.swipeRefreshLayout.setColorScheme(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
		this.swipeRefreshLayout.setEnabled(false);

		this.MyVehicle_Listview = (ListView) this.findViewById(R.id.my_vehicle_list);

		this.adapter = new MyVehicle_VehicleAdapter(this, data);
		this.MyVehicle_Listview.setAdapter(this.adapter);

		this.swipeRefreshLayout.setOnRefreshListener(this);
		this.MyVehicle_Listview.setOnScrollListener(this);

		this.MyVehicle_Listview.requestFocus();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_add:
			Toast.makeText(this, "add button on action bar is clicked...", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onDestroy() {
		EventsBus.getInstance().unregister(this);
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (this.data.size() == 0) {
			this.onRefresh();
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		this.swipeRefreshLayout.setEnabled(firstVisibleItem == 0);

		boolean loadMore = (firstVisibleItem + visibleItemCount >= totalItemCount);

		// TODO: add swipe to load more feature
		if(loadMore && !isLoadingMore && this.data.size() > 0) {

			// 1. limiter used to stop the load more feature
			if (this.data.size() >= 6) {
				return;
			}

			this.isLoadingMore = true;

			// 2. download additional data

			//			this.dialog = new ProgressDialog(getActivity());
			//			this.dialog.setMessage("Loading More Listings...");
			//			this.dialog.show();
		}

	}

	@Override
	public void onRefresh() {

		this.swipeRefreshLayout.setRefreshing(true);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {

				data.clear();
				// TODO: add swipe to reload feature

				String token = sharedPreferences.getString(getString(R.string.CM_API_TOKEN), "");
				long user_id = sharedPreferences.getLong(getString(R.string.CM_API_USER_ID), -1);

				if (StringUtils.isBlank(token) || user_id == -1) {

					/** if the user is not sign in, s/he technically will not able
					 * to find the way to this page and activity, but in case s/he
					 * somehow does.  This condition will send s/he back to the
					 * profile_fragment.
					 */
					onBackPressed();
					return;
				} else {
//				
					/**
					 * Modify the limit and offset parameters to enable the "load more" feature
					 * */
					ApiClient.getInstance(activity).getVehicles(user_id, "Token " + token, new Callback<List<Vehicle>>() {

						@Override
						public void success(List<Vehicle> vehicles, Response response) {
							for (Vehicle each : vehicles) {
								data.add(each);
							}

							adapter.notifyDataSetChanged();
							swipeRefreshLayout.setRefreshing(false);
						}

						@Override
						public void failure(RetrofitError retrofitError) {
							/**
							 * This message is for debug mode.
							 * */
							Toast.makeText(activity, retrofitError.getMessage(), Toast.LENGTH_SHORT).show();
							/**
							 * This message is for production mode.
							 * */
							Toast.makeText(activity, "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();
						}

					});
				}

				// swipeRefreshLayout.setRefreshing(false);
			}
		}, 3000);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		default:
			Toast.makeText(this, "Unexpected button pressed...", Toast.LENGTH_SHORT).show();
			break;
		}		
	}

	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();
		Vehicle[] vehicles;

		switch (event.getCaller()) {
		case R.string.MY_VEHICLE_REFRESH:

			vehicles = gson.fromJson(event.getResult(), Vehicle[].class);

			for (Vehicle each : vehicles) {
				this.data.add(each);
			}

			this.adapter.notifyDataSetChanged();
			this.swipeRefreshLayout.setRefreshing(false);

			break;
		default:
			break;
		}
	}
}
