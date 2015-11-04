package com.car_market_android.presenters.impl;

import android.text.TextUtils;

import com.car_market_android.BuildConfig;
import com.car_market_android.model.ApiKey;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.presenters.IUserCreatePresenter;
import com.car_market_android.views.IUserCreateView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserCreatePresenter extends CarMarketPresenter<IUserCreateView> implements IUserCreatePresenter {
    public UserCreatePresenter(IUserCreateView view) {
        super(view);
    }

    @Override
    public void signUp(String nickname, String firstName, String lastName, String email, String password, String status) {
        CarMarketClient.getInstance().createUser(nickname, firstName, lastName, email, password, password, status, new Callback<ApiKey>() {

            @Override
            public void failure(RetrofitError retrofitError) {
                String errorMessage = "Connection failed, please try again :(";

                /**
                 * This message is for debug mode.
                 * */
                if (BuildConfig.DEBUG) {
                    errorMessage = retrofitError.getMessage();
                }
                getView().onSignUpFailed(errorMessage);
            }

            @Override
            public void success(ApiKey apiKey, Response response) {

                if (!TextUtils.isEmpty(apiKey.getMessage())) {
                    getView().onSignUpFailed(apiKey.getMessage());
                    return;
                }

                getView().onSignUpSucceed(apiKey);
            }
        });
    }
}
