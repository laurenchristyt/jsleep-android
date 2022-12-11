package LaurenChristyJSleepRJ.jsleep_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import LaurenChristyJSleepRJ.jsleep_android.model.Payment;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreatePayment extends AppCompatActivity {
    Button orderBtn;
    TextView roomName;
    Context mContext;
    BaseApiService mApiService;
    EditText fromDate, toDate;
    final String REGEX_DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_create);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        mApiService= UtilsApi.getApiService();
        mContext = this;
        roomName = findViewById(R.id.roomName);
        roomName.setText(DetailRoomActivity.currentRoom.name);
        orderBtn = findViewById(R.id.order);
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Payment payment = requestPayment(MainActivity.accountLogin.id, MainActivity.accountLogin.renter.id, DetailRoomActivity.currentRoom.id,
                        fromDate.getText().toString(), toDate.getText().toString());
            }
        });


    }
    protected Payment requestPayment(int buyerId, int renterId, int roomId, String fromDate, String toDate) {

        System.out.println(fromDate);
        System.out.println(toDate);

        Pattern pattern = Pattern.compile(REGEX_DATE_PATTERN);
        Matcher matcher = pattern.matcher(fromDate);
        Matcher matcher2 = pattern.matcher(toDate);

        boolean isMatched = matcher.matches();
        boolean isMatched2 = matcher2.matches();


        if(isMatched && isMatched2) {
            mApiService.createPayment(buyerId, renterId, roomId, fromDate, toDate).enqueue(new Callback<Payment>() {
                @Override
                public void onResponse(Call<Payment> call, Response<Payment> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, "Payment Success", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(CreatePayment.this, ConfirmActivity.class);
                        startActivity(move);

                    } else {
                        Toast.makeText(mContext, "Payment Failed1", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Payment> call, Throwable t) {
                    Toast.makeText(mContext, "Payment Failed", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(mContext, "Date format is not valid", Toast.LENGTH_SHORT).show();
        }

        return null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


}