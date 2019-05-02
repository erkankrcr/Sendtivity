package com.example.sendtivity.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendtivity.R;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

   public TextView userName;
   public TextView postText;
   public ImageView userProfilePhoto;
   public ImageView postImage;

    public TimeLineViewHolder(View view){
        super(view);
        this.userName = view.findViewById(R.id.TL_Item_UserName);
        this.userProfilePhoto = view.findViewById(R.id.TL_Item_UserProfileImage);
        this.postText = view.findViewById(R.id.TL_Item_PostText);
        this.postImage = view.findViewById(R.id.TL_item_PostImage);


    }
}
