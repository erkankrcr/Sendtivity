package com.example.sendtivity.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendtivity.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageListViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView ProfilePhotoIW;
    public TextView MessageTV;
    public TextView MessageUserNameTV;
    public CardView cardView;
    public MessageListViewHolder(@NonNull View itemView) {
        super(itemView);
        ProfilePhotoIW = itemView.findViewById(R.id.ML_item_ProfileImage);
        MessageTV = itemView.findViewById(R.id.ML_item_Post);
        MessageUserNameTV = itemView.findViewById(R.id.ML_item_Username);
        cardView = itemView.findViewById(R.id.ML_item);
    }
}
