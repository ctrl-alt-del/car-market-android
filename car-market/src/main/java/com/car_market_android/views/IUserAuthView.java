package com.car_market_android.views;

import com.car_market_android.model.ApiKey;
import com.car_market_android.model.Listing;

import java.util.List;

public interface IUserAuthView extends ICarMarketView {
    void onSignInSucceed(ApiKey apiKey);
    void onSignInFailed(String errorMessage);
}
