package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class RecommendationsDao {
	protected ConnectionManager connectionManager;

	private static RecommendationsDao instance = null;
	protected RecommendationsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RecommendationsDao getInstance() {
		if(instance == null) {
			instance = new RecommendationsDao();
		}
		return instance;
	}

	public Recommendations create(Recommendations recommendation) throws SQLException {
		String insertRecommendation =
			"INSERT INTO Recommendations(IsRecommended,UserName,RestaurantId) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setBoolean(1, recommendation.getIsRecommended());
			insertStmt.setString(2, recommendation.getUser().getUserName());
			insertStmt.setInt(3, recommendation.getRestaurant().getRestaurantId());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int recommendationId = -1;
			if(resultKey.next()) {
				recommendationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(recommendationId);
			return recommendation;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	public Recommendations delete(Recommendations recommendation) throws SQLException {
		String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
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

	public Recommendations getRecommendationById(int recommendationId) throws SQLException {
		String selectReview =
			"SELECT RecommendationId,IsRecommended,UserName,RestaurantId " +
			"FROM Recommendations " +
			"WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			if(results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				boolean isRecommended = results.getBoolean("IsRecommended");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Recommendations recommendation = new Recommendations(resultRecommendationId, isRecommended,
						user, restaurant);
				return recommendation;
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

	public List<Recommendations>getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<Recommendations>();
		String selectRecommendations =
			"SELECT RecommendationId,IsRecommended,UserName,RestaurantId " +
			"FROM Recommendations " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while(results.next()) {
				int recommendationId = results.getInt("RecommendationId");
				boolean isRecommended = results.getBoolean("IsRecommended");
				String resultUserName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Recommendations recommendation = new Recommendations(recommendationId, isRecommended,
						resultUser, restaurant);
				recommendations.add(recommendation);
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
		return recommendations;
	}
	
	public List<Recommendations>getRecommendationsByRestaurantId(int restaurantId) throws SQLException {
	    List<Recommendations> recommendations = new ArrayList<Recommendations>();
	    String selectRecommendations =
			"SELECT RecommendationId,IsRecommended,UserName,RestaurantId " +
			"FROM Recommendations " +
			"WHERE RestaurantId=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectRecommendations);
	        selectStmt.setInt(1, restaurantId);
	        results = selectStmt.executeQuery();
	        UsersDao usersDao = UsersDao.getInstance();
	        RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
            Restaurants resultRestaurant = restaurantsDao.getRestaurantById(restaurantId);
	        while(results.next()) {
				int recommendationId = results.getInt("RecommendationId");
				boolean isRecommended = results.getBoolean("IsRecommended");
				String userName = results.getString("UserName");
	            
	            Users user =usersDao.getUserByUserName(userName);
	            Recommendations recommendation = new Recommendations(recommendationId, isRecommended,
						user, resultRestaurant);
	            recommendations.add(recommendation);
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
		return recommendations;
	}
}
