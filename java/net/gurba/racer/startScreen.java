package net.gurba.racer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class startScreen extends AppCompatActivity {
    Button register, login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        register = (Button) findViewById(R.id.registerBtn);
        login = (Button) findViewById(R.id.loginBtn);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity();
            }
        });

    }

    private void launchRegisterActivity() {
        Intent signup = new Intent(this, signup.class);
        startActivity(signup);
        finish();
    }

    private void launchLoginActivity() {
        Intent loginActivity = new Intent(this, login.class);
        startActivity(loginActivity);
        finish();
    }
}
