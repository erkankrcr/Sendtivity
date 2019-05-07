package com.example.sendtivity.Class;


public class Post {
    public String PostID;
    public String UserID;
    public String UserName;
    public String PostText;
    public String PostImageId;
    public String UserProfileImageId;
    public String PostImageUrl;
    public String PostUserImageUrl;
    public  boolean putImage;

    public Post(){

    }
    public Post(String postID ,String userID , String UserName, String postText ,String postImageId ,String postImageUrl,String postUserImageUrl, String userProfileImageId){

        this.PostID = postID;
        this.UserID = userID;
        this.PostText = postText;
        this.UserName = UserName;
        this.UserProfileImageId = userProfileImageId;
        this.PostImageId = postImageId;
        this.PostImageUrl = postImageUrl;
        this.PostUserImageUrl = postUserImageUrl;
        this.putImage = false;
    }

}
