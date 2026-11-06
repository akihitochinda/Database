package restaurant.model;

import java.util.Date;

/**
 * Users is a simple, plain old java objects (POJO).
 * Well, almost (it extends {@link UsersProfile}).
 */
public class Users extends UsersProfile {
    protected Date dob;

    public Users(String userName, String password, String firstName, String lastName, String email, String phoneNumber, Date dob) {
        super(userName, password, firstName, lastName, email, phoneNumber);
        this.dob = dob;
    }

    public Users(String userName) {
        super(userName);
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
}