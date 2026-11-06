package restaurant.dal;

import restaurant.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
// import java.util.Date;
import java.util.List;

public class CreditCardsDao {

    protected ConnectionManager connectionManager;
    
    private static CreditCardsDao instance = null;
    protected CreditCardsDao() {
        connectionManager = new ConnectionManager();
    }

    public static CreditCardsDao getInstance() {
        if(instance == null)
            instance = new CreditCardsDao();
        return instance;
    }
    
    public CreditCards create(CreditCards creditCard) throws SQLException {
        String insertCard = "INSERT INTO CreditCardInfo(CardNumber,ExpirationDate,UserName) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertCard);
            insertStmt.setLong(1, creditCard.getCardNumber());
            insertStmt.setDate(2, creditCard.getExpirationDate());
            insertStmt.setString(3, creditCard.getUser().getUserName());
            insertStmt.executeUpdate();
            return creditCard;
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
    
    public CreditCards getCreditCardByCardNumber(long cardNumber) throws SQLException {
    	String selectCard =
    		    "SELECT CardNumber, ExpirationDate, UserName " +
    		    "FROM CreditCardInfo " +
    		    "WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCard);
			selectStmt.setLong(1, cardNumber);
			results = selectStmt.executeQuery();
			if(results.next()) {
				long resultCardNumber = results.getLong("CardNumber");
				Date expirationDate = results.getDate("ExpirationDate");
				String userName = results.getString("UserName");
				UsersProfileDao usersDao = UsersProfileDao.getInstance();
				UsersProfile user = usersDao.getUserFromUserName(userName);
				CreditCards card = new CreditCards(resultCardNumber, expirationDate, user);
		        return card;
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
    
    public List<CreditCards>getCreditCardsByUserName(String userName)
            throws SQLException {
    	List<CreditCards> cards = new ArrayList<CreditCards>();
    	String selectCards =
    		    "SELECT CardNumber, ExpirationDate, UserName " +
    		    "FROM CreditCardInfo " +
    		    "WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCards);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersProfileDao usersDao = UsersProfileDao.getInstance();
	    	while(results.next()) {
	    	    long cardNumber = results.getLong("CardNumber");
	    	    Date expiration = results.getDate("ExpirationDate");
	    	    String resultUserName = results.getString("UserName");
	    	    UsersProfile resultUser = usersDao.getUserFromUserName(resultUserName);
	    	    CreditCards card = new CreditCards(cardNumber, expiration, resultUser);
	    	    cards.add(card);
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
	    return cards;
    }
    
    public CreditCards updateExpiration(CreditCards creditCard, Date newExpiration) throws SQLException {
    	String updateCard = "UPDATE CreditCardInfo SET ExpirationDate=? WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCard);
			updateStmt.setDate(1, newExpiration);
			updateStmt.setLong(2, creditCard.getCardNumber());
			updateStmt.executeUpdate();
			creditCard.setExpirationDate(newExpiration);
    		return creditCard;
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
    
    public CreditCards delete(CreditCards creditCard) throws SQLException {
    	String deleteCard = "DELETE FROM CreditCardInfo WHERE CardNumber=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCard);    	
			deleteStmt.setLong(1, creditCard.getCardNumber());
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