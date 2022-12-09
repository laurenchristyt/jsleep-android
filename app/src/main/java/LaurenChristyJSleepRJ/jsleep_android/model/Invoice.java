package LaurenChristyJSleepRJ.jsleep_android.model;

public class Invoice extends Serializable {
    public int renterId;
    public int buyerId;
    public RoomRating rating;
    public PaymentStatus status;

    public enum PaymentStatus {
        FAILED, WAITING, SUCCESS
    }

    public enum RoomRating {
        NONE, BAD, NEUTRAL, GOOD
    }

}
