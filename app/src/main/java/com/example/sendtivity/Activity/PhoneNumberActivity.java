package com.example.sendtivity.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.R;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class PhoneNumberActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        Fabric.with(this, new Crashlytics());


        final CountryCodePicker spinner = findViewById(R.id.PhoneSpinner);
        final EditText NumberET = findViewById(R.id.PhoneNumberET);


        findViewById(R.id.PhoneNumberRestoreBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneNumberActivity.this,PN_RestoreActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.PhoneResumeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = spinner.getSelectedCountryCodeWithPlus() + NumberET.getText().toString();
                Intent intent = new Intent(PhoneNumberActivity.this,PhoneVerifyActivity.class);
                if(number.isEmpty() || NumberET.getText().toString().length()<10){
                    Toast.makeText(
                            PhoneNumberActivity.this,
                            "Telefon Numarası boş veya eksik girildi",
                            Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("Number", number);
                    startActivity(intent);
                }
            }
        });



    }
}
