package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sendtivity.Adapters.MessageListAdapter;
import com.example.sendtivity.Class.MessageListClass;
import com.example.sendtivity.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageListFragment extends Fragment {
    RecyclerView recyclerView;
    View view;
    String value;
    FirebaseAuth mAuth;
    Gson gson;
    MessageListClass listClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.message_list_fragment,container,false);

        recyclerView = view.findViewById(R.id.Message_list_recyclerview);
        mAuth = FirebaseAuth.getInstance();
        gson = new Gson();

        value = Get("https://us-central1-sendtivity.cloudfunctions.net/GetMessageLineWeb?mAuthID="+mAuth.getUid());
        listClass = gson.fromJson(value, MessageListClass.class);
        MessageListAdapter adapter = new MessageListAdapter(getActivity(),listClass.Result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);




        return view;
    }


    String Get(String url) {
        Response response;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            response  = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
