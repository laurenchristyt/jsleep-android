package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import LaurenChristyJSleepRJ.jsleep_android.model.BedType;
import LaurenChristyJSleepRJ.jsleep_android.model.City;
import LaurenChristyJSleepRJ.jsleep_android.model.Facility;
import LaurenChristyJSleepRJ.jsleep_android.model.Price;
import LaurenChristyJSleepRJ.jsleep_android.model.Room;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * The CreateRoomActivity class is an Android activity that represents all of the information to create a new room.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class CreateRoomActivity extends AppCompatActivity {
    /**
     * A {@link BaseApiService} instance for making API requests.
     */
    BaseApiService mApiService;
    /**
     * The {@link Context} of the activity.
     */
    Context mContext;
    /**
     * The {@link EditText} where the renter can enter the room's name, the room's price,
     * the room's size, and the room's address.
     */
    EditText roomName, roomPrice, roomAddress, roomSize;
    /**
     * The {@link CheckBox} that the renter can choose from the room's facility- ac, refrigerator, bath tub,
     * balcony, restaurant, pool, and fitness center- is provided with the room.
     */
    CheckBox AC, Wifi, Refrigerator, Bathtub, Balcony, Resto, SwimmingP, FitnessC;
    /**
     * The {@link Spinner} where the renter can choose between the provided selection.
     */
    Spinner bedType, city;
    /**
     * Button for submitting a new room and cancel it.
     */
    Button createRoom, cancelCreateRoom;
    /**
     * Arraylist of facilities to store the facilities provided.
     */
    ArrayList<Facility> facility = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Spinner citySpinner = (Spinner) findViewById(R.id.city_spinner);
        citySpinner.setAdapter(new ArrayAdapter<City>(this, R.layout.spinnerlayout , City.values()));
        Spinner bedTypeSpinner = (Spinner) findViewById(R.id.bedType_spinner);
        bedTypeSpinner.setAdapter(new ArrayAdapter<BedType>(this, R.layout.spinnerlayout , BedType.values()));
        mApiService = UtilsApi.getApiService();
        mContext = this;


        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        createRoom = findViewById(R.id.button_createRoom);
        cancelCreateRoom = findViewById(R.id.button_cancelCreateRoom);


        createRoom.setOnClickListener(a -> {
                    createRoomRequest();
                }
        );
        cancelCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * This function is used to to request a new room.
     *
     * @return Room
     */
    protected Room createRoomRequest(){
        roomName = findViewById(R.id.room_name_et);
        roomPrice = findViewById(R.id.room_price_et);
        int price = Integer.parseInt(roomPrice.getText().toString());
        roomAddress = findViewById(R.id.room_address_et);
        roomSize = findViewById(R.id.room_size_et);
        int size = Integer.parseInt(roomSize.getText().toString());
        checkBoxFacility();
        bedType = findViewById(R.id.bedType_spinner);
        BedType bedTypeEnum = BedType.valueOf(bedType.getSelectedItem().toString());
        city = findViewById(R.id.city_spinner);
        City cityEnum = City.valueOf(city.getSelectedItem().toString());

        mApiService.create(MainActivity.accountLogin.id,
                roomName.getText().toString(),
                size,
                price,
                facility,
                cityEnum,
                roomAddress.getText().toString(),
                bedTypeEnum).enqueue(new Callback<Room>() {

            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                Toast.makeText(mContext, "Room Created", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(mContext, "Room Creation Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    void checkBoxFacility(){
        AC = findViewById(R.id.checkBox_AC);
        Wifi = findViewById(R.id.checkBox_Wifi);
        Refrigerator = findViewById(R.id.checkBox_Refri);
        Bathtub = findViewById(R.id.checkBox_bathtub);
        Balcony = findViewById(R.id.checkBox_balcony);
        Resto = findViewById(R.id.checkBox_restaurant);
        SwimmingP = findViewById(R.id.checkBox_swimpool);
        FitnessC = findViewById(R.id.checkBox_fitnessC);

        if(AC.isChecked()){
            facility.add(Facility.AC);
        }
        if(Wifi.isChecked()){
            facility.add(Facility.WiFi);
        }
        if(Refrigerator.isChecked()){
            facility.add(Facility.Refrigerator);
        }
        if(Bathtub.isChecked()){
            facility.add(Facility.Bathtub);
        }
        if(Balcony.isChecked()){
            facility.add(Facility.Balcony);
        }
        if(Resto.isChecked()){
            facility.add(Facility.Restaurant);
        }
        if(SwimmingP.isChecked()){
            facility.add(Facility.SwimmingPool);
        }
        if(FitnessC.isChecked()){
            facility.add(Facility.FitnessCenter);
        }
    }


}