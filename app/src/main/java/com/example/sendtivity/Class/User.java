package com.example.sendtivity.Class;


import android.database.Cursor;
import android.provider.ContactsContract;


public class User{
    public ProfilePhoto profilePhoto;
    public String mAuthID;
    public String Name;
    public String LastName;
    public String Email;
    public String PhoneNumber;
    public Cursor Contact;



    public User(){

    }
    public User(String mAuthID,String Name,String LastName,String Email,String PhoneNumber){
        this.mAuthID = mAuthID;
        this.Name = Name;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }




}
