package LaurenChristyJSleepRJ.jsleep_android;

import LaurenChristyJSleepRJ.jsleep_android.model.Account;
import LaurenChristyJSleepRJ.jsleep_android.model.Room;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.*;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import android.view.*;
import android.widget.*;
import android.os.Bundle;
import java.io.*;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Gson gson = new Gson();
    protected static Account accountLogin = null;
    protected static Account accountRegister;

    public static Account accountLogin() {
        return accountLogin;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String jsonString = toStringJSON(getApplicationContext(), "randomRoomList.json");
        Type token = new TypeToken<ArrayList<Room>>(){}.getType();
        ArrayList<Room> room = gson.fromJson(jsonString, token);

        ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(this, R.layout.roomlistview, room);
        ListView listView = findViewById(R.id.room_list_view);
        listView.setAdapter(adapter);
    }

    private static String toStringJSON(Context context, String nameFile) {
        String Json;
        try {
            InputStream is = context.getAssets().open(nameFile);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);

            is.close();
            Json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Json;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_account:
                Toast.makeText(this, "Opening", Toast.LENGTH_SHORT).show();
                Intent aboutMi = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(aboutMi);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}