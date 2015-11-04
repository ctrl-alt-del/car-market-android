package com.car_market_android.views;

import com.car_market_android.model.ApiKey;

public interface IUserCreateView extends ICarMarketView {
    void onSignUpSucceed(ApiKey apiKey);
    void onSignUpFailed(String errorMessage);
}
