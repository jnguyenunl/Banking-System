package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 */
public class PortfolioData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() {

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

		String query = "DELETE FROM AssetPortfolio";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Portfolio";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Email";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Person";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Removes the person record from the database corresponding to the provided
	 * <code>personCode</code>.
	 * @param personCode
	 */
	public static void removePerson(String personCode) {

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

		String query = "DELETE FROM AssetPortfolio WHERE portfolioId in "
				+ "(SELECT portfolioId FROM Portfolio WHERE ownerId = "
				+ "(SELECT personId FROM Person WHERE personCode = ?) or "
				+ "managerId = (SELECT personId FROM Person WHERE personCode = ?) or "
				+ "beneficiaryId = (SELECT personId FROM Person WHERE personCode = ?))";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, personCode);
			ps.setString(3, personCode);
			ps.executeUpdate();

			query = "DELETE FROM Portfolio WHERE " + "ownerId = (SELECT personId FROM Person WHERE personCode = ?) or "
					+ "managerId = (SELECT personId FROM Person WHERE personCode = ?) or "
					+ "beneficiaryId = (SELECT personId FROM Person WHERE personCode = ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, personCode);
			ps.setString(3, personCode);
			ps.executeUpdate();

			query = "DELETE FROM Email WHERE personId = (SELECT personId FROM Person WHERE personCode = ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.executeUpdate();

