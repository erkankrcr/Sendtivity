package com.example.sendtivity.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sendtivity.Class.User;
import com.example.sendtivity.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;


public class PostSendFragment extends Fragment {

    SharedPreferences sharedPreferences;
    String UserJson;
    Gson gson;
    ImageView UserProfilePhoto;
    TextView UserName;
    ImageView PostImage;

    public PostSendFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.post_send_fragment,container,false);
        sharedPreferences = getActivity().getSharedPreferences("AppInfo", Context.MODE_PRIVATE);
        UserJson = sharedPreferences.getString("UserJson","null");
        gson = new Gson();
        User user = gson.fromJson(UserJson,User.class);
        UserProfilePhoto = view.findViewById(R.id.PS_Image_UserProfileImage);
        UserName = view.findViewById(R.id.PS_Text_UserName);

       Picasso.get().load(user.profilePhoto.ImageUrl).placeholder(R.drawable.baseline_call_merge_black_48).into(UserProfilePhoto);
        UserName.setText(user.Name + " " +user.LastName);




        return view;
    }
}
