package com.example.sendtivity.Class;

public class ProfilePhoto {
    public String photoID;
    public String ImageUrl;
    public boolean useProfilePhoto;

    public ProfilePhoto() {
        //empty constructor needed
    }

    public ProfilePhoto(String name, String imageUrl, boolean usePhoto) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        this.photoID = name;
        this.ImageUrl = imageUrl;
        this.useProfilePhoto = usePhoto;
    }

}