package com.tbf;

/**
 * Broker Class
 * @author James Nguyen and Thi Hoang
 *
 */
public abstract class Broker extends Person{
	private String secCode;
	
	public Broker(int personId, String personCode, String lastName, String firstName, Address address, String secCode) {
		super(personId,personCode, lastName, firstName, address);
		this.secCode = secCode;
	}
	
	public Broker(String personCode, String lastName, String firstName, Address address,  String secCode) {
		super(personCode, lastName, firstName, address);
		this.secCode = secCode;
	}
	
	public String getSecCode() {
		return secCode;
	}
	
	public String toString() {
		return "Broker [secCode=" + secCode + "]";
	}
	
	public abstract double getFee(int assetsOwned);
	public abstract double getCommission(double annualReturn);
	
}
