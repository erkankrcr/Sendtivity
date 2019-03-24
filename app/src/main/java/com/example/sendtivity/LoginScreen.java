package com.example.sendtivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        findViewById(R.id.GirisBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this,FragmentActivity.class);
                intent.putExtra("Key","Giris");
                startActivity(intent);
            }
        });
        findViewById(R.id.KayitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this,FragmentActivity.class);
                intent.putExtra("Key","Kayıt");
                startActivity(intent);
            }
        });
    }
}
