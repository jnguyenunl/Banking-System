package com.tbf;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.XStream;

/**
 * Data Serialization Utilities Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class DataSerialization {

	/**
	 * Given an list of objects, converts each object into XML format and
	 * concatenates it to a string
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> String xmlToString(Collection<T> t) {
		XStream xstream = new XStream();

		xstream.alias("asset", Stock.class);
		xstream.alias("asset", PrivateInvestment.class);
		xstream.alias("asset", DepositAccount.class);
		xstream.alias("asset", Asset.class);
		xstream.alias("person", JuniorBroker.class);
		xstream.alias("person", Person.class);
		xstream.alias("person", ExpertBroker.class);

		String formattedXml = "";
		for (T a : t) {
			String xml = xstream.toXML(a);
			formattedXml += '\n' + xml;
		}

		return formattedXml;
	}

	/**
	 * Outputs a person list to xml
	 * @param person
	 * 
	 */
	public static void personsToXmL(List<Person> persons) {
		try {
			PrintWriter pw = new PrintWriter("data/Persons.xml");
			pw.print("<?xml version=\"1.0\"?>" + '\n' + '<' + "persons" + '>');
			pw.println(DataSerialization.xmlToString(persons));
			pw.println("</" + "persons" + '>');
			pw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Outputs a asset list to xml
	 * @param asset
	 * 
	 */
	public static void assetsToXml(List<Asset> assets) {
		try {
			PrintWriter pw = new PrintWriter("data/Assets.xml");
			pw.print("<?xml version=\"1.0\"?>" + '\n' + '<' + "assets" + '>');
			pw.println(DataSerialization.xmlToString(assets));
			pw.println("</" + "assets" + '>');
			pw.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
