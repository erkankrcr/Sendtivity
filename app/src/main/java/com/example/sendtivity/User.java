package com.example.sendtivity;


public class User{
    public String Username;
    public String Name;
    public String LastName;
    public String Email;
    public String PhoneNumber;


    public User(){
        this.Username = "";
        this.Name = "";
        this.LastName = "";
        this.Email = "";
        this.PhoneNumber = "";
    }
    public User(String Username,String Name,String LastName,String Email,String PhoneNumber){
        this.Username = Username;
        this.Name = Name;
        this.LastName = LastName;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }
    public User(String Username){
        this.Username=Username;
        this.Name=getUserInfo(Username,"Name");
        this.LastName=getUserInfo(Username,"LastName");
        this.Email=getUserInfo(Username,"Email");
        this.PhoneNumber=getUserInfo(Username,"PhoneNumber");
    }
    private String getUserInfo(String Username,String InfoType){
        String isim = "erkan";
        return isim;

    }


}
