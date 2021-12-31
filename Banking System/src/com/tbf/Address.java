package com.tbf;

/**
 * Address Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class Address {
	private int addressId;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;

	public Address(int addressId, String street, String city, String state, String zip, String country) {
		this.addressId = addressId;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public int getAddressId() {
		return addressId;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	public String toString() {
		return street + '\n' + city + ", " + state + ' ' + country + ' ' + zip;
	}

}
