package com.car_market_android.application;

import android.content.Context;
import android.text.TextUtils;

import com.car_market_android.model.ApiKey;
import com.car_market_android.model.Vehicle;
import com.car_market_android.util.LogUtils;
import com.car_market_android.util.SessionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Session {

    private Context mContext;
    private ApiKey mApiKey;
    private Set<String> mVehicleVinWishList = new HashSet<>();
    private ArrayList<Vehicle> mVehicleWishList = new ArrayList<>();
    private boolean mVehicleWishListUpdated = false;

    public Session(Context context) {
        mContext = context;
        restore();
    }

    private void restore() {
        try {
            SessionUtils.restoreApiKey(mContext, this);
            SessionUtils.restoreVehicleWishList(mContext, this);
        } catch (Exception e) {
            LogUtils.debug(e.getMessage());
        }
    }

    public CarMarketApplication getCarMarketApplication() {
        return (CarMarketApplication) mContext;
    }

    public void saveApiKey(ApiKey apiKey) {
        setApiKey(apiKey);
        SessionUtils.saveApiKey(mContext, mApiKey);
    }

    public void setApiKey(ApiKey apiKey) {
        mApiKey = apiKey;
    }

    public boolean hasSignedInUser() {
        return mApiKey != null;
    }

    public ApiKey getApiKey() {
        return mApiKey;
    }

    public void logout() {
        mApiKey = null;
        SessionUtils.logout(mContext);
    }

    public boolean isVehicleInWishList(String vin) {
        return mVehicleVinWishList.contains(vin);
    }

    public List<Vehicle> getVehicleWishList() {
        return mVehicleWishList;
    }

    public void setVehicleWishList(Vehicle[] vehicleWishList) {
        for (Vehicle vehicle : vehicleWishList) {
            mVehicleWishList.add(vehicle);
            mVehicleVinWishList.add(vehicle.getVin());
        }
    }

    public void setVehicleWishList(List<Vehicle> vehicleWishList) {
        for (Vehicle vehicle : vehicleWishList) {
            mVehicleWishList.add(vehicle);
            mVehicleVinWishList.add(vehicle.getVin());
        }
    }

    public void addVehicleToWishList(Vehicle vehicle) {
        final String vin = vehicle.getVin();
        if (TextUtils.isEmpty(vin)) {
            return;
        }

        if (!isVehicleInWishList(vin)) {
            mVehicleWishList.add(vehicle);
            mVehicleVinWishList.add(vin);
            SessionUtils.saveVehicleWishList(mContext, mVehicleWishList);
        }
    }

    public void removeVehicleToWishList(String vin) {

        if (TextUtils.isEmpty(vin)) {
            return;
        }

        if (isVehicleInWishList(vin)) {
            for (Vehicle vehicle : mVehicleWishList) {
                if (vin.equals(vehicle.getVin())) {
                    mVehicleWishList.remove(vehicle);
                    mVehicleVinWishList.remove(vin);
                    SessionUtils.saveVehicleWishList(mContext, mVehicleWishList);
                    return;
                }
            }
        }
    }

    public void setVehicleWishListUpdated(boolean vehicleWishListUpdated) {
        mVehicleWishListUpdated = vehicleWishListUpdated;
    }

    public boolean isVehicleWishListUpdated() {
        return mVehicleWishListUpdated;
    }
}
