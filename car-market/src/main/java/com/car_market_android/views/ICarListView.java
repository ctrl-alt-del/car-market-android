package com.car_market_android.views;

import com.car_market_android.model.Listing;

import java.util.List;

public interface ICarListView extends ICMBaseView {
    void onReceiveCarListSucceed(List<Listing> listings);
    void onReceiveCarListFailed(String errorMessage);
}
