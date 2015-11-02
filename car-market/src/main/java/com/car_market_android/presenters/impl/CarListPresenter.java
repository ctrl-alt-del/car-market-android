package com.car_market_android.presenters.impl;

import com.car_market_android.model.Listing;
import com.car_market_android.network.CarMarketClient;
import com.car_market_android.presenters.ICarListPresenter;
import com.car_market_android.views.ICarListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarListPresenter extends CarMarketPresenter<ICarListView> implements ICarListPresenter {
    public CarListPresenter(ICarListView view) {
        super(view);
    }

    /**
     * Modify the limit and offset parameters to enable the "load more" feature
     */
    @Override
    public void getListings(int limit, int offset) {

        CarMarketClient.getInstance().getListings(limit, offset, new Callback<List<Listing>>() {

            @Override
            public void success(List<Listing> listings, Response response) {
                getView().onReceiveCarListSucceed(listings);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                getView().onReceiveCarListFailed(retrofitError.getMessage());
            }

        });
    }
}
