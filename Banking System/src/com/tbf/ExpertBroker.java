package com.tbf;

/**
 * Expert Broker Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class ExpertBroker extends Broker {
	private String type;

	public ExpertBroker(int personId, String personCode, String lastName, String firstName, Address address,
			String secCode, String type) {
		super(personId, personCode, lastName, firstName, address, secCode);
		this.type = type;

	}

	public ExpertBroker(String personCode, String lastName, String firstName, Address address, String secCode,
			String type) {
		super(personCode, lastName, firstName, address, secCode);
		this.type = type;

	}

	public String getType() {
		return type;
	}

	public String toString() {
		return this.getLastName() + ", " + this.getFirstName() + '\n' + "Expert Broker\n" + this.getEmails() + '\n'
				+ this.getAdress();
	}

	public double getFee(int assetsOwned) {
		return 0.00;
	}
	
	public double getCommission(double annualReturn) {
		return .0375 * annualReturn;
	}

}
