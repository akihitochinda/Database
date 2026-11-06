package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ReviewsDao {
	protected ConnectionManager connectionManager;

	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}

	public Reviews create(Reviews review) throws SQLException {
		String insertReview =
			"INSERT INTO Reviews(Rating,WrittenReview,Created,UserName,RestaurantId) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// Reviews has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setDouble(1, review.getRating());
			insertStmt.setString(2, review.getWrittenReview());
			insertStmt.setTimestamp(3, review.getCreated());
			insertStmt.setString(4, review.getUser().getUserName());
			insertStmt.setInt(5, review.getRestaurant().getRestaurantId());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			return review;
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

	/**
	 * Update the content of the Reviews instance.
	 * This runs a UPDATE statement.
	 */
	public Reviews updateReview(Reviews review, String newWrittenReview) throws SQLException {
		String updateReview = "UPDATE Reviews SET WrittenReview=?,Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReview);
			updateStmt.setString(1, newWrittenReview);
			// Sets the Created timestamp to the current time.
			Timestamp newCreatedTimestamp = new Timestamp(System.currentTimeMillis());
			updateStmt.setTimestamp(2, newCreatedTimestamp);
			updateStmt.setInt(3, review.getReviewId());
			updateStmt.executeUpdate();

			// Update the review param before returning to the caller.
			review.setWrittenReview(newWrittenReview);
			review.setCreated(newCreatedTimestamp);
			return review;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the Reviews instance.
	 * This runs a DELETE statement.
	 */
	public Reviews delete(Reviews review) throws SQLException {
		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Reviews instance.
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

	/**
	 * Get the Reviews record by fetching it from your MySQL instance.
	 * This runs a SELECT statement and returns a single Reviews instance.
	 * Note that we use UsersDao and RestaurantsDao to retrieve the referenced
	 * Users and Restaurants instance.
	 * One alternative (possibly more efficient) is using a single SELECT statement
	 * to join the Reviews, Users, Restaurants tables and then build each object.
	 */
	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview =
			"SELECT ReviewId,Rating,WrittenReview,Created,UserName,RestaurantId " +
			"FROM Reviews " +
			"WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				double rating = results.getDouble("Rating");
				String writtenReview = results.getString("WrittenReview");
				Timestamp created =  results.getTimestamp("Created");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users user = usersDao.getUserByUserName(userName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Reviews review = new Reviews(resultReviewId, rating, writtenReview,
					created, user, restaurant);
				return review;
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
	 * Get the all the Reviews for a user.
	 */
	public List<Reviews> getReviewsByUserName(String userName) throws SQLException {
		List<Reviews> reviews = new ArrayList<Reviews>();
		String selectReviews =
			"SELECT ReviewId,Rating,WrittenReview,Created,UserName,RestaurantId " +
			"FROM Reviews " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReviews);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				double rating = results.getDouble("Rating");
				String writtenReview = results.getString("WrittenReview");
				Timestamp created =  results.getTimestamp("Created");
				String resultUserName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				Restaurants restaurant = restaurantsDao.getRestaurantById(restaurantId);
				Reviews review = new Reviews(reviewId, rating, writtenReview,
						created, resultUser, restaurant);
				reviews.add(review);
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
		return reviews;
	}
	
	/**
	 * Get the all the Reviews for a restaurant.
	 */
	public List<Reviews>getReviewsByRestaurantId(int restaurantId) throws SQLException {
	    List<Reviews> reviews = new ArrayList<Reviews>();
	    String selectReviews =
	    	"SELECT ReviewId,Rating,WrittenReview,Created,UserName,RestaurantId " +
	        "FROM Reviews " +
	        "WHERE RestaurantId=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectReviews);
	        selectStmt.setInt(1, restaurantId);
	        results = selectStmt.executeQuery();
	        UsersDao usersDao = UsersDao.getInstance();
	        RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
            Restaurants resultRestaurant = restaurantsDao.getRestaurantById(restaurantId);
	        while(results.next()) {
	            int reviewId = results.getInt("ReviewId");
	            double rating = results.getDouble("Rating");
	            String writtenReview =results.getString("WrittenReview");
	            Timestamp created =results.getTimestamp("Created");
	            String userName =results.getString("UserName");
	            
	            Users user =usersDao.getUserByUserName(userName);
	            Reviews review = new Reviews(reviewId, rating, writtenReview,
	            		created, user, resultRestaurant);
	            reviews.add(review);
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
		return reviews;
	}
}
