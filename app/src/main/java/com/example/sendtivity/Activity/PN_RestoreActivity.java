package com.example.sendtivity.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.Class.PNR_Class;
import com.example.sendtivity.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.fabric.sdk.android.Fabric;

public class PN_RestoreActivity extends Activity {
    String arr[] = {"+90"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pn__restore);

        Fabric.with(this, new Crashlytics());


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        final EditText emailET = findViewById(R.id.PNR_emailET);
        final EditText numberET = findViewById(R.id.PNR_numberET);
        final Spinner spinner = findViewById(R.id.PNR_Spinner);
        spinner.setAdapter(new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arr));

        findViewById(R.id.PNR_SuccessBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PNR_Class pnr = new PNR_Class((String.valueOf(spinner.getSelectedItem()) + emailET.getText().toString()),numberET.getText().toString());
                myRef.child("Recovery").push().setValue(pnr);
            }
        });


    }
}
