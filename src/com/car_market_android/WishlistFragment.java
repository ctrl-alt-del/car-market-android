package com.car_market_android;

import java.util.LinkedList;

import org.apache.commons.lang3.StringUtils;

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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;


public class WishlistFragment extends Fragment 
implements OnClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private ListView Vehicle_Listview;
	private SwipeRefreshLayout swipeRefreshLayout;
	private VehicleAdapterWishlist vadp;
	private LinkedList<Vehicle> data = new LinkedList<Vehicle>();

	private SharedPreferences sharedPreferences;

	/** 
	 * isLoadingMore is used to prevent the LOAD_MORE action being perform again
	 * while it is performing.
	 * */
	private boolean isLoadingMore = false;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static WishlistFragment newInstance(int sectionNumber) {
		WishlistFragment fragment = new WishlistFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public WishlistFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		EventsBus.getInstance().register(this);

		View rootView = inflater.inflate(R.layout.vehicle_index_wishlist, container, false);

		this.swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.vehicle_index_wishlist_swipe);
		this.swipeRefreshLayout.setColorScheme(R.color.dark_blue, R.color.dark_green, R.color.dark_red, R.color.dark_yellow);
		this.swipeRefreshLayout.setEnabled(false);

		this.Vehicle_Listview = (ListView) rootView.findViewById(R.id.vehicle_index_wishlist_list);

		this.vadp = new VehicleAdapterWishlist(getActivity(), data);
		this.Vehicle_Listview.setAdapter(this.vadp);

		this.swipeRefreshLayout.setOnRefreshListener(this);

		this.Vehicle_Listview.setOnScrollListener(this);
		this.Vehicle_Listview.requestFocus();

		return rootView;
	}

	private ProgressDialog dialog;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
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

	@Override
	public void onResume() {
		super.onResume();
		if (sharedPreferences != null) {

			if (this.data.size() == 0) {
				this.onRefresh();
			}

		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (vadp.isDataChanged()) {
			vadp.saveDataChanegs();
			vadp.setDataChanged(false);
		}
	}

	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();
		Vehicle[] vehicles;

		switch (event.getCaller()) {
		case R.string.REFRESH_WISHLIST:

			((MainActivity) getActivity()).setProfileResult(event.getResult());
			//			((MainActivity) getActivity()).getActionBar().setSelectedNavigationItem(1);

			vehicles = gson.fromJson(event.getResult(), Vehicle[].class);

			for (Vehicle each : vehicles) {
				this.data.add(each);
			}

			this.vadp.notifyDataSetChanged();
			this.swipeRefreshLayout.setRefreshing(false);

			break;
		case R.string.LOAD_MORE_WISHLIST:

			((MainActivity) getActivity()).setProfileResult(event.getResult());
			//			((MainActivity) getActivity()).getActionBar().setSelectedNavigationItem(1);

			vehicles = gson.fromJson(event.getResult(), Vehicle[].class);

			for (Vehicle each : vehicles) {
				this.data.add(each);
			}

			this.vadp.notifyDataSetChanged();


			//			if (dialog.isShowing()) {
			//				dialog.dismiss();
			//			}
			this.isLoadingMore = false;

			break;
		default:
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		//Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		case R.string.REFRESH_WISHLIST:
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

				// TODO: add swipe to reload feature
				if (vadp.isDataChanged()) {
					vadp.saveDataChanegs();
					vadp.setDataChanged(false);
				}
				
				data.clear();

				String JSON_DB = sharedPreferences.getString(getString(R.string.CM_USER_WISHLIST), "");

				if (StringUtils.isBlank(JSON_DB)) {
					return;
				}

				Gson gson = new GsonBuilder().create();
				Vehicle[] vehicles = gson.fromJson(JSON_DB, Vehicle[].class);

				for (Vehicle each : vehicles) {
					data.add(each);
				}

				vadp.notifyDataSetChanged();
				swipeRefreshLayout.setRefreshing(false);

				// TODO: load it from cache or database
				// new GetRequest(R.string.REFRESH).execute(getString(R.string.CM_API_ADDRESS) + "/vehicles");

				// swipeRefreshLayout.setRefreshing(false);
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
		//		if(loadMore && !isLoadingMore && this.data.size() > 0) {
		//
		//			// 1. limiter used to stop the load more feature
		//			if (this.data.size() >= 6) {
		//				return;
		//			}
		//			
		//			this.isLoadingMore = true;
		//
		//			// 2. download additional data
		//			// TODO: load it from cache or database
		//			// new GetRequest(R.string.LOAD_MORE).execute(getString(R.string.CM_API_ADDRESS) + "/vehicles");
		//
		//			this.dialog = new ProgressDialog(getActivity());
		//			this.dialog.setMessage("Loading More Vehicles...");
		//			this.dialog.show();
		//		}
	}

}
