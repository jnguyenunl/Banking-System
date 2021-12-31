package com.tbf;

import java.util.List;

/**
 * Given a Persons.dat and Assets.dat files, processes the information in both
 * files, converts it to XML format, and outputs it to a XML file
 * @authors James Nguyen and Thi Hoang
 *
 */
public class DataConverter {

	public static void main(String args[]) {

		List<Person> persons = DataLoading.personsDataLoading();
		DataSerialization.personsToXmL(persons);

		List<Asset> assets = DataLoading.assetsDataLoading();
		DataSerialization.assetsToXml(assets);

	}
}
