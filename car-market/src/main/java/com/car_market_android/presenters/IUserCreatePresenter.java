package com.car_market_android.presenters;

import com.car_market_android.views.IUserCreateView;

public interface IUserCreatePresenter extends ICarMarketPresenter<IUserCreateView> {
    void signUp(String nickname, String firstName, String lastName, String email, String password, String status);
}
