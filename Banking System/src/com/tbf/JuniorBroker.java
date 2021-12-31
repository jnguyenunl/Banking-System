package com.tbf;

/**
 * Junior Broker Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class JuniorBroker extends Broker {
	private String type;

	public JuniorBroker(int personId, String personCode, String lastName, String firstName, Address address,
			String secCode, String type) {
		super(personId, personCode, lastName, firstName, address, secCode);
		this.type = type;

	}

	public JuniorBroker(String personCode, String lastName, String firstName, Address address, String secCode,
			String type) {
		super(personCode, lastName, firstName, address, secCode);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		return this.getLastName() + ", " + this.getFirstName() + '\n' + "Junior Broker\n" + this.getEmails() + '\n'
				+ this.getAdress();
	}
	
	public double getFee(int assetsOwned) {
		return 75.00 * assetsOwned;
	}
	
	public double getCommission(double annualReturn) {
		return .0125 * annualReturn;
	}

}
