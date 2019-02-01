package net.gurba.racer;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class chat extends AppCompatActivity {
    Profile personalProfile;
    private FirebaseListAdapter<message> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        personalProfile = new Profile();
        listMessages();
        FloatingActionButton send = (FloatingActionButton)findViewById(R.id.sendBtn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                String userAndCar = personalProfile.getPersonalProfile().getName()+", "+personalProfile.getPersonalProfile().getCarModel();
                FirebaseDatabase.getInstance().getReference("Chats").push().setValue(new message(input.getText().toString(), userAndCar));

                input.setText("");
            }
        });
    }

    public void listMessages(){
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<message>(this, message.class, R.layout.message, FirebaseDatabase.getInstance().getReference("Chats")) {
            @Override
            protected void populateView(View v, message model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }
}
