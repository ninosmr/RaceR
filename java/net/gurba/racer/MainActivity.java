package net.gurba.racer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipePlaceHolderView mSwipeView;
    public Context mContext;
    private Button edit, message;

    public Profile personalProfile; //dummy profile to give main access to the personal profile set on login
    public FirebaseListAdapter<Profile> adapter;
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Profiles");
    List<Profile> profileList = new ArrayList<>();
    Users users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personalProfile = new Profile();
        personalProfile = personalProfile.getPersonalProfile();
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.racer_black);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));

//---------------------------------------------POPULATING ARRAYLIST WITH VALUES FROM FIREBASE------------------------------------------------------//



//        Query query = databaseReference.orderByChild("name");
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    Profile profile;
//                    for(DataSnapshot profileSnapshot : dataSnapshot.getChildren()){
//                        profile = profileSnapshot.getValue(Profile.class);
//                        profileList.add(profile);
//                        System.out.println("--------------------------------PROFILE ADDED ------------------------------------");
//                        System.out.println(profile.getName());
//                        System.out.println(profileList.size());
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
        profileList = null;
        profileList = users.localList;

        System.out.println("--------------------------------RUNNING ------------------------------------");
        for (int i = 0; i < profileList.size(); i++) {
            Profile profile = profileList.get(i);
            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
            System.out.println("--------------------------------CARD ADDED ------------------------------------");
        }


        findViewById(R.id.messageBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchChat();
            }
        });


        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditActivity();
            }
        });
        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
    }

    public void launchEditActivity() {
        Intent edit = new Intent(this, edit.class);
        startActivity(edit);
    }

    private void launchChat() {
        Intent chat = new Intent(this, chat.class);
        startActivity(chat);
    }

    public void listUsers() {


        }

    }
