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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MarketplaceFragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private Button Show_Vehicles;
	private TextView Json_result;

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

		View rootView = inflater.inflate(R.layout.fragment_marketplace, container, false);

		this.Show_Vehicles = (Button) rootView.findViewById(R.id.show_vehicles);
		this.Json_result = (TextView) rootView.findViewById(R.id.json_result);

		this.Json_result.setText("MarketplaceFragment");
		this.Show_Vehicles.setOnClickListener(this);

		return rootView;
	}

	private ProgressDialog dialog;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.show_vehicles:

			new GetRequest(R.id.show_vehicles).execute(getString(R.string.CM_API_ADDRESS) + "/vehicles");
			this.dialog = new ProgressDialog(getActivity());
			this.dialog.setMessage("Loding Vehicles...");
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
}
