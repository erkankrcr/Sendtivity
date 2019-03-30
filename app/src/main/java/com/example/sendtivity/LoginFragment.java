package com.example.sendtivity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class LoginFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Fragmente Ulaştı");

       View view =  inflater.inflate(R.layout.fragment_login, container, false);
       view.findViewById(R.id.LoginRecoBtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getActivity(),"Şifreni unuttun buttonuna basıldı",Toast.LENGTH_SHORT).show();
           }
       });
       view.findViewById(R.id.LoginBackBtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(),LoginScreen.class);
               startActivity(intent);

           }
       });

       return view;
    }

}
