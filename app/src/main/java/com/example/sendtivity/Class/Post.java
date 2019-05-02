package com.example.sendtivity.Class;


public class Post {
    public String PostID;
    public String UserID;
    public String UserName;
    public String PostText;
    public String PostImage;
    public String UserProfileImageUri;

    public Post(){

    }
    public Post(String postID ,String userID , String UserName, String postText ,String postImage , String userProfileImage){

        this.PostID = postID;
        this.UserID = userID;
        this.PostText = postText;
        this.UserName = UserName;
        this.UserProfileImageUri = userProfileImage;
        this.PostImage = postImage;
    }

}
