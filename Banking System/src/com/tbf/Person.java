package com.tbf;

import java.util.ArrayList;
import java.util.List;

/**
 * Person Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class Person {
	private int personId;
	private String personCode;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> emails = new ArrayList<>();

	public Person(int personId, String personCode, String lastName, String firstName, Address address) {
		this.personId = personId;
		this.personCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
	}

	public Person(String personCode, String lastName, String firstName, Address address) {
		this.personCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
	}

	public int getPersonId() {
		return personId;
	}

	public String getPersonCode() {
		return personCode;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAdress() {
		return address;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void addEmail(String address) {
		this.emails.add(address);
	}

	public String toString() {
		return lastName + ", " + firstName + '\n' + emails + '\n' + address;
	}

	public String getType() {
		return "";
	}

	public String getSecCode() {
		return "";
	}

}
