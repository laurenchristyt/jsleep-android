package LaurenChristyJSleepRJ.jsleep_android.request;

import java.util.ArrayList;
import java.util.List;

import LaurenChristyJSleepRJ.jsleep_android.model.Account;
import LaurenChristyJSleepRJ.jsleep_android.model.BedType;
import LaurenChristyJSleepRJ.jsleep_android.model.City;
import LaurenChristyJSleepRJ.jsleep_android.model.Facility;
import LaurenChristyJSleepRJ.jsleep_android.model.Payment;
import LaurenChristyJSleepRJ.jsleep_android.model.Renter;
import LaurenChristyJSleepRJ.jsleep_android.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    /**
     * Gets the account with the specified id.
     *
     * @param id the id of the account to retrieve
     * @return a {@link Call} object representing the API call
     * */
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    /**
     * Registers a new user with the specified name, email, and password.
     *
     * @param name the name of the user
     * @param email the email of the user
     * @param password the password of the user
     * @return a {@link Call} object representing the API call
     */
    @POST("account/register")
    Call<Account> register(@Query("name") String name,
                           @Query("email") String email,
                           @Query("password") String password);


    /**
     * Attempts to log in with the specified email and password.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return a {@link Call} object representing the API call
     */
    @POST("account/login")
    Call<Account> login(@Query("email") String email,
                        @Query("password") String password);


    /**
     * Adds the specified balance to the account with the specified id.
     *
     * @param id the id of the account to add balance to
     * @param balance the amount of balance to add
     * @return a {@link Call} object representing the API call
     */
    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id,
                        @Query("balance") double balance);

    /**
     * Registers a new renter for the account with the specified id.
     *
     * @param id the id of the account to register the renter
     * @param username the username of the renter
     * @param address the address of the renter
     * @param phoneNumber the phone number of the renter
     * @return a {@link Call} object representing the API call
     */
    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    /**
     * Gets a paginated list of all rooms in the system.
     *
     * @param page the page number
     * @param pageSize the number of items per page
     * @return a {@link Call} that can be enqueued to make the API request
     */
    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page,
                                @Query("pageSize") int pageSize);

    /**
     * Gets a paginated list of rooms owned by a specific renter.
     *
     * @param id the id of the renter
     * @param page the page number
     * @param pageSize the number of items per page
     * @return a {@link Call} that can be enqueued to make the API request
     */
    @GET("room/getRoomByRenter")
    Call<List<Room>> getRoomByRenter(@Path("id") int id,
                                     @Query("page") int page,
                                     @Query("pageSize") int pageSize);

    /**
     * Creates a new payment with the specified buyer and renter ids, room id, and date range.
     *
     * @param buyerId the id of the buyer
     * @param renterId the id of the renter
     * @param roomId the id of the room
     * @param from the start date of the payment
     * @param to the end date of the payment
     * @return the created {@code Payment} object
     */
    @POST("payment/create")
    Call<Payment> createPayment(@Query("buyerId") int buyerId,
                                @Query("renterId") int renterId,
                                @Query("roomId") int roomId,
                                @Query("from") String from,
                                @Query("to") String to);

    /**
     * Creates a new room in the system.
     *
     * @param accountId the id of the account that will own the new room
     * @param name the name of the new room
     * @param size the size of the new room
     * @param price the price of the new room
     * @param facility the facilities available in the new room
     * @param city the city where the new room is located
     * @param address the address of the new room
     * @param bedType the type of bed in the new room
     * @return a {@link Call} that can be enqueued to make the API request
     */
    @POST("room/create")
    Call<Room> create(@Query("accountId") int accountId,
                          @Query("name") String name,
                          @Query("size") int size,
                          @Query("price") int price,
                          @Query("facility") ArrayList<Facility> facility,
                          @Query("city") City city,
                          @Query("address") String address,
                          @Query("bedType") BedType bedType);


}