package com.example.sendtivity.Class;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PNR_Class {
    public String email;
    public String phoneNumber;
    public User userInfo;

    public PNR_Class(String email,String phoneNumber){
        this.email=email;
        this.phoneNumber=phoneNumber;
        getUser();
    }

    public void getUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    User user = (User) ds.getValue();
                    if(user.PhoneNumber==phoneNumber && user.Email==email){
                        userInfo = user;
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("OnCancelled Oldu");

            }
        });



    }

}
