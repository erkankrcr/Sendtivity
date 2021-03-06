package com.example.sendtivity.Adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Class.Post;

import com.example.sendtivity.Fragments.TimeLineFragment;
import com.example.sendtivity.Listeners.FragmentListener;
import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.TimeLineViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    ArrayList<Post> posts;
    Context context;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Bundle bundle;
    TimeLineFragment timeLineFragment;
    Fragment CurrentFragment;


    public TimeLineAdapter(Context context, ArrayList<Post> posts){
        this.posts = posts;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

    }
    public TimeLineAdapter(Context context, ArrayList<Post> posts,Fragment Currentfragment){
        this.posts = posts;
        this.context = context;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        this.CurrentFragment = Currentfragment;

    }


    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.time_line_item,viewGroup,false);
         return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimeLineViewHolder timeLineViewHolder, int i) {
        final Post post = posts.get(i);

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
            timeLineViewHolder.userProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeLineFragment = new TimeLineFragment();
                    bundle = new Bundle();
                    bundle.putString("mAuthID",post.UserID);
                    timeLineFragment.setArguments(bundle);
                    FragmentListener fragmentListener = ((FragmentListener) CurrentFragment);
                    fragmentListener.FragmentReplace(timeLineFragment);
                    Log.wtf("debug","Adapter tarafı Çalıştı");

                }
            });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
