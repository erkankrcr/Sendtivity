package com.example.sendtivity.Class;


import android.net.Uri;

public class Post {
    public String PostID;
    public String UserID;
    public String UserName;
    public String PostText;
    public String PostImageId;
    public String UserProfileImageId;
    public  boolean putImage;

    public Post(){

    }
    public Post(String postID ,String userID , String UserName, String postText ,String postImage , String userProfileImage){

        this.PostID = postID;
        this.UserID = userID;
        this.PostText = postText;
        this.UserName = UserName;
        this.UserProfileImageId = userProfileImage;
        this.PostImageId = postImage;
        this.putImage = false;
    }

}
