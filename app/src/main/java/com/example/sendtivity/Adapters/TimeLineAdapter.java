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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.io.File;
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
        Picasso.get().cancelRequest(timeLineViewHolder.userProfilePhoto);
        Picasso.get().cancelRequest(timeLineViewHolder.postImage);

        if (timeLineViewHolder.itemView.getTag() == null) {

            timeLineViewHolder.postImage.setVisibility(View.VISIBLE);


            if (post.putImage){
                timeLineViewHolder.postText.setText(post.PostText);
                timeLineViewHolder.userName.setText(post.UserName);
                Picasso
                        .get()
                        .load(post.PostUserImageUrl)
                        .placeholder(R.drawable.baseline_call_merge_black_48)
                        .into(timeLineViewHolder.userProfilePhoto);
                Picasso
                        .get()
                        .load(post.PostImageUrl)
                        .placeholder(R.drawable.baseline_call_merge_black_48)
                        .into(timeLineViewHolder.postImage);

            }else{
                timeLineViewHolder.postText.setText(post.PostText);
                timeLineViewHolder.userName.setText(post.UserName);
                timeLineViewHolder.postImage.setVisibility(View.GONE);
                Picasso
                        .get()
                        .load(post.PostUserImageUrl)
                        .placeholder(R.drawable.baseline_call_merge_black_48)
                        .into(timeLineViewHolder.userProfilePhoto);
            }

            timeLineViewHolder.itemView.setTag(post);

        } else {
            timeLineViewHolder.itemView.setTag(post);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
