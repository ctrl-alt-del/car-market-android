package com.car_market_android.model;

public class Vehicle {

    private int id;
    private String vin;
    private String manufacturer;
    private String model;
    private String year;
    private String color;
    private String user_id;
    private String photo_id;
    private String created_at;
    private String updated_at;


    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public Vehicle setId(int id) {
        this.id = id;
        return this;
    }

    public String getVin() {
        return this.vin;
    }

    public Vehicle setVin(String vin) {
        this.vin = vin;
        return this;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public Vehicle setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    public Vehicle setModel(String model) {
        this.model = model;
        return this;
    }

    public String getYear() {
        return this.year;
    }

    public Vehicle setYear(String year) {
        this.year = year;
        return this;
    }

    public String getColor() {
        return this.color;
    }

    public Vehicle setColor(String color) {
        this.color = color;
        return this;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public Vehicle setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getPhoto_id() {
        return this.photo_id;
    }

    public Vehicle setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
        return this;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public Vehicle setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getUpdated_at() {
        return this.updated_at;
    }

    public Vehicle setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }
}
