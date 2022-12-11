package LaurenChristyJSleepRJ.jsleep_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a room.
 *
 * @author Lauren Christy Tanudjaja
 */
public class Room extends Serializable {
    public int size;
    public String name;
    public ArrayList<Facility> facility;
    public Price price;
    public City city;
    public String address;
    public BedType bedType;
    public ArrayList<Date> booked;
    public int accountId;

    @Override
    public String toString() {
        return name;

    }
}

