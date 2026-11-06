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


public class ReservationsDao {
	protected ConnectionManager connectionManager;

	private static ReservationsDao instance = null;
	protected ReservationsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReservationsDao getInstance() {
		if(instance == null) {
			instance = new ReservationsDao();
		}
		return instance;
	}

	public Reservations create(Reservations reservation) throws SQLException {
		String insertReservation =
			"INSERT INTO Reservations(StartTime,EndTime,PartySize,UserName,RestaurantId) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReservation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setTimestamp(1, reservation.getStartTime());
			insertStmt.setTimestamp(2, reservation.getEndTime());
			insertStmt.setInt(3, reservation.getPartySize());
			insertStmt.setString(4, reservation.getUser().getUserName());
			insertStmt.setInt(5, reservation.getRestaurant().getRestaurantId());
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int reservationId = -1;
			if(resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reservation.setReservationId(reservationId);
			return reservation;
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

	public Reservations delete(Reservations reservation) throws SQLException {
		String deleteReservation = "DELETE FROM Reservations WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReservation);
			deleteStmt.setInt(1, reservation.getReservationId());
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

	public Reservations getReservationById(int reservationId) throws SQLException {
		String selectReservation =
			"SELECT ReservationId,StartTime,EndTime,PartySize,UserName,RestaurantId " +
			"FROM Reservations " +
			"WHERE ReservationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservation);
			selectStmt.setInt(1, reservationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
			if(results.next()) {
				int resultReservationId = results.getInt("ReservationId");
				Timestamp startTime =  results.getTimestamp("StartTime");
				Timestamp endTime =  results.getTimestamp( "EndTime");
				int partySize = results.getInt("PartySize");
				String userName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users user = usersDao.getUserByUserName(userName);
				SitDownRestaurants restaurant = sitDownRestaurantsDao.getSitDownRestaurantById(restaurantId);
				Reservations reservation = new Reservations(resultReservationId, startTime, endTime, partySize,
						user, restaurant);
				return reservation;
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

	public List<Reservations>getReservationsByUserName(String userName) throws SQLException {
		List<Reservations> reservations = new ArrayList<Reservations>();
		String selectReservations =
			"SELECT ReservationId,StartTime,EndTime,PartySize,UserName,RestaurantId " +
			"FROM Reservations " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReservations);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
			while(results.next()) {
				int reservationId = results.getInt("ReservationId");
				Timestamp startTime =  results.getTimestamp("StartTime");
				Timestamp endTime =  results.getTimestamp( "EndTime");
				int partySize = results.getInt("PartySize");
				String resultUserName = results.getString("UserName");
				int restaurantId = results.getInt("RestaurantId");
				
				Users resultUser = usersDao.getUserByUserName(resultUserName);
				SitDownRestaurants restaurant = sitDownRestaurantsDao.getSitDownRestaurantById(restaurantId);
				Reservations reservation = new Reservations(reservationId, startTime, endTime, partySize,
						resultUser, restaurant);
				reservations.add(reservation);
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
		return reservations;
	}
	
	public List<Reservations>getReservationsBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
	    List<Reservations> reservations = new ArrayList<Reservations>();
		String selectReservations =
			"SELECT ReservationId,StartTime,EndTime,PartySize,UserName,RestaurantId " +
			"FROM Reservations " +
			"WHERE RestaurantId=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	        connection = connectionManager.getConnection();
	        selectStmt = connection.prepareStatement(selectReservations);
	        selectStmt.setInt(1, sitDownRestaurantId);
	        results = selectStmt.executeQuery();
	        UsersDao usersDao = UsersDao.getInstance();
	        SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
	        SitDownRestaurants resultRestaurant = sitDownRestaurantsDao.getSitDownRestaurantById(sitDownRestaurantId);
	        while(results.next()) {
				int reservationId = results.getInt("ReservationId");
				Timestamp startTime =  results.getTimestamp("StartTime");
				Timestamp endTime =  results.getTimestamp( "EndTime");
				int partySize = results.getInt("PartySize");
				String userName = results.getString("UserName");
	            
	            Users user =usersDao.getUserByUserName(userName);
				Reservations reservation = new Reservations(reservationId, startTime, endTime, partySize,
						user, resultRestaurant);
				reservations.add(reservation);
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
		return reservations;
	}
}
