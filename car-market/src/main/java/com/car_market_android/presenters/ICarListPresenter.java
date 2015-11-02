package com.car_market_android.presenters;

import com.car_market_android.views.ICarListView;

public interface ICarListPresenter extends ICarMarketPresenter<ICarListView> {
    void getListings(int limit, int offset);
}
