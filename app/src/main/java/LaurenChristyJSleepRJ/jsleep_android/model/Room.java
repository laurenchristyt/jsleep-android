package LaurenChristyJSleepRJ.jsleep_android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Room implements Serializable {
    public int size;
    public String name;
    public Facility facility;
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
