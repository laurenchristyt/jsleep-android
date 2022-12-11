package LaurenChristyJSleepRJ.jsleep_android.model;

/**
 * The {@code Account} class represents an account for the JSleep Android app.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class Account extends Serializable {
    /**
     * The name of the user associated with the account.
     */
    public String name;
    /**
     * The password for the account.
     */
    public String password;
    /**
     * The renter associated with the account.
     */
    public Renter renter;
    /**
     * The email address of the user associated with the account.
     */
    public String email;

    /**
     * The balance of the account.
     */
    public double balance;

    @Override
    public String toString() {
        return "Account{" +
                "Balance =" + balance +
                ", Email ='" + email + '\'' +
                ", Name ='" + name + '\'' +
                ", Password ='" + password + '\'' +
                ", Renter =" + renter +
                '}';
    }
}