			query = "DELETE FROM Person WHERE personCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>brokerType</code> will either be "E" or "J" (Expert or Junior) or
	 * <code>null</code> if the person is not a broker.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param brokerType
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country, String brokerType, String secBrokerId) {

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

		int addressId = 0;

		String query = "SELECT addressId FROM Address where street = ? and zip = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			ps.setString(2, zip);
			rs = ps.executeQuery();
			if (rs.next()) {
				addressId = rs.getInt("addressId");
			} else {
				rs.close();

				query = "INSERT INTO Address (street, city, state, zip, country) VALUES (?, ?, ?, ?, ?)";

				ps = conn.prepareStatement(query);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);
				ps.setString(5, country);
				ps.executeUpdate();

				ps = conn.prepareStatement("SELECT LAST_INSERT_ID() AS lastInsert");
				rs = ps.executeQuery();
				rs.next();
				addressId = rs.getInt("lastInsert");
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		query = "INSERT INTO Person (personCode, firstName, lastName, addressId, secCode, typeOfBroker) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setInt(4, addressId);
			ps.setString(5, secBrokerId);
			ps.setString(6, brokerType);
			ps.executeUpdate();
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

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {

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

		String query = "SELECT emailId FROM Email WHERE address = ? and personId = (SELECT personId FROM Person WHERE personCode = ?)";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, personCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				throw new RuntimeException("Attempted to add a preexisting email");
			} 
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		query = "INSERT INTO Email (address, personId) VALUES (?, (SELECT personId FROM Person WHERE personCode = ?))";

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, personCode);
			ps.executeUpdate();
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

	}

	/**
	 * Removes all asset records from the database
	 */
	public static void removeAllAssets() {

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

		String query = "DELETE FROM AssetPortfolio";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Asset";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Removes the asset record from the database corresponding to the provided
	 * <code>assetCode</code>
	 * @param assetCode
	 */
	public static void removeAsset(String assetCode) {

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

		String query = "DELETE FROM AssetPortfolio WHERE assetId = (SELECT assetId FROM Asset WHERE assetCode = ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();

			query = "DELETE FROM Asset where assetCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a deposit account asset record to the database with the provided data. 
	 * @param assetCode
	 * @param label
	 * @param apr - Annual Percentage Rate on the scale [0,1]
	 */
	public static void addDepositAccount(String assetCode, String label, double apr) {

		if (apr > 1 || apr < 0) {
			throw new RuntimeException("apr must be in the scale [0,1]");
		}

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

		String query = "INSERT INTO Asset (assetCode, typeOfAsset, label, apr) VALUES (?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, "D");
			ps.setString(3, label);
			ps.setDouble(4, apr);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a private investment asset record to the database with the provided
	 * data. 
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn  on the scale [0,1]
	 * @param baseOmega
	 * @param totalValue
	 */
	public static void addPrivateInvestment(String assetCode, String label, Double quarterlyDividend,
			Double baseRateOfReturn, Double baseOmega, Double totalValue) {

		if (baseRateOfReturn > 1 || baseRateOfReturn < 0) {
			throw new RuntimeException("baseRateOfReturn must be in the range [0,1]");
		}

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

		String query = "INSERT INTO Asset (assetCode, typeOfAsset, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, "P");
			ps.setString(3, label);
			ps.setDouble(4, quarterlyDividend);
			ps.setDouble(5, baseRateOfReturn);
			ps.setDouble(6, baseOmega);
			ps.setDouble(7, totalValue);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a stock asset record to the database with the provided data.
	 * @param assetCode
	 * @param label
	 * @param quarterlyDividend
	 * @param baseRateOfReturn  on the scale of [0,1]
	 * @param beta
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, Double quarterlyDividend, Double baseRateOfReturn,
			Double beta, String stockSymbol, Double sharePrice) {

		if (baseRateOfReturn > 1 || baseRateOfReturn < 0) {
			throw new RuntimeException("baseRateOfReturn must be in the range [0,1]");
		}

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

		String query = "INSERT INTO Asset (assetCode, typeOfAsset, label, quarterlyDividend, baseRateOfReturn, betaMeasure, stockSymbol, sharePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, assetCode);
			ps.setString(2, "S");
			ps.setString(3, label);
			ps.setDouble(4, quarterlyDividend);
			ps.setDouble(5, baseRateOfReturn);
			ps.setDouble(6, beta);
			ps.setString(7, stockSymbol);
			ps.setDouble(8, sharePrice);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Removes all portfolio records from the database
	 */
	public static void removeAllPortfolios() {

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

		String query = "DELETE FROM AssetPortfolio";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Portfolio";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Removes the portfolio record from the database corresponding to the provided
	 * <code>portfolioCode</code> 
	 * @param portfolioCode
	 */
	public static void removePortfolio(String portfolioCode) {

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

		String query = "DELETE FROM AssetPortfolio WHERE portfolioId = (SELECT portfolioId FROM Portfolio WHERE portfolioCode = ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();

			query = "DELETE FROM Portfolio where portfolioCode = ?";
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a portfolio records to the database with the given data. If the
	 * portfolio has no beneficiary, the <code>beneficiaryCode</code> will be
	 * <code>null</code>. 
	 * @param portfolioCode
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addPortfolio(String portfolioCode, String ownerCode, String managerCode,
			String beneficiaryCode) {

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

		String query = "INSERT INTO Portfolio (portfolioCode, ownerId, managerId, beneficiaryId) "
				+ "VALUES (?, (SELECT personId FROM Person WHERE personCode = ?), "
				+ "(SELECT personId FROM Person WHERE personCode = ?), "
				+ "(SELECT personId FROM Person WHERE personCode = ?))";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.setString(2, ownerCode);
			ps.setString(3, managerCode);
			ps.setString(4, beneficiaryCode);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Associates the asset record corresponding to <code>assetCode</code> with the
	 * portfolio corresponding to the provided <code>portfolioCode</code>. The third
	 * parameter, <code>value</code> is interpreted as a <i>balance</i>, <i>number
	 * of shares</i> or <i>stake percentage</i> depending on the type of asset the
	 * <code>assetCode</code> is associated with. 
	 * @param portfolioCode
	 * @param assetCode
	 * @param value
	 */
	public static void addAsset(String portfolioCode, String assetCode, double value) {

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

		String query = "INSERT INTO AssetPortfolio (portfolioId, assetId, assetValue) "
				+ "VALUES ((SELECT portfolioId FROM Portfolio WHERE portfolioCode = ?), "
				+ "(SELECT assetId FROM Asset WHERE assetCode = ?), ?)";

		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, portfolioCode);
			ps.setString(2, assetCode);
			ps.setDouble(3, value);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}