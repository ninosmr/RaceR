package net.gurba.racer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class edit extends AppCompatActivity {

    DatabaseReference databaseReference;
    private Button cancel, save;
    String user, password, car, city, pic, power;
    public TextView userIn, passIn, carIn, powerIn, cityIn, picIn;
    Profile personalProf;
    static String origKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        personalProf = new Profile();
        personalProf = personalProf.getPersonalProfile();
        cancel = (Button) findViewById(R.id.Cancel);
        save = (Button) findViewById(R.id.save);
        userIn = (TextView) findViewById(R.id.userIn);
        passIn = (TextView) findViewById(R.id.passIn);
        carIn = (TextView) findViewById(R.id.carIn);
        powerIn = (TextView) findViewById(R.id.powerIn);
        cityIn = (TextView) findViewById(R.id.cityIn);
        picIn = (TextView) findViewById(R.id.picIn);


        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");

        Query query = databaseReference.orderByChild("name").equalTo(personalProf.getName());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot profileSnapshot : dataSnapshot.getChildren()){
                        origKey = profileSnapshot.getKey(); //-----------------------------------------------------------------------------------------------------//
                        System.out.println(origKey); } } }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}});

        userIn.setText(personalProf.getName());
        passIn.setText(personalProf.getPassword());
        carIn.setText(personalProf.getCarModel());
        powerIn.setText(personalProf.getPower());
        cityIn.setText(personalProf.getLocation());
        picIn.setText(personalProf.getImageUrl());

        user = userIn.getText().toString();
        password = passIn.getText().toString();
        car = carIn.getText().toString();
        power = powerIn.getText().toString();
        city = cityIn.getText().toString();
        pic = picIn.getText().toString();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("gurba");
                System.out.println(origKey);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(origKey);
                if (TextUtils.isEmpty(user) | TextUtils.isEmpty(password) | TextUtils.isEmpty(car) | TextUtils.isEmpty(city) | TextUtils.isEmpty(pic)) {
                    Toast.makeText(edit.this, "Please enter a value for every field", Toast.LENGTH_LONG).show();
                }
                else{
                    user = userIn.getText().toString();
                    password = passIn.getText().toString();
                    car = carIn.getText().toString();
                    power = powerIn.getText().toString();
                    city = cityIn.getText().toString();
                    pic = picIn.getText().toString();

                    Profile newProf = new Profile(user, password, car, power, city, pic);
                    databaseReference.child(origKey).child("name").setValue(user);
                    databaseReference.child(origKey).child("password").setValue(password);
                    databaseReference.child(origKey).child("carModel").setValue(car);
                    databaseReference.child(origKey).child("power").setValue(power);
                    databaseReference.child(origKey).child("location").setValue(city);
                    databaseReference.child(origKey).child("imageUrl").setValue(pic);

                    newProf.setPersonalProfile(newProf);
                    Toast.makeText(edit.this, "Successfully updated!", Toast.LENGTH_LONG).show();
                    launchStartScreen();
                }
            }
        });
    }

    public void launchStartScreen() {
        Intent startscreen = new Intent(this, startScreen.class);
        startActivity(startscreen);
    }
}
