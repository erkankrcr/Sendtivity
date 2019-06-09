package com.example.sendtivity.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.Adapters.MessageItemAdapter;
import com.example.sendtivity.Class.MessageClass;
import com.example.sendtivity.Class.UserMessageClass;
import com.example.sendtivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;

public class MessageActivity extends AppCompatActivity {
    Gson gson;
    UserMessageClass userMessageClass;
    Intent intent;
    TextView UserNameAndSurnameTV;
    String messageSenderID,messageReceiverID;
    MessageInput messageInput;
    CircleImageView UserProfilePhoto;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ArrayList MessageList;
    RecyclerView recyclerView;
    MessageItemAdapter messageItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Fabric.with(this, new Crashlytics());
        gson = new Gson();


        intent = getIntent();
        userMessageClass = gson.fromJson(intent.getStringExtra("MessageUser"),UserMessageClass.class);


        UserNameAndSurnameTV = findViewById(R.id.MessagePage_User_NameAndSurname);
        UserProfilePhoto = findViewById(R.id.MessagePage_User_ProfileImage);
        messageInput = findViewById(R.id.MessagePage_Message_Send);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getUid();
        messageReceiverID = userMessageClass.mAuthID;

        MessageList = new ArrayList();
        recyclerView = findViewById(R.id.MessagePage_Message_Recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
        messageItemAdapter = new MessageItemAdapter(MessageList);
        recyclerView.setAdapter(messageItemAdapter);

        UserNameAndSurnameTV.setText(userMessageClass.Name + " "+userMessageClass.LastName);

        Picasso
                .get()
                .load(userMessageClass.profilePhoto.ImageUrl)
                .placeholder(R.drawable.baseline_call_merge_black_48)
                .into(UserProfilePhoto);



        messageInput.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                MessageSend(input.toString());
                return false;
            }
        });

    }

    private void MessageSend(String input) {
        if (TextUtils.isEmpty(input))
        {
            Toast.makeText(this, "Boş Mesaj Gönderemezsiniz", Toast.LENGTH_SHORT).show();
        }else{

            String messageSenderRef = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String messageReceiverRef = "Messages/" + messageReceiverID + "/" + messageSenderID;

            DatabaseReference userMessageKeyRef = databaseReference.child("Messages")
                    .child(messageSenderID).child(messageReceiverID).push();

            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", input);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            messageTextBody.put("to", messageReceiverID);
            messageTextBody.put("messageID", messagePushID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put( messageReceiverRef + "/" + messagePushID, messageTextBody);

            databaseReference.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(MessageActivity.this, "Mesaj Gönderildi...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MessageActivity.this, "Error : "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        Crashlytics.log(task.getException().toString());
                    }
                    messageInput.getInputEditText().setText("");
                }
            });
        }


        }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.child("Messages").child(messageSenderID).child(messageReceiverID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    MessageClass messageClass = dataSnapshot.getValue(MessageClass.class);
                    MessageList.add(messageClass);
                    messageItemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
