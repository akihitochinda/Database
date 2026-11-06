package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TakeOutRestaurantsDao extends RestaurantsDao {
	private static TakeOutRestaurantsDao instance = null;
	protected TakeOutRestaurantsDao() {
		super();
	}
	public static TakeOutRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new TakeOutRestaurantsDao();
		}
		return instance;
	}
	
	public TakeOutRestaurants create(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		super.create(takeOutRestaurant);

		String insertRestaurant = "INSERT INTO TakeOutRestaurants(RestaurantId,MaxWaitTime) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurant);
			insertStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			insertStmt.setInt(2, takeOutRestaurant.getMaxWaitTime());
			insertStmt.executeUpdate();
			return takeOutRestaurant;
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
	
	public TakeOutRestaurants getTakeOutRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT Restaurants.*, TakeOutRestaurants.MaxWaitTime " +
				"FROM Restaurants " +
				"INNER JOIN TakeOutRestaurants " +
				"  ON Restaurants.RestaurantId = TakeOutRestaurants.RestaurantId " +
				"WHERE Restaurants.RestaurantId=?;";
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
				TakeOutRestaurants takeOutRestaurant =
						new TakeOutRestaurants(
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
								company,
								results.getInt("MaxWaitTime"));
				return takeOutRestaurant;
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
	
	public List<TakeOutRestaurants>getTakeOutRestaurantsByCompanyName(String companyName)
			throws SQLException {
		List<TakeOutRestaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT Restaurants.*, TakeOutRestaurants.MaxWaitTime " +
				"FROM Restaurants " +
				"INNER JOIN TakeOutRestaurants " +
				"  ON Restaurants.RestaurantId = TakeOutRestaurants.RestaurantId " +
				"WHERE CompanyName=?;";
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
						new TakeOutRestaurants(
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
								resultCompany,
								results.getInt("MaxWaitTime")));
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
	
	public TakeOutRestaurants delete(TakeOutRestaurants takeOutRestaurant) throws SQLException {
		String deleteRestaurant = "DELETE FROM TakeOutRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, takeOutRestaurant.getRestaurantId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for RestaurantId=" + takeOutRestaurant.getRestaurantId());
			}
			super.delete(takeOutRestaurant);
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
