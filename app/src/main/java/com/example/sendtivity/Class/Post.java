package com.example.sendtivity.Class;

public class Post {
    public String PostID;
    public String UserID;
    public String PostText;

    public Post(){

    }
    public Post(String postID ,String userID ,String postText){

        this.PostID = postID;
        this.UserID = userID;
        this.PostText = postText;
    }

}
