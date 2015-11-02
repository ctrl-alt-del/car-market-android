package com.car_market_android.presenters.impl;

import com.car_market_android.presenters.ICarMarketPresenter;
import com.car_market_android.views.ICarMarketView;

public class CarMarketPresenter<T extends ICarMarketView> implements ICarMarketPresenter<T> {
    private T mView;

    public CarMarketPresenter(T view) {
        mView = view;
    }

    @Override
    public T getView() {
        return mView;
    }
}
