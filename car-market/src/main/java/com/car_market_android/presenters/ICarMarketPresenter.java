package com.car_market_android.presenters;

import com.car_market_android.views.ICarMarketView;

public interface ICarMarketPresenter<T extends ICarMarketView>{
    T getView();
}
