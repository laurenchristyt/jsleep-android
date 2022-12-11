package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import LaurenChristyJSleepRJ.jsleep_android.model.Facility;
import LaurenChristyJSleepRJ.jsleep_android.model.Room;

public class DetailRoomActivity extends AppCompatActivity {
    TextView roomName, roomBedType, roomSize, roomPrice, roomAddress;
    CheckBox AC, Wifi, Refrigerator, Bathtub, Balcony, Resto, SwimmingP, FitnessC;
    static Room currentRoom;

    static Button rentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        roomName = findViewById(R.id.room_detail_name);
        roomBedType = findViewById(R.id.room_detail_bedtype);
        roomSize = findViewById(R.id.room_detail_size);
        roomPrice = findViewById(R.id.room_detail_price);
        roomAddress = findViewById(R.id.room_detail_address);
        AC = findViewById(R.id.checkBox_AC);
        Wifi = findViewById(R.id.checkBox_Wifi);
        Refrigerator = findViewById(R.id.checkBox_Refri);
        Bathtub = findViewById(R.id.checkBox_bathtub);
        Balcony = findViewById(R.id.checkBox_balcony);
        Resto = findViewById(R.id.checkBox_restaurant);
        SwimmingP = findViewById(R.id.checkBox_swimpool);
        FitnessC = findViewById(R.id.checkBox_fitnessC);

        roomName.setText(currentRoom.name);
        roomBedType.setText(""+ currentRoom.bedType);
        roomSize.setText(""+currentRoom.size);
        roomPrice.setText(String.valueOf(currentRoom.price.price));
        roomAddress.setText(currentRoom.address);


        int i;
        for (i = 0; i < currentRoom.facility.size(); i++){
            if(currentRoom.facility.get(i).equals(Facility.AC)){
                AC.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.WiFi)){
                Wifi.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.Refrigerator)){
                Refrigerator.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.Bathtub)){
                Bathtub.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.Balcony)){
                Balcony.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.Restaurant)){
                Resto.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.SwimmingPool)){
                SwimmingP.setChecked(true);
            }
            if(currentRoom.facility.get(i).equals(Facility.FitnessCenter)){
                FitnessC.setChecked(true);
            }
        }

        rentNow = findViewById(R.id.btnRentRoom);
        rentNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent move = new Intent(DetailRoomActivity.this, CreatePayment.class);
                startActivity(move);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

}

