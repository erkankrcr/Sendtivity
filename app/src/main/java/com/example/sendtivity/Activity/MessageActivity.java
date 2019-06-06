package com.example.sendtivity.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.R;

import io.fabric.sdk.android.Fabric;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Fabric.with(this, new Crashlytics());

    }
}
