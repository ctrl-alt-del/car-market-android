package com.car_market_android;

import org.apache.commons.lang3.StringUtils;

import com.car_market_android.model.Vehicle;
import com.car_market_android.util.EventsBus;
import com.car_market_android.util.GetRequest;
import com.car_market_android.util.GetRequestResultEvent;
import com.car_market_android.util.PostRequestResultEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AuthFragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";


	private Button Authentication;
	private Button Show_Vehicles;
	private TextView Json_result;
	private TextView Email_profile;
	private TextView Nickname_profile;
	private RelativeLayout User_layout;

	private SharedPreferences sharedPreferences;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static AuthFragment newInstance(int sectionNumber) {
		AuthFragment fragment = new AuthFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AuthFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		EventsBus.getInstance().register(this);

		View rootView = inflater.inflate(R.layout.fragment_auth, container, false);

		this.Authentication = (Button) rootView.findViewById(R.id.authentication);
		this.Show_Vehicles = (Button) rootView.findViewById(R.id.show_vehicles);
		this.Json_result = (TextView) rootView.findViewById(R.id.json_result);

		this.User_layout = (RelativeLayout) rootView.findViewById(R.id.user_layout);
		this.Email_profile = (TextView) rootView.findViewById(R.id.email_profile);
		this.Nickname_profile = (TextView) rootView.findViewById(R.id.nickname_profile);

		this.Authentication.setOnClickListener(this);
		this.Show_Vehicles.setOnClickListener(this);

		if (sharedPreferences.contains(getString(R.string.CM_API_TOKEN))) {
			// TODO: make POST call to get user profile
			
			this.setUserView();
		} else {
			this.Json_result.setText("Json Result");
			this.setGusetView();
		}


		return rootView;
	}

	private ProgressDialog dialog;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.authentication:

			if (sharedPreferences.contains(getString(R.string.CM_API_TOKEN))) {
				this.sharedPreferences.edit().remove(getString(R.string.CM_API_TOKEN)).commit();

				this.Json_result.setText("Json Result");
				this.setGusetView();
			} else {
				Intent myIntent = new Intent(getActivity(), Authentication.class);
				startActivity(myIntent);
			}
			break;
		case R.id.show_vehicles:

			new GetRequest(R.id.show_vehicles).execute(getString(R.string.CM_API_ADDRESS) + "/vehicles");
			this.dialog = new ProgressDialog(getActivity());
			this.dialog.setMessage("Loding...");
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

	@Override
	public void onResume() {
		super.onResume();
		if (sharedPreferences != null) {
			
			if (!sharedPreferences.contains(getString(R.string.CM_API_TOKEN)) || 
					!sharedPreferences.contains(getString(R.string.CM_API_USER_ID))) {

				this.Json_result.setText("Json Result");
				this.setGusetView();
				return;
			}
			
			String token = sharedPreferences.getString(getString(R.string.CM_API_TOKEN), "");
			long user_id = sharedPreferences.getLong(getString(R.string.CM_API_USER_ID), -1);
			
			if (StringUtils.isBlank(token) || user_id == -1) {
				this.Json_result.setText("Json Result");
				this.setGusetView();
				return;
			}
			
			// TODO: make POST call to get user profile
			if (StringUtils.isBlank(this.Email_profile.getText()) || 
					StringUtils.isBlank(this.Nickname_profile.getText())) {

			}

			this.Json_result.setText(token + "\n" + user_id);
			this.setUserView();

		}
	}

	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {
		switch (event.getCaller()) {
		case R.id.show_vehicles:

			((MainActivity) getActivity()).setProfileResult(event.getResult());
			//			((MainActivity) getActivity()).getActionBar().setSelectedNavigationItem(1);
			Gson gson = new GsonBuilder().create();
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

		Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		case R.id.show_vehicles:
			break;
		default:
			break;
		}
	}

	private void setUserView() {
		this.Authentication.setText("Sign Out");
		LayoutParams params = this.User_layout.getLayoutParams();
		params.height = LayoutParams.WRAP_CONTENT;
		this.User_layout.setLayoutParams(params);
	}

	private void setGusetView() {
		this.Authentication.setText("Sign In");
		LayoutParams params = this.User_layout.getLayoutParams();
		params.height = 0;
		this.User_layout.setLayoutParams(params);
	}

}
