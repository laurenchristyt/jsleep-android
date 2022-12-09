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
    TextView renterName, renterAddress, renterPhone;

    EditText editName, editAddress, editPhone, amount;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        Account account = LoginActivity.accountLogin;

        //Account Details
        username = findViewById(R.id.nameAboutMe);
        email = findViewById(R.id.emailAboutMe);
        balance = findViewById(R.id.balanceAboutMe);
        amount = findViewById(R.id.amount);

        username.setText(LoginActivity.accountLogin.name);
        email.setText(LoginActivity.accountLogin.email);
        balance.setText(String.valueOf(LoginActivity.accountLogin.balance));

        //Button
        Button registerRenterButton = findViewById(R.id.RegisterRenterButton);
        Button cancelButton = findViewById(R.id.CancelButton);
        Button topUpButton = findViewById(R.id.TopUpButton);
        Button registerRenter = findViewById(R.id.RegisterButton);

        //Card View
        CardView renterCard = findViewById(R.id.cardViewRenter);
        CardView registerCard = findViewById(R.id.cardViewRegister);

        //Register Renter
        renterName = findViewById(R.id.RenterNameView);
        renterAddress = findViewById(R.id.RenterAddressView);
        renterPhone = findViewById(R.id.RenterPhoneNumberView);

        editName = findViewById(R.id.RegisterName);
        editAddress = findViewById(R.id.RegisterAddress);
        editPhone = findViewById(R.id.RegisterPhone);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTopUp();
            }
        });

        if(account.renter == null) {
            registerRenterButton.setVisibility(View.VISIBLE);
            renterCard.setVisibility(View.GONE);
            registerCard.setVisibility(View.GONE);

            registerRenterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    registerRenterButton.setVisibility(View.GONE);
                    renterCard.setVisibility(View.GONE);
                    registerCard.setVisibility(View.VISIBLE);

                    registerRenter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        LoginActivity.renter = requestRegister();
                            Intent move = new Intent(AboutMeActivity.this, LoginActivity.class);
                            startActivity(move);

                        }
                    });
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registerRenterButton.setVisibility(View.VISIBLE);
                            renterCard.setVisibility(View.GONE);
                            registerCard.setVisibility(View.GONE);

                            renterName.setText("");
                            renterAddress.setText("");
                            renterPhone.setText("");
                        }
                    });
                }
            });
        } else {
            registerRenterButton.setVisibility(View.GONE);
            renterCard.setVisibility(View.VISIBLE);
            registerCard.setVisibility(View.GONE);

            renterName.setText(LoginActivity.accountLogin.renter.username);
            renterAddress.setText(LoginActivity.accountLogin.renter.address);
            renterPhone.setText(LoginActivity.accountLogin.renter.phoneNumber);
        }
    }

    public Renter requestRegister() {
        mApiService.registerRenter(LoginActivity.accountLogin.id, editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString()).enqueue(new Callback<Renter>(){
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()) {
                    Renter renter;
                    renter = response.body();
                    LoginActivity.accountLogin.renter = renter;
                    System.out.println("ACCOUNT RENTER ADDED");
                    Intent move = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Register Renter Successful!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println("ACCOUNT RENTER ALREADY REGISTERED");
                System.out.println(t.toString());
                Toast.makeText(mContext, "ACCOUNT RENTER ALREADY REGISTERED!", Toast.LENGTH_LONG).show();
            }
        });
        return null;
    }

    protected Boolean requestTopUp() {
        mApiService.topUp(LoginActivity.accountLogin.id, Double.parseDouble(amount.getText().toString())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(mContext, "Top Up Successful", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(AboutMeActivity.this, AboutMeActivity.class);
                startActivity(move);


            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(mContext, "Top Up Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return false;
    }
}