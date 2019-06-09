package com.example.sendtivity.Adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Class.MessageClass;
import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.MessageItemVH;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemVH> {
    ArrayList<MessageClass> arrayList;
    MessageClass messageClass;
    FirebaseAuth mAuth;

    public MessageItemAdapter(ArrayList arrayList){
        this.arrayList = arrayList;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MessageItemVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_item,viewGroup,false);
        return new MessageItemVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemVH messageItemVH, int i) {
        messageClass = arrayList.get(i);
        String messageSenderId = mAuth.getCurrentUser().getUid();

        messageItemVH.senderCardView.setVisibility(View.GONE);
        messageItemVH.receiverCardView.setVisibility(View.GONE);
        messageItemVH.messageSenderImage.setVisibility(View.GONE);
        messageItemVH.messageReceiverImage.setVisibility(View.GONE);




        if (messageClass.getType().equals("text"))
        {
            if (messageClass.getFrom().equals(messageSenderId))
            {
                messageItemVH.senderCardView.setVisibility(View.VISIBLE);

                messageItemVH.senderMessageText.setText(messageClass.getMessage());
            }
            else
            {
                messageItemVH.receiverCardView.setVisibility(View.VISIBLE);

                messageItemVH.receiverMessageText.setText(messageClass.getMessage());
            }
        }


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
