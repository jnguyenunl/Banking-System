package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database Reading class
 * @author James Nguyen and Thi Hoang
 *
 */
public class DatabaseReading {

	/**
	 * Returns a person from the database given a personId
	 * @param personId
	 * @return
	 * 
	 */
	public static Person getPerson(int personId) {

		Person p = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		//gets the major fields of a person
		String query = "SELECT a.addressId, a.street, a.city, a.zip, a.state, a.country, p.personCode, p.typeOfBroker, p.secCode, "
				     + "p.firstName, p.lastName FROM Address a join Person p on a.addressId = p.addressId WHERE p.personId = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int addressId = rs.getInt("addressId");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String zip = rs.getString("zip");
				String state = rs.getString("state");
				String country = rs.getString("country");
				String personCode = rs.getString("personCode");
				String typeOfBroker = rs.getString("typeOfBroker");
				String secCode = rs.getString("secCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");

				Address a = new Address(addressId, street, city, state, zip, country);

				if (typeOfBroker == null) {
					p = new Person(personId, personCode, lastName, firstName, a);
				} else if (typeOfBroker.equals("J")) {
					p = new JuniorBroker(personId, personCode, lastName, firstName, a, secCode, typeOfBroker);
				} else {
					p = new ExpertBroker(personId, personCode, lastName, firstName, a, secCode, typeOfBroker);
				}

			} else {
				throw new IllegalStateException("no such Person with personId = " + personId);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		//gets the email(s) of the person
		query = "SELECT address FROM Email WHERE personId = ?";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String address = rs.getString("address");
				p.addEmail(address);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return p;
	}

	/**
	 * Returns a portfolios from the database given its portfolioId
	 * @param portfolioId
	 * @return
	 */
	public static Asset getAsset(int assetId) {

		Asset a = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "SELECT assetCode, typeOfAsset, label, stockSymbol, apr, quarterlyDividend, "
			  +        "baseRateOfReturn, betaMeasure, baseOmegaMeasure, totalValue, sharePrice FROM Asset "
			  +        "WHERE assetId = ?";
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, assetId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String assetCode = rs.getString("assetCode");
				String typeOfAsset = rs.getString("typeOfAsset");
				String label = rs.getString("label");

				if (typeOfAsset.equals("D")) {
					double apr = rs.getDouble("apr");
					a = new DepositAccount(assetId, assetCode, typeOfAsset, label, apr);

				} else if (typeOfAsset.equals("S")) {
					double quarterlyDividend = rs.getDouble("quarterlyDividend");
					double baseRateOfReturn = rs.getDouble("baseRateOfReturn");
					double betaMeasure = rs.getDouble("betaMeasure");
					String stockSymbol = rs.getString("stockSymbol");
					double sharePrice = rs.getDouble("sharePrice");

					a = new Stock(assetId, assetCode, typeOfAsset, label, quarterlyDividend, baseRateOfReturn,
							betaMeasure, stockSymbol, sharePrice);

				} else {
					double quarterlyDividend = rs.getDouble("quarterlyDividend");
					double baseRateOfReturn = rs.getDouble("baseRateOfReturn");
					double baseOmegaMeasure = rs.getDouble("baseOmegaMeasure");
					double totalValue = rs.getDouble("totalValue");

					a = new PrivateInvestment(assetId, assetCode, typeOfAsset, label, quarterlyDividend,
							baseRateOfReturn, baseOmegaMeasure, totalValue);

				}
				
			} else {
				throw new IllegalStateException("no such Asset with assetId = " + assetId);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return a;
	}
	/**
	 * Returns a portfolios from the database given its portfolioId
	 * @param portfolioId
	 * @return
	 */
	public static Portfolio getPortfolio(int portfolioId) {

		Portfolio p = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		//gets the portfolio 
		String query = "SELECT portfolioId, portfolioCode, ownerId, managerId, beneficiaryId FROM Portfolio WHERE portfolioId = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, portfolioId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String portfolioCode = rs.getString("portfolioCode");
				int ownerId = rs.getInt("ownerId");
				int managerId = rs.getInt("managerId");
				int beneficiaryId = rs.getInt("beneficiaryId");

				Person owner = DatabaseReading.getPerson(ownerId);
				Broker manager = (Broker) DatabaseReading.getPerson(managerId);
				Person beneficiary = null;

				if (beneficiaryId != 0) {
					beneficiary = DatabaseReading.getPerson(beneficiaryId);
				}

				p = new Portfolio(portfolioId, portfolioCode, owner, manager, beneficiary);

			} else {
				throw new IllegalStateException("no such Porfolio with porfolioId = " + portfolioId);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		//gets the asset(s) of the portfolio and maps it to its asset value
		query = "SELECT a.assetId, ap.assetValue FROM AssetPortfolio ap JOIN Asset a ON "
			+   "ap.assetId = a.assetId WHERE ap.portfolioId = ?";

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, portfolioId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Asset a = getAsset(rs.getInt("assetId"));
				double assetValue = rs.getDouble("assetValue");
				p.addAssets(a, assetValue);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return p;
	}
	
	/**
	 * Returns a list of portfolios from the database
	 * @return
	 * 
	 */
	public static List<Portfolio> getAllPortfolios() {

		List<Portfolio> p = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "SELECT portfolioId FROM Portfolio";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int portfolioId = rs.getInt("portfolioId");
				p.add(getPortfolio(portfolioId));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return p;
	}
	
	/**
	 * Returns a list of persons from the database
	 * @return
	 * 
	 */
	public static List<Person> getAllPersons() {

		List<Person> p = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "SELECT personId FROM Person";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int personId = rs.getInt("personId");
				p.add(getPerson(personId));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return p;
	}
	
	
	/**
	 * Returns a list of persons from the database
	 * @return
	 * 
	 */
	public static List<Asset> getAllAssets() {

		List<Asset> a = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "SELECT assetId FROM Asset";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				int assetId = rs.getInt("assetUd");
				a.add(getAsset(assetId));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return a;
	}
	
}
