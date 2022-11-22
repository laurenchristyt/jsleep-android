package LaurenChristyJSleepRJ.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import LaurenChristyJSleepRJ.jsleep_android.request.BaseApiService;

public class AboutMeActivity extends AppCompatActivity {
    TextView name, email, balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        name = (TextView) findViewById(R.id.nameAboutMe);
        name.setText(MainActivity.accountLogin.name);
        email = (TextView) findViewById(R.id.emailAboutMe);
        email.setText(MainActivity.accountLogin.email);
        balance = (TextView) findViewById(R.id.balanceAboutMe);
        balance.setText(String.valueOf(MainActivity.accountLogin.balance));
    }
}