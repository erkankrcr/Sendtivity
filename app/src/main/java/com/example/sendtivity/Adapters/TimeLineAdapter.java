package com.example.sendtivity.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Class.Post;

import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.TimeLineViewHolder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    ArrayList<Post> posts;
    Context context;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    File file1 , file2;


    public TimeLineAdapter(Context context, ArrayList<Post> posts){
        this.posts = posts;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

    }


    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_line_item,viewGroup,false);

        TimeLineViewHolder timeLineViewHolder = new TimeLineViewHolder(view);

        return timeLineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeLineViewHolder timeLineViewHolder, int i) {
        final Post post = posts.get(i);


        if (post.putImage){
            timeLineViewHolder.postText.setText(post.PostText);
            timeLineViewHolder.userName.setText(post.UserName);
            Thread UserImageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        file1 = File.createTempFile("image","jpg");
                        storageReference
                                .child("User")
                                .child(post.UserID)
                                .child("ProfilePhoto")
                                .child(post.UserProfileImageId)
                                .getFile(file1)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Picasso
                                        .get()
                                        .load(file1)
                                        .placeholder(R.drawable.baseline_call_merge_black_48)
                                        .into(timeLineViewHolder.userProfilePhoto);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
            });
            UserImageThread.run();
            Thread PostImageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        file2 = File.createTempFile("image","jpg");
                        storageReference
                                .child("Post")
                                .child(post.PostImageId)
                                .getFile(file2)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Picasso
                                        .get()
                                        .load(file2)
                                        .placeholder(R.drawable.baseline_call_merge_black_48)
                                        .into(timeLineViewHolder.postImage);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
            });
            PostImageThread.run();
        }else{
            timeLineViewHolder.postText.setText(post.PostText);
            timeLineViewHolder.userName.setText(post.UserName);
            timeLineViewHolder.postImage.setVisibility(View.GONE);
            Thread UserImageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        file1 = File.createTempFile("image","jpg");
                        storageReference.child("User").child(post.UserID).child("ProfilePhoto").child(post.UserProfileImageId).getFile(file1).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Picasso.get().load(file1).placeholder(R.drawable.baseline_call_merge_black_48).into(timeLineViewHolder.userProfilePhoto);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                }
            });
            UserImageThread.run();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
