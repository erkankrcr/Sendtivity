package com.example.sendtivity;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("Fragmente Ulaştı");
        mAuth = FirebaseAuth.getInstance();
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
       final EditText UsernameET = view.findViewById(R.id.LoginUsernameET);
       final EditText PasswordET = view.findViewById(R.id.LoginPasswordET);
       view.findViewById(R.id.LoginSuccesBtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email = UsernameET.getText().toString();
               String password = PasswordET.getText().toString();
               mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(getActivity(),"Başarılı", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(getActivity(),"Başarısız", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       });

       return view;
    }

}
