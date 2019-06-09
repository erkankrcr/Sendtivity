package com.example.sendtivity.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendtivity.R;

public class MessageItemVH extends RecyclerView.ViewHolder {

    public TextView senderMessageText, receiverMessageText;
    public ImageView messageSenderImage, messageReceiverImage;
    public CardView senderCardView, receiverCardView;

    public MessageItemVH(@NonNull View itemView) {
        super(itemView);
        this.senderMessageText = itemView.findViewById(R.id.Message_item_Sender_MessageText);
        this.receiverMessageText = itemView.findViewById(R.id.Message_item_Reciever_MessageText);
        this.messageSenderImage = itemView.findViewById(R.id.Message_item_Sender_MessageImage);
        this.messageReceiverImage = itemView.findViewById(R.id.Message_item_Reciever_MessageImage);
        this.senderCardView = itemView.findViewById(R.id.Message_item_Sender_CardView);
        this.receiverCardView = itemView.findViewById(R.id.Message_item_Reciever_CardView);
    }
}
