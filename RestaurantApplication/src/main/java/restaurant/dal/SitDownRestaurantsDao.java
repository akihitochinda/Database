package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitDownRestaurantsDao extends RestaurantsDao {
	private static SitDownRestaurantsDao instance = null;
	protected SitDownRestaurantsDao() {
		super();
	}
	public static SitDownRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new SitDownRestaurantsDao();
		}
		return instance;
	}
	
	public SitDownRestaurants create(SitDownRestaurants sitDownRestaurant) throws SQLException {
		super.create(sitDownRestaurant);

		String insertRestaurant = "INSERT INTO SitDownRestaurants(RestaurantId,Capacity) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurant);
			insertStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			insertStmt.setInt(2, sitDownRestaurant.getCapacity());
			insertStmt.executeUpdate();
			return sitDownRestaurant;
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
	
	public SitDownRestaurants getSitDownRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT Restaurants.*, SitDownRestaurants.Capacity " +
				"FROM Restaurants " +
				"INNER JOIN SitDownRestaurants " +
				"  ON Restaurants.RestaurantId = SitDownRestaurants.RestaurantId " +
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
				SitDownRestaurants sitDownRestaurant =
						new SitDownRestaurants(
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
								results.getInt("Capacity"));
				return sitDownRestaurant;
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
	
	public List<SitDownRestaurants>getSitDownRestaurantsByCompanyName(String companyName)
			throws SQLException {
		List<SitDownRestaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT Restaurants.*, SitDownRestaurants.Capacity " +
				"FROM Restaurants " +
				"INNER JOIN SitDownRestaurants " +
				"  ON Restaurants.RestaurantId = SitDownRestaurants.RestaurantId " +
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
						new SitDownRestaurants(
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
								results.getInt("Capacity")));
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
	
	public SitDownRestaurants delete(SitDownRestaurants sitDownRestaurant) throws SQLException {
		// Note: Reservations has a fk constraint on SitDownRestaurants with the reference option
		// ON DELETE CASCADE. So this delete operation will delete all the referencing
		// Reservations.
		String deleteRestaurant = "DELETE FROM SitDownRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, sitDownRestaurant.getRestaurantId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for RestaurantId=" + sitDownRestaurant.getRestaurantId());
			}
			super.delete(sitDownRestaurant);
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
