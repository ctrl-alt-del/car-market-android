package com.car_market_android.presenters.impl;

import android.text.TextUtils;

import com.car_market_android.BuildConfig;
import com.car_market_android.model.ApiKey;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.presenters.IUserAuthPresenter;
import com.car_market_android.views.IUserAuthView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserAuthPresenter extends CarMarketPresenter<IUserAuthView> implements IUserAuthPresenter {
    public UserAuthPresenter(IUserAuthView view) {
        super(view);
    }

    @Override
    public void signIn(String email, String password) {
        CarMarketClient.getInstance().signin(
                email, password, new Callback<ApiKey>() {

                    @Override
                    public void success(ApiKey apiKey, Response response) {

                        if (TextUtils.isEmpty(apiKey.getToken())) {
                            getView().onSignInFailed("unable to sign in, make sure your email and password are correct.");
                            return;
                        }
                        getView().onSignInSucceed(apiKey);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                        String errorMessage = "Connection failed, please try again :(";

                        /**
                         * This message is for debug mode.
                         * */
                        if (BuildConfig.DEBUG) {
                            errorMessage = retrofitError.getMessage();
                        }
                        getView().onSignInFailed(errorMessage);
                    }

                });
    }
}
