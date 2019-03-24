package com.example.sendtivity;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Fragmente Ulaştı");

       View view =  inflater.inflate(R.layout.fragment_login, container, false);

       return view;
    }

}
