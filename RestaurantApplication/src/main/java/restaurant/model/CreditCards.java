package restaurant.model;

import java.sql.Date;

public class CreditCards {

    protected long cardNumber;
    protected Date expirationDate;
    protected UsersProfile user;

    public CreditCards(long cardNumber, Date expirationDate, UsersProfile user) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public UsersProfile getUser() {
        return user;
    }

    public void setUser(UsersProfile user) {
        this.user = user;
    }
}