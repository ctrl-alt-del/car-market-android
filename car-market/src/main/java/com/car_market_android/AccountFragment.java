package com.car_market_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.car_market_android.model.ApiKey;
import com.car_market_android.model.User;
import com.car_market_android.network.ApiInterface;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.util.EventsBus;
import com.car_market_android.util.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AccountFragment extends CarMarketFragment implements OnClickListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button mUserUpdate;
    private Button mSwitchToSignUp;
    private Button mMyVehicles;
    private TextView mEmail;
    private TextView mNickname;
    private View mUserProfileView;

    private SharedPreferences mSharedPreferences;
    private ApiInterface mCarMarketClient;
    private Button mSignIn;

    public AccountFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AccountFragment newInstance(int sectionNumber) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mCarMarketClient = CarMarketClient.getInstance();
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

        mSignIn = (Button) view.findViewById(R.id.sign_in);
        mSwitchToSignUp = (Button) view.findViewById(R.id.sign_up);

        mUserProfileView = view.findViewById(R.id.user_profile_view);
        mUserUpdate = (Button) view.findViewById(R.id.user_update);
        mEmail = (TextView) view.findViewById(R.id.email_profile);
        mNickname = (TextView) view.findViewById(R.id.nickname_profile);
        mMyVehicles = (Button) view.findViewById(R.id.my_vehicles);

        mSignIn.setOnClickListener(this);
        mSwitchToSignUp.setOnClickListener(this);

        updateAccountView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:

                if (getSession().hasSignedInUser()) {
                    getSession().logout();
                    updateAccountView();
                } else {
                    Intent authentication_intent = new Intent(getActivity(), UserAuth.class);
                    startActivity(authentication_intent);
                }
                break;
            case R.id.user_update:
                Intent user_update_intent = new Intent(getActivity(), UserUpdate.class);
                startActivity(user_update_intent);
                break;
            case R.id.sign_up:
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
        if (mSharedPreferences != null) {

            String nickname = mSharedPreferences.getString(getString(R.string.CM_USER_NICKNAME), StringUtils.EMPTY);
            String email = mSharedPreferences.getString(getString(R.string.CM_USER_EMAIL), StringUtils.EMPTY);

            if (!TextUtils.isEmpty(nickname) && !TextUtils.isEmpty(email)) {
                mNickname.setText(nickname);
                mEmail.setText(email);
                return;
            }

            updateAccountView();


            if (getSession().hasSignedInUser()) {

                ApiKey apiKey = getSession().getApiKey();

                showProgressDialog(R.string.please_wait);

                mCarMarketClient.getUser(apiKey.getUserId(), "Token " + apiKey.getToken(), new Callback<User>() {

                    @Override
                    public void success(User user, Response response) {

                        mNickname.setText(user.getNickname());
                        mEmail.setText(user.getEmail());

                        SharedPreferences.Editor editContent = mSharedPreferences.edit();
                        editContent.putString(getString(R.string.CM_USER_NICKNAME), user.getNickname());
                        editContent.putString(getString(R.string.CM_USER_EMAIL), user.getEmail());
                        editContent.apply();

                        updateAccountView();

                        dismissProgressDialog();
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

                        dismissProgressDialog();
                    }

                });
            }
        }
    }

    private void updateAccountView() {
        if (getSession().hasSignedInUser()) {
            mSignIn.setVisibility(View.GONE);
            mSwitchToSignUp.setVisibility(View.GONE);
            mUserProfileView.setVisibility(View.VISIBLE);
        } else {
            mSignIn.setVisibility(View.VISIBLE);
            mSwitchToSignUp.setVisibility(View.VISIBLE);
            mUserProfileView.setVisibility(View.GONE);
        }
    }
}
