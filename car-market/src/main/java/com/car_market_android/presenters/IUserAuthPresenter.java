package com.car_market_android.presenters;

import com.car_market_android.views.IUserAuthView;

public interface IUserAuthPresenter extends ICarMarketPresenter<IUserAuthView> {
    void signIn(String email, String password);
}
