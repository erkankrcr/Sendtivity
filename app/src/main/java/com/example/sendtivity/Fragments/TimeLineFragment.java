package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Adapters.TimeLineAdapter;
import com.example.sendtivity.Class.Post;
import com.example.sendtivity.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeLineFragment extends Fragment {

    DatabaseReference databaseReference;

    ArrayList<Post> posts = new ArrayList<>();

    RecyclerView recyclerView;

    RecyclerView.Adapter adapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.timel_line_fragment,container,false);

        recyclerView = view.findViewById(R.id.TL_recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Post post = snapshot.getValue(Post.class);

                    posts.add(post);
                }

                adapter = new TimeLineAdapter(getActivity(),posts);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });



        return view;
    }
}
