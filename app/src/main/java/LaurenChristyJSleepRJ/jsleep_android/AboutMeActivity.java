package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import LaurenChristyJSleepRJ.jsleep_android.model.Account;
import LaurenChristyJSleepRJ.jsleep_android.model.Renter;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    TextView username, email, balance;
    TextView name, address, phoneNumber;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        username = (TextView) findViewById(R.id.nameAboutMe);
        username.setText(MainActivity.accountLogin.name);
        email = (TextView) findViewById(R.id.emailAboutMe);
        email.setText(MainActivity.accountLogin.email);
        balance = (TextView) findViewById(R.id.balanceAboutMe);
        balance.setText(String.valueOf(MainActivity.accountLogin.balance));

        CardView renterCard = findViewById(R.id.cardViewRenter);
        CardView registerCard = findViewById(R.id.cardViewRegister);

        Button registerRenterButton = findViewById(R.id.RegisterRenterButton);
        Button cancelButton = findViewById(R.id.CancelButton);
        Button topUpButton = findViewById(R.id.TopUpButton);
        Button registerRenter = findViewById(R.id.RegisterButton);

        if ((MainActivity.accountLogin().renter == null)) {
            registerRenterButton.setVisibility(View.VISIBLE);
            renterCard.setVisibility(View.GONE);
        } else {
            registerRenterButton.setVisibility(View.GONE);
            renterCard.setVisibility(View.VISIBLE);
            TextView renterName = findViewById(R.id.RenterNameView);
            renterName.setText(MainActivity.accountLogin().renter.username);
            TextView renterAddress = findViewById(R.id.RenterAddressView);
            renterAddress.setText(MainActivity.accountLogin().renter.address);
            TextView renterPhoneNumber = findViewById(R.id.RenterPhoneNumberView);
            renterPhoneNumber.setText(MainActivity.accountLogin().renter.phoneNumber);
        }

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTopUp();
            }
        });

        registerRenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerRenterButton.setVisibility(View.GONE);
                registerCard.setVisibility(View.VISIBLE);
            }
            });

        registerRenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestRenter();
                }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerRenterButton.setVisibility(View.VISIBLE);
                registerCard.setVisibility(View.GONE);
            }
        });


    }

    protected Renter requestRenter(){
        System.out.println("RENTER : " + name.getText().toString() + address.getText().toString() + phoneNumber.getText().toString());
        mApiService.registerRenter(name.getText().toString(), address.getText().toString(), phoneNumber.getText().toString()).enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Boolean requestTopUp(){
        mApiService.topUp(0, Double.parseDouble(balance.getText().toString())).enqueue(new Callback<Boolean>(){
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, "Successful", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}