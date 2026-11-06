package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsDao {

	protected ConnectionManager connectionManager;

	private static RestaurantsDao instance = null;
	protected RestaurantsDao() {
		connectionManager = new ConnectionManager();
	}

	public static RestaurantsDao getInstance() {
		if(instance == null) {
			instance = new RestaurantsDao();
		}
		return instance;
	}

	public Restaurants create(Restaurants restaurant) throws SQLException {
		String insertRestaurant =
				"INSERT INTO Restaurants(Name,Description,Menu,ListedHours,IsActive, Street1,Street2,City,State,Zip,CuisineType,CompanyName) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Restaurants has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRestaurant,
					PreparedStatement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, restaurant.getName());
			insertStmt.setString(2, restaurant.getDescription());
			insertStmt.setString(3, restaurant.getMenu());
			insertStmt.setString(4, restaurant.getListedHours());
			insertStmt.setBoolean(5, restaurant.getIsActive());
			insertStmt.setString(6, restaurant.getStreet1());
			insertStmt.setString(7, restaurant.getStreet2());
			insertStmt.setString(8, restaurant.getCity());
			insertStmt.setString(9, restaurant.getState());
			insertStmt.setString(10, restaurant.getZip());
			insertStmt.setString(11, restaurant.getCuisineType().name());
			insertStmt.setString(12, restaurant.getCompany().getCompanyName());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int restaurantId = -1;
			if(resultKey.next()) {
				restaurantId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			restaurant.setRestaurantId(restaurantId);
			return restaurant;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	/**
	 * Get the Restaurants record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Restaurants instance.
	 * Note that we use CompaniesDao to retrieve the referenced Companies instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Restaurants, Companies tables and then build each object.
	 */
	public Restaurants getRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT * FROM Restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurant);
			selectStmt.setInt(1, restaurantId);
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			if(results.next()) {
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);
				Restaurants restaurant =
						new Restaurants(
								results.getInt("RestaurantId"),
								results.getString("Name"),
								results.getString("Description"),
								results.getString("Menu"),
								results.getString("ListedHours"),
								results.getBoolean("IsActive"),
								results.getString("Street1"),
								results.getString("Street2"),
								results.getString("City"),
								results.getString("State"),
								results.getString("Zip"),
								Restaurants.CuisineType.valueOf(
										results.getString("CuisineType")),
								company);
				return restaurant;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	/**
	 * Get the all the Restaurants for a cuisine.
	 */
	public List<Restaurants>getRestaurantsByCuisine(Restaurants.CuisineType cuisine)
			throws SQLException {
		List<Restaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT * FROM Restaurants WHERE CuisineType=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, cuisine.name());
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while(results.next()) {
				String companyName = results.getString("CompanyName");
				Companies company = companiesDao.getCompanyByCompanyName(companyName);
				restaurants.add(
						new Restaurants(
								results.getInt("RestaurantId"),
								results.getString("Name"),
								results.getString("Description"),
								results.getString("Menu"),
								results.getString("ListedHours"),
								results.getBoolean("IsActive"),
								results.getString("Street1"),
								results.getString("Street2"),
								results.getString("City"),
								results.getString("State"),
								results.getString("Zip"),
								Restaurants.CuisineType.valueOf(
										results.getString("CuisineType")),
								company));
			}
    	} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return restaurants;	
	}
	
	/**
	 * Get the all the Restaurants for a company.
	 */
	public List<Restaurants>getRestaurantsByCompanyName(String companyName)
			throws SQLException {
		List<Restaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT * FROM Restaurants WHERE CompanyName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRestaurants);
			selectStmt.setString(1, companyName);
			results = selectStmt.executeQuery();
			CompaniesDao companiesDao = CompaniesDao.getInstance();
			while(results.next()) {
				String resultCompanyName = results.getString("CompanyName");
				Companies resultCompany = companiesDao.getCompanyByCompanyName(resultCompanyName);
				restaurants.add(
						new Restaurants(
								results.getInt("RestaurantId"),
								results.getString("Name"),
								results.getString("Description"),
								results.getString("Menu"),
								results.getString("ListedHours"),
								results.getBoolean("IsActive"),
								results.getString("Street1"),
								results.getString("Street2"),
								results.getString("City"),
								results.getString("State"),
								results.getString("Zip"),
								Restaurants.CuisineType.valueOf(
										results.getString("CuisineType")),
								resultCompany));
			}
    	} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return restaurants;	
	}
	
	/**
	 * Delete the Restaurants instance.
	 * This runs a DELETE statement.
	 */
	public Restaurants delete(Restaurants restaurant) throws SQLException {
		// Note: Reviews/Recommendations has a fk constraint on Restaurants with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// Reviews/Recommendations.
		String deleteRestaurant = "DELETE FROM Restaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, restaurant.getRestaurantId());
			deleteStmt.executeUpdate();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
}