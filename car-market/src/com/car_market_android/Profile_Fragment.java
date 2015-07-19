package com.car_market_android;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.car_market_android.model.User;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.util.EventsBus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Profile_Fragment extends CarMarketFragment implements OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    //	private Button Authentication;
    private EditText etNickname;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirmation;
    private Button btSwitchToAuthentication;
    private boolean isAuthenticationView = false;

    private Button User_update;
    private Button mSwitchToSignUp;
    private Button My_vehicles;
    private TextView Email_profile;
    private TextView Nickname_profile;
    private RelativeLayout User_layout;

    private SharedPreferences sharedPreferences;
    private ProgressDialog dialog;

    public Profile_Fragment() {
    }

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        EventsBus.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        etNickname = (EditText) view.findViewById(R.id.nickname_user_create);
//        etEmail = (EditText) view.findViewById(R.id.email_user_create);
//        etPassword = (EditText) view.findViewById(R.id.password_user_create);
//        etPasswordConfirmation = (EditText) view.findViewById(R.id.password_confirmation_user_create);
//
        mSwitchToSignUp = (Button) view.findViewById(R.id.registration);
//
//        btSwitchToAuthentication = (Button) view.findViewById(R.id.switch_to_authentication);
//        btSwitchToAuthentication.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setAuthenticationView();
//            }
//        });
//
//        setRegistrationView();
//
//
////        this.Authentication = (Button) view.findViewById(R.id.authentication);
//        this.User_update = (Button) view.findViewById(R.id.user_update);
//
//        this.My_vehicles = (Button) view.findViewById(R.id.my_vehicles);
//
//        this.User_layout = (RelativeLayout) view.findViewById(R.id.user_profile_layout);
//        this.Email_profile = (TextView) view.findViewById(R.id.email_profile);
//        this.Nickname_profile = (TextView) view.findViewById(R.id.nickname_profile);
//
////		this.Authentication.setOnClickListener(this);
//        this.User_update.setOnClickListener(this);
//        this.mSwitchToSignUp.setOnClickListener(this);
//        this.My_vehicles.setOnClickListener(this);
//
//        if (sharedPreferences.contains(getString(R.string.CM_API_TOKEN))) {
//            this.setUserView();
//        } else {
//            this.setGusetView();
//        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//		case R.id.authentication:
//
//			if (sharedPreferences.contains(getString(R.string.CM_API_TOKEN))) {
//				this.sharedPreferences.edit().remove(getString(R.string.CM_API_TOKEN)).commit();
//				this.sharedPreferences.edit().remove(getString(R.string.CM_USER_NICKNAME)).commit();
//				this.sharedPreferences.edit().remove(getString(R.string.CM_USER_EMAIL)).commit();
//
//				this.setGusetView();
//			} else {
//				Intent authentication_intent = new Intent(getActivity(), UserAuth.class);
//				startActivity(authentication_intent);
//			}
//			break;
            case R.id.user_update:
                Intent user_update_intent = new Intent(getActivity(), UserUpdate.class);
                startActivity(user_update_intent);
                break;
//            case R.id.sign_up_or_sign_in_button:
//                Intent registration_intent = new Intent(getActivity(), UserCreate.class);
//                startActivity(registration_intent);
//                break;
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

            if (TextUtils.isEmpty(token) || user_id == -1) {
                this.setGusetView();
                return;
            }

            String nickname = sharedPreferences.getString(getString(R.string.CM_USER_NICKNAME), "");
            String email = sharedPreferences.getString(getString(R.string.CM_USER_EMAIL), "");

            if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(email)) {

                this.Nickname_profile.setText(nickname);
                this.Email_profile.setText(email);

                this.setUserView();

                return;
            }


            this.dialog = new ProgressDialog(getActivity());
            this.dialog.setMessage("Loading Profile...");
            this.dialog.show();

            CarMarketClient.getInstance(getActivity()).getUser(user_id, "Token " + token, new Callback<User>() {

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
        }
    }

    private void setUserView() {
//		this.Authentication.setText("Sign Out");
        LayoutParams params = this.User_layout.getLayoutParams();
        params.height = LayoutParams.WRAP_CONTENT;
        this.User_layout.setLayoutParams(params);

        this.mSwitchToSignUp.setVisibility(View.INVISIBLE);
        this.mSwitchToSignUp.setEnabled(false);
    }

    private void setGusetView() {
//		this.Authentication.setText("Sign In");
        LayoutParams params = this.User_layout.getLayoutParams();
        params.height = 0;
        this.User_layout.setLayoutParams(params);

        this.mSwitchToSignUp.setVisibility(View.VISIBLE);
        this.mSwitchToSignUp.setEnabled(true);
    }

//    public void setRegistrationView() {
//        etNickname.setVisibility(View.VISIBLE);
//        etEmail.setVisibility(View.VISIBLE);
//        etPassword.setVisibility(View.VISIBLE);
//        etPasswordConfirmation.setVisibility(View.VISIBLE);
//        mSwitchToSignUp.setText("Sign Up");
//        isAuthenticationView = false;
//        btSwitchToAuthentication.setVisibility(View.VISIBLE);
//    }
//
//    public void setAuthenticationView() {
//        etNickname.setVisibility(View.GONE);
//        etPasswordConfirmation.setVisibility(View.GONE);
//        mSwitchToSignUp.setText("Sign In");
//        isAuthenticationView = true;
//        btSwitchToAuthentication.setVisibility(View.GONE);
//    }
//
//    private void setRegistrationAction() {
//        MessageUtils.showToastShort(getContext(), "setRegistrationAction() called");
//    }
//
//    private void setAuthenticationAction() {
//        MessageUtils.showToastShort(getContext(), "setAuthenticationAction() call");
//    }
//
//    public boolean goBackToRegistrationView() {
//        if (isAuthenticationView) {
//            setRegistrationView();
//            return false;
//        } else {
//            return true;
//        }
//    }
}
