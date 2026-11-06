package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodCartRestaurantsDao extends RestaurantsDao {
	private static FoodCartRestaurantsDao instance = null;
	protected FoodCartRestaurantsDao() {
		super();
	}
	public static FoodCartRestaurantsDao getInstance() {
		if(instance == null) {
			instance = new FoodCartRestaurantsDao();
		}
		return instance;
	}
	
	public FoodCartRestaurants create(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		super.create(foodCartRestaurant);

		String insertRestaurant = "INSERT INTO FoodCartRestaurants(RestaurantId,IsLicensed) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRestaurant);
			insertStmt.setInt(1, foodCartRestaurant.getRestaurantId());
			insertStmt.setBoolean(2, foodCartRestaurant.getIsLicensed());
			insertStmt.executeUpdate();
			return foodCartRestaurant;
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
	
	public FoodCartRestaurants getFoodCartRestaurantById(int restaurantId) throws SQLException {
		String selectRestaurant = "SELECT Restaurants.*, FoodCartRestaurants.IsLicensed " +
				"FROM Restaurants " +
				"INNER JOIN FoodCartRestaurants " +
				"  ON Restaurants.RestaurantId = FoodCartRestaurants.RestaurantId " +
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
				FoodCartRestaurants foodCartRestaurant =
						new FoodCartRestaurants(
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
								results.getBoolean("IsLicensed"));
				return foodCartRestaurant;
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
	
	public List<FoodCartRestaurants>getFoodCartRestaurantsByCompanyName(String companyName)
			throws SQLException {
		List<FoodCartRestaurants> restaurants = new ArrayList<>();
		String selectRestaurants = "SELECT Restaurants.*, FoodCartRestaurants.IsLicensed " +
				"FROM Restaurants " +
				"INNER JOIN FoodCartRestaurants " +
				"  ON Restaurants.RestaurantId = FoodCartRestaurants.RestaurantId " +
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
						new FoodCartRestaurants(
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
								results.getBoolean("IsLicensed")));
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
	
	public FoodCartRestaurants delete(FoodCartRestaurants foodCartRestaurant) throws SQLException {
		String deleteRestaurant = "DELETE FROM FoodCartRestaurants WHERE RestaurantId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRestaurant);
			deleteStmt.setInt(1, foodCartRestaurant.getRestaurantId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for RestaurantId=" + foodCartRestaurant.getRestaurantId());
			}
			super.delete(foodCartRestaurant);
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
