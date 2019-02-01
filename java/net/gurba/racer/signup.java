package net.gurba.racer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class signup extends AppCompatActivity {
    DatabaseReference databaseReference;

    private Button cancel, signUp;
    public TextView userIn, passIn, carIn, powerIn, cityIn, picIn;
    String user, password, car, city, pic, power;
    Users users = new Users();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        cancel = (Button) findViewById(R.id.Cancel);
        signUp = (Button) findViewById(R.id.signUp);
        userIn = (TextView) findViewById(R.id.userIn);
        passIn = (TextView) findViewById(R.id.passIn);
        carIn = (TextView) findViewById(R.id.carIn);
        powerIn = (TextView) findViewById(R.id.powerIn);
        cityIn = (TextView) findViewById(R.id.cityIn);
        picIn = (TextView) findViewById(R.id.picIn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");

//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish(); //this takes you back to the main screen
//            }
//        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchStartScreen();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = userIn.getText().toString();
                password = passIn.getText().toString();
                car = carIn.getText().toString();
                power = powerIn.getText().toString();
                city = cityIn.getText().toString();
                pic = picIn.getText().toString();
                if (TextUtils.isEmpty(user) | TextUtils.isEmpty(password) | TextUtils.isEmpty(car) | TextUtils.isEmpty(city) | TextUtils.isEmpty(pic)) {
                    Toast.makeText(signup.this, "Please enter a value for every field", Toast.LENGTH_LONG).show();
                }
                else{
                Profile newProf = new Profile(user, password, car, power, city, pic);
                String id = databaseReference.push().getKey();
                databaseReference.child(id).setValue(newProf);
                Toast.makeText(signup.this, "Successfully created account!", Toast.LENGTH_LONG).show();
                launchStartScreen();
            }
            }
        });

    }
    private void launchStartScreen() {
        Intent startscreen = new Intent(this, startScreen.class);
        startActivity(startscreen);
        finish();
    }

    private void launchMain() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }

}
