package com.example.sendtivity.Class;

public class PersonInfo {

    private String name;
    private String phoneNumber;
    public String getName(){

        return this.name;

    }
    public String getPhoneNumber(){

        return this.phoneNumber;
    }
    public PersonInfo(String name, String phoneNumber){

        this.name = name;
        this.phoneNumber = phoneNumber;

    }
}