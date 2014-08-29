package com.car_market_android.model;

public class Listing {
	
	private int id;
	private String price;
	private String currency;
	private String status;
	private String city;
	private String state;
	private String country;
	private String zip_code;
	private String description;
	private String created_at;
	private String updated_at;
	private Vehicle vehicle;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public Listing setId(int id) {
		this.id = id;
		return this;
	}
	/**
	 * @return this.the price
	 */
	public String getPrice() {
		return this.price;
	}
	/**
	 * @param price the price to set
	 */
	public Listing setPrice(String price) {
		this.price = price;
		return this;
	}
	/**
	 * @return this.the currency
	 */
	public String getCurrency() {
		return this.currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public Listing setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	/**
	 * @return this.the status
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * @param status the status to set
	 */
	public Listing setStatus(String status) {
		this.status = status;
		return this;
	}
	/**
	 * @return this.the city
	 */
	public String getCity() {
		return this.city;
	}
	/**
	 * @param city the city to set
	 */
	public Listing setCity(String city) {
		this.city = city;
		return this;
	}
	/**
	 * @return this.the state
	 */
	public String getState() {
		return this.state;
	}
	/**
	 * @param state the state to set
	 */
	public Listing setState(String state) {
		this.state = state;
		return this;
	}
	/**
	 * @return this.the country
	 */
	public String getCountry() {
		return this.country;
	}
	/**
	 * @param country the country to set
	 */
	public Listing setCountry(String country) {
		this.country = country;
		return this;
	}
	/**
	 * @return this.the zip_code
	 */
	public String getZip_code() {
		return this.zip_code;
	}
	/**
	 * @param zip_code the zip_code to set
	 */
	public Listing setZip_code(String zip_code) {
		this.zip_code = zip_code;
		return this;
	}
	/**
	 * @return this.the description
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * @param description the description to set
	 */
	public Listing setDescription(String description) {
		this.description = description;
		return this;
	}
	/**
	 * @return this.the created_at
	 */
	public String getCreated_at() {
		return this.created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public Listing setCreated_at(String created_at) {
		this.created_at = created_at;
		return this;
	}
	/**
	 * @return this.the updated_at
	 */
	public String getUpdated_at() {
		return this.updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public Listing setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
		return this;
	}
	/**
	 * @return this.the vehicle
	 */
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	/**
	 * @param vehicle the vehicle to set
	 */
	public Listing setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		return this;
	}
	
}
