package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import com.google.gson.Gson;

import LaurenChristyJSleepRJ.jsleep_android.model.Account;
import LaurenChristyJSleepRJ.jsleep_android.model.Renter;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The LoginActivity class is an Android activity that represents the login session to JSleep.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * A {@link BaseApiService} instance for making API requests.
     */
    BaseApiService mApiService;
    /**
     * The {@link EditText} where the user can enter their email and password.
     */
    EditText username, password;
    /**
     * The {@link Context} of the activity.
     */
    Context mContext;
    /**
     * Static instance of Renter to be shared across the application.
     */
    static Renter renter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}


        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(i -> startActivity(new Intent(this, MainActivity.class)));

        TextView registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(i -> startActivity(new Intent(this, RegisterActivity.class)));

        mApiService = UtilsApi.getApiService();
        mContext = this;

        TextView register = findViewById(R.id.registerButton);
        username = findViewById(R.id.usernameTextBoxLogin);
        password = findViewById(R.id.passwordTextBoxLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account login = requestLogin();

            }
        });

    }
    protected Account requestAccount(){
        mApiService.getAccount(0).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Account account;
                    account = response.body();
                    System.out.println(account.toString());
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "no Account id", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

    protected Account requestLogin(){
        mApiService.login(username.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.accountLogin = response.body();
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "Login Successful", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "wrong password or email", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }


}