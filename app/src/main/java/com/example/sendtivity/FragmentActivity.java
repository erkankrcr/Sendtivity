package com.example.sendtivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


public class FragmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        System.out.println("Activitye ulaştı ");

        Intent intent = getIntent();
        FragmentManager fm = getFragmentManager();
        switch (intent.getExtras().getString("Key","Null")){
            case "Giris":
                fm.beginTransaction().replace(R.id.FragmentFrame,new LoginFragment()).commit();
                break;
            case "Kayıt":
                fm.beginTransaction().replace(R.id.FragmentFrame,new SignFragment()).commit();
                break;
            default:
                Toast.makeText(this,"Değer Glemedi",Toast.LENGTH_SHORT).show();
                break;

        }



    }
}
