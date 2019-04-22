package com.example.sendtivity.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sendtivity.R;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    public TextView postID;
    public TextView postText;
    public TextView userID;

    public TimeLineViewHolder(View view){
        super(view);
        postID = ((TextView) view.findViewById(R.id.TL_item_postID));
        postText = ((TextView) view.findViewById(R.id.TL_item_postText));
        userID = ((TextView) view.findViewById(R.id.TL_item_UserID));

    }
}
