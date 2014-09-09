package com.car_market_android;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.car_market_android.model.User;
import com.car_market_android.network.ApiClient;
import com.car_market_android.network.GetRequest;
import com.car_market_android.network.GetRequestResultEvent;
import com.car_market_android.network.PostRequestResultEvent;
import com.car_market_android.util.EventsBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Subscribe;

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


public class Profile_Fragment extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";


	private Button Authentication;
	private Button User_update;
	private Button Registration;
	private Button My_vehicles;
	private TextView Email_profile;
	private TextView Nickname_profile;
	private RelativeLayout User_layout;

	private SharedPreferences sharedPreferences;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static Profile_Fragment newInstance(int sectionNumber) {
		Profile_Fragment fragment = new Profile_Fragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public Profile_Fragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		EventsBus.getInstance().register(this);

		View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

		this.Authentication = (Button) rootView.findViewById(R.id.authentication);
		this.User_update = (Button) rootView.findViewById(R.id.user_update);
		this.Registration = (Button) rootView.findViewById(R.id.registration);
		this.My_vehicles = (Button) rootView.findViewById(R.id.my_vehicles);

		this.User_layout = (RelativeLayout) rootView.findViewById(R.id.user_layout);
		this.Email_profile = (TextView) rootView.findViewById(R.id.email_profile);
		this.Nickname_profile = (TextView) rootView.findViewById(R.id.nickname_profile);

		this.Authentication.setOnClickListener(this);
		this.User_update.setOnClickListener(this);
		this.Registration.setOnClickListener(this);
		this.My_vehicles.setOnClickListener(this);

		if (sharedPreferences.contains(getString(R.string.CM_API_TOKEN))) {
			this.setUserView();
		} else {
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
				this.sharedPreferences.edit().remove(getString(R.string.CM_USER_NICKNAME)).commit();
				this.sharedPreferences.edit().remove(getString(R.string.CM_USER_EMAIL)).commit();
				
				this.setGusetView();
			} else {
				Intent authentication_intent = new Intent(getActivity(), UserAuth.class);
				startActivity(authentication_intent);
			}
			break;
		case R.id.user_update:
			Intent user_update_intent = new Intent(getActivity(), UserUpdate.class);
			startActivity(user_update_intent);
			break;
		case R.id.registration:
			Intent registration_intent = new Intent(getActivity(), UserCreate.class);
			startActivity(registration_intent);
			break;
		case R.id.my_vehicles:
			Intent myvehicle_intent = new Intent(getActivity(), MyVehicle.class);
			startActivity(myvehicle_intent);
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

			String token = sharedPreferences.getString(getString(R.string.CM_API_TOKEN), "");
			long user_id = sharedPreferences.getLong(getString(R.string.CM_API_USER_ID), -1);

			if (StringUtils.isBlank(token) || user_id == -1) {
				this.setGusetView();
				return;
			}
			
			String nickname = sharedPreferences.getString(getString(R.string.CM_USER_NICKNAME), "");
			String email = sharedPreferences.getString(getString(R.string.CM_USER_EMAIL), "");
			
			if (StringUtils.isBlank(nickname) || StringUtils.isBlank(email)) {
				
				new GetRequest(R.string.SIGN_IN)
				.setAuthToken(token) //"7c14a5e93644b85923df1d90d8c2dcf7"
				.execute(getString(R.string.CM_API_ADDRESS) + "/users/" + user_id);
				
				this.dialog = new ProgressDialog(getActivity());
				this.dialog.setMessage("Loading Profile...");
				this.dialog.show();
				
				ApiClient.getApiClient(getActivity()).getUser(user_id, "Token " + token, new Callback<User>() {

					@Override
					public void success(User user, Response response) {
						
						Nickname_profile.setText(user.getNickname());
						Email_profile.setText(user.getEmail());
						
						sharedPreferences.edit().putString(getString(R.string.CM_USER_NICKNAME), user.getNickname()).commit();
						sharedPreferences.edit().putString(getString(R.string.CM_USER_EMAIL), user.getEmail()).commit();

						setUserView();

						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}

					@Override
					public void failure(RetrofitError retrofitError) {
						/**
						 * This message is for debug mode.
						 * */
						Toast.makeText(getActivity(), retrofitError.getMessage(), Toast.LENGTH_SHORT).show();
						/**
						 * This message is for production mode.
						 * */
						Toast.makeText(getActivity(), "Connection failed, please try again :(", Toast.LENGTH_SHORT).show();
						
						if (dialog.isShowing()) {
							dialog.dismiss();
						}
					}

				});
				
				return;
			} else {
				
				this.Nickname_profile.setText(nickname);
				this.Email_profile.setText(email);
				
				this.setUserView();

				return;
			}
		}
	}

	@Subscribe
	public void onGetRequestTaskResult(GetRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		case R.string.SIGN_IN:

			User user = gson.fromJson(event.getResult(), User.class);

			this.Nickname_profile.setText(user.getNickname());
			this.Email_profile.setText(user.getEmail());
			
			this.sharedPreferences.edit().putString(getString(R.string.CM_USER_NICKNAME), user.getNickname()).commit();
			this.sharedPreferences.edit().putString(getString(R.string.CM_USER_EMAIL), user.getEmail()).commit();

			this.setUserView();

			if (dialog.isShowing()) {
				dialog.dismiss();
			}

			break;
		default:
			break;
		}
	}

	@Subscribe
	public void onPostRequestTaskResult(PostRequestResultEvent event) {

		Gson gson = new GsonBuilder().create();

		switch (event.getCaller()) {
		default:
			break;
		}
	}

	private void setUserView() {
		this.Authentication.setText("Sign Out");
		LayoutParams params = this.User_layout.getLayoutParams();
		params.height = LayoutParams.WRAP_CONTENT;
		this.User_layout.setLayoutParams(params);
		
		this.Registration.setVisibility(View.INVISIBLE);
		this.Registration.setEnabled(false);
	}

	private void setGusetView() {
		this.Authentication.setText("Sign In");
		LayoutParams params = this.User_layout.getLayoutParams();
		params.height = 0;
		this.User_layout.setLayoutParams(params);
		
		this.Registration.setVisibility(View.VISIBLE);
		this.Registration.setEnabled(true);
	}

}
