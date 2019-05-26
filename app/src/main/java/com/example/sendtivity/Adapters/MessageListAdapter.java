package com.example.sendtivity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.sendtivity.Class.UserMessageClass;
import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.MessageListViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListViewHolder> {
    private Context context;
    private ArrayList<UserMessageClass> arrayList;

    public MessageListAdapter(Context context, ArrayList arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_list_item,viewGroup,false);
        return new MessageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder messageListViewHolder, final int i) {
        UserMessageClass messageClass = arrayList.get(i);
        messageListViewHolder.MessageTV.setText("Message : " + i);
        messageListViewHolder.MessageUserNameTV.setText(messageClass.Name +" "+messageClass.LastName);
        Picasso
                .get()
                .load(messageClass.profilePhoto.ImageUrl)
                .placeholder(R.drawable.baseline_call_merge_black_48)
                .into(messageListViewHolder.ProfilePhotoIW);
        messageListViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Kullanıcı ismi ve SoyIsmi : "+messageClass.Name+" "+messageClass.LastName,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
