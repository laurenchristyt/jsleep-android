package LaurenChristyJSleepRJ.jsleep_android.model;

/**
 * A class that represents an invoice for a rental transaction.
 *
 * <p>This class extends the `Serializable` class and adds fields for the IDs of the buyer and renter, the payment status, and the rating of the rental.</p>
 *
 * @author Lauren Christy Tanudjaja
 * @see Serializable
 */
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
