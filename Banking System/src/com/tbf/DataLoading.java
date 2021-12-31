package com.tbf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Data Loading Class
 * @author James Nguyen and Thi Hoang
 *
 */
public class DataLoading {

	/**
	 * Loads in a Persons.dat file from the data folder, processes it, and returns a
	 * list of Persons
	 * @return
	 * 
	 */
	public static List<Person> personsDataLoading() {
		List<Person> persons = new ArrayList<>();

		File f = new File("data/Persons.dat");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		int n = s.nextInt();
		s.nextLine();
		for (int i = 0; i < n; i++) {
			String line = s.nextLine();
			String personToken[] = line.split(";");

			Person person = null;

			String name[] = personToken[2].split(",");
			String firstName = name[1];
			String lastName = name[0];
			firstName = firstName.replaceAll("\\s+", "");
			String addressToken[] = personToken[3].split(",");
			Address address = new Address(addressToken[0], addressToken[1], addressToken[2], addressToken[3],
					addressToken[4]);

			if (!personToken[1].equals("")) {
				String brokerToken[] = personToken[1].split(",");
				if (brokerToken[0].equals("E")) {
					person = new ExpertBroker(personToken[0], lastName, firstName, address, brokerToken[1],
							brokerToken[0]);
				} else {
					person = new JuniorBroker(personToken[0], lastName, firstName, address, brokerToken[1],
							brokerToken[0]);
				}
			} else {
				person = new Person(personToken[0], lastName, firstName, address);
			}

			if (personToken.length == 5) {
				String emailToken[] = personToken[4].split(",");

				for (int j = 0; j < emailToken.length; j++) {
					person.addEmail(emailToken[j]);
				}
			}

			persons.add(person);

		}
		s.close();

		return persons;
	}

	/**
	 * Loads in a Asset.dat file, processes it, and returns a list of Assets
	 * @return
	 * 
	 */
	public static List<Asset> assetsDataLoading() {
		List<Asset> assets = new ArrayList<>();

		File f = new File("data/Assets.dat");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		int n = s.nextInt();
		s.nextLine();
		for (int i = 0; i < n; i++) {
			String line = s.nextLine();
			String assetToken[] = line.split(";");

			if (assetToken.length == 4) {
				DepositAccount depositAccount = new DepositAccount(assetToken[0], assetToken[1], assetToken[2],
						Double.parseDouble(assetToken[3]));
				assets.add(depositAccount);
			} else if (assetToken.length == 8) {
				Stock stock = new Stock(assetToken[0], assetToken[1], assetToken[2], Double.parseDouble(assetToken[3]),
						Double.parseDouble(assetToken[4]), Double.parseDouble(assetToken[5]), assetToken[6],
						Double.parseDouble(assetToken[7]));
				assets.add(stock);
			} else if (assetToken.length == 7) {
				PrivateInvestment privateInvestment = new PrivateInvestment(assetToken[0], assetToken[1], assetToken[2],
						Double.parseDouble(assetToken[3]), Double.parseDouble(assetToken[4]),
						Double.parseDouble(assetToken[5]), Double.parseDouble(assetToken[6]));
				assets.add(privateInvestment);
			}
		}
		s.close();

		return assets;
	}

	/**
	 * Loads in a Portfilio.dat file, processes it, and returns a list of Portfolios
	 * @return
	 * 
	 */
	public static List<Portfolio> portfolioDataLoading() {
		List<Person> person = DataLoading.personsDataLoading();
		List<Asset> assets = DataLoading.assetsDataLoading();
		List<Portfolio> portfolios = new ArrayList<>();

		File f = new File("data/Portfolios.dat");
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int n = s.nextInt();
		s.nextLine();
		for (int i = 0; i < n; i++) {
			String line = s.nextLine();
			String token[] = line.split(";");

			Person owner = null;
			Broker manager = null;
			Person beneficiary = null;

			Portfolio portfolio = null;

			int length = token.length - 1;

			for (int l = 0; l < person.size(); l++) {
				if (person.get(l).getPersonCode().equals(token[1])) {
					owner = person.get(l);
				}
				if (person.get(l).getPersonCode().equals(token[2])) {
					manager = (Broker) person.get(l);
				}
				if (length >= 3 && !token[3].contains(":")) {
					if (person.get(l).getPersonCode().equals(token[3])) {
						beneficiary = person.get(l);
					}
				}
			}

			portfolio = new Portfolio(token[0], owner, manager, beneficiary);

			if (token[length].contains(":")) {
				String assetTokens[] = token[length].split(",");
				for (int j = 0; j < assetTokens.length; j++) {
					String asset[] = assetTokens[j].split(":");
					for (int k = 0; k < assets.size(); k++) {
						if (assets.get(k).getCode().equals(asset[0])) {
							Asset a = assets.get(k);
							portfolio.addAssets(a, Double.parseDouble(asset[1]));
							break;
						}
					}
				}
			}

			portfolios.add(portfolio);
		}
		s.close();

		return portfolios;
	}
}
