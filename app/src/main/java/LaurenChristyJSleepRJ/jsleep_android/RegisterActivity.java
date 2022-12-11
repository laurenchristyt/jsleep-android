package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import LaurenChristyJSleepRJ.jsleep_android.model.Account;
import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;
import LaurenChristyJSleepRJ.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The RegisterActivity class is an Android activity that represents the register session to JSleep.
 *
 * @author Lauren Christy Tanudjaja
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {
    /**
     * A {@link BaseApiService} instance for making API requests.
     */
    BaseApiService mApiService;

    /**
     * The {@link Context} of the activity.
     */
    Context mContext;
    /**
     * The {@link EditText} where the user can enter their desired name, email and password to make a new account.
     */
    EditText name, email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try
        {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e){}

        Button registerButton = findViewById(R.id.registerButtonLink);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = requestRegister();
            }
        });
    }

    /**
     * This function is used to request register to the server
     *
     * @return Account object
     * @see Account
     */
    protected Account requestRegister(){
        mApiService.register(name.getText().toString(), email.getText().toString(), password.getText().toString()).enqueue(new Callback<Account>(){
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    Intent move = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(move);
                    Toast.makeText(mContext, "account registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(mContext, "account already registered", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}