package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sendtivity.Adapters.TimeLineAdapter;
import com.example.sendtivity.Class.GetTimeLineClass;
import com.example.sendtivity.Class.Post;
import com.example.sendtivity.Class.User;
import com.example.sendtivity.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileFragment extends Fragment {
    CircleImageView UserImageView;
    TextView UserNameAndSurnameTV;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    Gson gson;
    TimeLineAdapter adapter;
    GetTimeLineClass timeLineClass;
    User user;
    String mAuthID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.profile_fragment,container,false);

        UserImageView = view.findViewById(R.id.Profile_User_Image);
        UserNameAndSurnameTV = view.findViewById(R.id.Profile_NameAndSurname);
        recyclerView = view.findViewById(R.id.Profile_UserPost_Recycler);
        gson = new Gson();
        timeLineClass = new GetTimeLineClass();
        mAuthID = getArguments().getString("mAuthID");
        String UserValue = Get("https://us-central1-sendtivity.cloudfunctions.net/GetUserWeb?mAuthID="+mAuthID);
        String PostValue = Get("https://us-central1-sendtivity.cloudfunctions.net/GetProfileLineWeb?mAuthID="+mAuthID);
        timeLineClass = gson.fromJson(PostValue,GetTimeLineClass.class);
        user = gson.fromJson(UserValue,User.class);



        UserNameAndSurnameTV.setText(user.Name+" "+user.LastName);
        Picasso
                .get()
                .load(user.profilePhoto.ImageUrl)
                .placeholder(R.drawable.baseline_person_black_48)
                .into(UserImageView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);


        adapter = new TimeLineAdapter(getActivity(),timeLineClass.Result);

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
