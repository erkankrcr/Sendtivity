package com.example.sendtivity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sendtivity.R;
import com.example.sendtivity.ViewHolders.MessageListViewHolder;
import com.squareup.picasso.Picasso;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListViewHolder> {
    private Context context;

    public MessageListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.message_list_item,viewGroup,false);
        MessageListViewHolder viewHolder = new MessageListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder messageListViewHolder, final int i) {
        messageListViewHolder.PostTV.setText("deneme"+i);
        messageListViewHolder.PostUserNameTV.setText("kullanıcı"+i);
        Picasso.get().load(R.drawable.baseline_call_merge_black_48).into(messageListViewHolder.ProfilePhotoIW);
        messageListViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Basılan Mesaj : "+i,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }
}
