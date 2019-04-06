package com.example.sendtivity.Class;

public class ProfilePhoto {
    private String mAuthID;
    private String ImageUrl;

    public ProfilePhoto() {
        //empty constructor needed
    }

    public ProfilePhoto(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        this.mAuthID = name;
        this.ImageUrl = imageUrl;
    }

    public String getName() {
        return this.mAuthID;
    }

    public void setName(String name) {
        this.mAuthID = name;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }
}