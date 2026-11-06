package restaurant.model;

/**
 * UsersProfile is a simple, plain old java objects (POJO).
 * 
 * UsersProfile/UsersProfileDao is the superclass for CreditCardInfo/CreditCardInfoDao and
 * Users/UsersDao. Our implementation of UsersProfile is a concrete class. This allows 
 * us to create records in the UsersProfile MySQL table without having the associated records
 * in the CreditCardInfo or Users MySQL tables. Alternatively, UsersProfile could be an
 * interface or an abstract class, which would force a UsersProfile record to be created only
 * if a CreditCardInfo or Users record is created, too.
 */
public class UsersProfile {

    protected String userName;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;

    public UsersProfile(String userName, String password, String firstName, String lastName, String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public UsersProfile(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}