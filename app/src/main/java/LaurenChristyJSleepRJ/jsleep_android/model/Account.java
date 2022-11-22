package LaurenChristyJSleepRJ.jsleep_android.model;


public class Account extends Serializable{
    public String name;
    public String email;
    public String password;
    public double balance;
    public Renter renter;



    @Override
    public String toString(){
        return "Account{" +
                "Balance =" + balance +
                ", Email ='" + email + '\'' +
                ", Name ='" + name + '\'' +
                ", Password ='" + password + '\'' +
                ", Renter =" + renter +
                '}';
    }
}
