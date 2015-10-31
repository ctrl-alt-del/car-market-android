package com.car_market_android.model;

public class Vehicle {

    private String id;
    private String vin;
    private String manufacturer;
    private String model;
    private String year;
    private String color;
    private String user_id;
    private String photo_id;
    private String created_at;
    private String updated_at;

    public String getId() {
        return this.id;
    }

    public String getVin() {
        return this.vin;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public String getYear() {
        return this.year;
    }


    public String getColor() {
        return this.color;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getPhoto_id() {
        return this.photo_id;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }
}
