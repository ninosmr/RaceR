package net.gurba.racer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    Button cancel, login;
    TextView user, pass;
    String username, password;
    DatabaseReference databaseReference;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cancel = (Button) findViewById(R.id.cancelBtn);
        login = (Button) findViewById(R.id.loginBtn);
        user = (EditText) findViewById(R.id.userIn);
        pass = (EditText) findViewById(R.id.passIn);

        databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");




        Query query = databaseReference.orderByChild("name");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Profile profile;
                    for(DataSnapshot profileSnapshot : dataSnapshot.getChildren()){
                        profile = profileSnapshot.getValue(Profile.class);
                        users.localList.add(profile);
                        System.out.println("--------------------------------PROFILE ADDED ------------------------------------");
                        System.out.println(profile.getName());
                        System.out.println(users.localList.size());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchStartScreen();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                password = pass.getText().toString();
                if(TextUtils.isEmpty(username)|TextUtils.isEmpty(password)){
                    Toast.makeText(login.getContext(), "Please enter a username and password", Toast.LENGTH_LONG).show();
                }
                else{
                    Query query = databaseReference.orderByChild("name").equalTo(username);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Profile profile = null;
                                for(DataSnapshot profileSnapshot : dataSnapshot.getChildren()){
                                    profile = profileSnapshot.getValue(Profile.class);
                                }
                                if(profile.getPassword().equals(password)){
                                    System.out.println("works");
                                    profile.setPersonalProfile(profile);
                                    System.out.println(profile.getName());
                                    launchMainActivity(profile);
                                }
                                else{
                                    Toast.makeText(login.getContext(), "Password incorrect", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(login.getContext(), "Account does not exist", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

    }
    private void launchMainActivity(Profile profile){
        profile.setPersonalProfile(profile);
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();

    }
    private void launchStartScreen() {
        Intent startscreen = new Intent(this, startScreen.class);
        startActivity(startscreen);
        finish();
    }
}
