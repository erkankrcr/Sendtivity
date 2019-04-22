package com.example.sendtivity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Class.Post;

import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.TimeLineViewHolder;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    ArrayList<Post> posts;
    LayoutInflater inflater;


    public TimeLineAdapter(Context context, ArrayList<Post> posts){
        this.posts = posts;
        inflater = LayoutInflater.from(context);

    }


    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.time_line_item,viewGroup,false);

        TimeLineViewHolder timeLineViewHolder = new TimeLineViewHolder(view);

        return timeLineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder timeLineViewHolder, int i) {
        Post post = posts.get(i);

        timeLineViewHolder.postID.setText(post.PostID);
        timeLineViewHolder.postText.setText(post.PostText);
        timeLineViewHolder.userID.setText(post.UserID);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
