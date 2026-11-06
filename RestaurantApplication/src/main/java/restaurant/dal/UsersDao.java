package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Data access object (DAO) class to interact with the underlying Users table in your
 * MySQL instance. This is used to store {@link UsersDao} into your MySQL instance and 
 * retrieve {@link UsersDao} from MySQL instance.
 */
public class UsersDao extends UsersProfileDao {
	// Single pattern: instantiation is limited to one object.
	private static UsersDao instance = null;
	protected UsersDao() {
		super();
	}
	public static UsersDao getInstance() {
		if(instance == null) {
			instance = new UsersDao();
		}
		return instance;
	}

	public Users create(Users user) throws SQLException {
		// Insert into the superclass table first.
		super.create(new UsersProfile(user.getUserName(), user.getPassword(),
				user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhoneNumber()));

		String insertUser = "INSERT INTO Users(UserName,DoB) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser);
			insertStmt.setString(1, user.getUserName());
			insertStmt.setTimestamp(2, new Timestamp(user.getDob().getTime()));
			insertStmt.executeUpdate();
			return user;
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
	 * Delete the Users instance.
	 * This runs a DELETE statement.
	 */
	public Users delete(Users user) throws SQLException {
		String deleteUser = "DELETE FROM Users WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setString(1, user.getUserName());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserName=" + user.getUserName());
			}

			// Then also delete from the superclass.
			// Notes:
			// 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
			//    super.delete() without even needing to delete from CreditCardInfo first.
			// 2. Reviews has a fk constraint on Users with the reference option
			//    ON DELETE SET NULL. If the Reviews fk reference option was instead
			//    ON DELETE RESTRICT, then the caller would need to delete the referencing
			//    Reviews before this User can be deleted.
			//    Example to delete the referencing Reviews:
			//    List<Reviews> reviews = ReviewsDao.getReviewsForUser(User.getUserName());
			//    for(Reviews r : reviews) ReviewsDao.delete(r);
			super.delete(user);

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

	public Users getUserByUserName(String userName) throws SQLException {
		// To build an User object, we need the Users record, too.
		String selectUser =
			"SELECT Users.UserName AS UserName, Password, FirstName, LastName, Email, PhoneNumber, DoB " +
			"FROM Users INNER JOIN UsersProfile " +
			"  ON Users.UserName = UsersProfile.UserName " +
			"WHERE Users.UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			if(results.next()) {
				String resultUserName = results.getString("UserName");
				String password = results.getString("Password");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				String phoneNumber = results.getString("PhoneNumber");
				Date dob = new Date(results.getTimestamp("DoB").getTime());
				Users User = new Users(resultUserName, password, firstName, lastName, email, phoneNumber, dob);
				return User;
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

	public List<Users> getUsersByFirstName(String firstName)
			throws SQLException {
		List<Users> Users = new ArrayList<Users>();
		String selectUsers =
			"SELECT Users.UserName AS UserName, Password, FirstName, LastName, Email, PhoneNumber, DoB " +
			"FROM Users INNER JOIN UsersProfile " +
			"  ON Users.UserName = UsersProfile.UserName " +
			"WHERE UsersProfile.FirstName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUsers);
			selectStmt.setString(1, firstName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				String userName = results.getString("UserName");
				String password = results.getString("Password");
				String resultFirstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				String phoneNumber = results.getString("PhoneNumber");
				Date dob = new Date(results.getTimestamp("DoB").getTime());
				Users User = new Users(userName, password, resultFirstName, lastName, email, phoneNumber, dob);
				Users.add(User);
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
		return Users;
	}
}
