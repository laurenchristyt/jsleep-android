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

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText roomName, roomPrice, roomAddress, roomSize;
    CheckBox AC, Wifi, Refrigerator, Bathtub, Balcony, Resto, SwimmingP, FitnessC;
    Spinner bedType, city;
    Button createRoom, cancelCreateRoom;
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


        createRoom = findViewById(R.id.button_createRoom);
        cancelCreateRoom = findViewById(R.id.button_cancelCreateRoom);


        createRoom.setOnClickListener(a -> {
                    createRoomRequest();
                    finish();
                }
        );
        cancelCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

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

        mApiService.create(LoginActivity.accountLogin.id,
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