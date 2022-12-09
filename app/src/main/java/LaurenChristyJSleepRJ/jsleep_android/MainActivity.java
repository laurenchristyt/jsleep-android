package LaurenChristyJSleepRJ.jsleep_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.view.Menu;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import LaurenChristyJSleepRJ.jsleep_android.model.Room;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText pageSize_et;
    Button nextBtn, prevBtn, goBtn;
    ListView listView;
    int currPage = 0;
    static List<Room> allRooms = new ArrayList<Room>();
    public static int selectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        //starter
        getAllRooms();

        //button control
        nextBtn = findViewById(R.id.next_button);
        nextBtn.setOnClickListener(i -> {
            currPage++;
            getAllRooms();
        });
        prevBtn = findViewById(R.id.previous_button);
        prevBtn.setOnClickListener(i -> {
            currPage = currPage == 0 ? 0 : currPage - 1;
            getAllRooms();
        });
        goBtn = findViewById(R.id.go_button);
        goBtn.setOnClickListener(i -> {
            getAllRooms();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            Intent intent = new Intent(MainActivity.this, DetailRoomActivity.class);
            startActivity(intent);
        });



    }

    protected ArrayList<Room> getAllRooms() {
        pageSize_et = findViewById(R.id.index_number);
        listView = findViewById(R.id.room_list_view);
        String pageSizeStr = pageSize_et.getText().toString();
        int pageSize = pageSizeStr.equals("") ? 12 : Integer.parseInt(pageSizeStr);
        System.out.println("paging : " + pageSize + " " + currPage);

        mApiService.getAllRoom(currPage, pageSize).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                allRooms = response.body();
                if (allRooms == null) {
                    Toast.makeText(mContext, "No Room Found", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<Room> adapter = new ArrayAdapter<Room>(mContext, R.layout.roomlistview, allRooms);
                    listView.invalidateViews();
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to load the room", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        if (LoginActivity.accountLogin.renter == null) {
            menu.findItem(R.id.add_button).setVisible(false);
        } else {
            menu.findItem(R.id.add_button).setVisible(true);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.my_account) {
            Toast.makeText(this, "Opening Profile", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, AboutMeActivity.class));
            return true;
        }
        else if(item.getItemId() == R.id.add_button) {
            Toast.makeText(this, "Opening Create Room", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, CreateRoomActivity.class));
            return true;
        }
        else if(item.getItemId() == R.id.search_button) {
            Toast.makeText(this, "Searching", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            throw new IllegalStateException("Invalid button : " + item.getItemId());
        }
    }

    public static Room getSelectedRoom() {
        return allRooms.get(selectedPos);
    }
}