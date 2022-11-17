package LaurenChristyJSleepRJ.jsleep_android;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(i -> startActivity(new Intent(this, MainActivity.class)));

        Button register = findViewById(R.id.registerButton);
        register.setOnClickListener(i -> startActivity(new Intent(this, RegisterActivity.class)));

    }
}