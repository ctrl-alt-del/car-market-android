package com.car_market_android;

import java.util.LinkedList;

import com.car_market_android.model.Vehicle;
import com.car_market_android.util.EventsBus;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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

	/** 
	 * isLoadingMore is used to prevent the LOAD_MORE action being perform again
	 * while it is performing.
	 * */
	private boolean isLoadingMore = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_vehicles);

		EventsBus.getInstance().register(this);

		getActionBar().setDisplayHomeAsUpEnabled(true);


		this.swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.my_vehicle_swipe);
		this.swipeRefreshLayout.setColorScheme(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
		this.swipeRefreshLayout.setEnabled(false);

		this.MyVehicle_Listview = (ListView) this.findViewById(R.id.my_vehicle_list);

		this.adapter = new MyVehicle_VehicleAdapter(this, data);
		this.MyVehicle_Listview.setAdapter(this.adapter);

		//		this.swipeRefreshLayout.setOnRefreshListener(this);
		//		this.MyVehicle_Listview.setOnScrollListener(this);

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
}
