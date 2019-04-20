package com.example.sendtivity.Class;


import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;


public class User{
    public ProfilePhoto profilePhoto;
    public String mAuthID;
    public String Name;
    public String LastName;
    public String Email;
    public String PhoneNumber;
    public ArrayList PhoneNumberList;



    public User(){
        this.profilePhoto = new ProfilePhoto();
    }
    public User(String mAuthID,String Name,String LastName,String Email,String PhoneNumber){
        this.mAuthID = mAuthID;
        this.Name = Name;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.profilePhoto = new ProfilePhoto();
    }






}
