package com.example.sendtivity.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.view.Display;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.R;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Fabric.with(this, new Crashlytics());
        
        isInternetCheck();

    }

    public void isInternetCheck(){
        if (isInternetOn()){
            Toast.makeText(SplashActivity.this,"Link Start!",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    SharedPreferences sharedPreferences = getSharedPreferences("AppInfo",MODE_PRIVATE);

                    if (sharedPreferences.getBoolean("RememberMe",false)){
                        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashActivity.this,PhoneNumberActivity.class);
                        startActivity(intent);
                    }

                }
            },1000);
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
            builder
                    .setTitle("Olamaz!")
                    .setMessage("İnternet bağlantısı olmayan bir cihaz mı kullanıyorsun ? Çok şey kaçırıyorsun dostum :)")
                    .setCancelable(false)
                    .setNegativeButton("Bana 1 Saniye Ver Hemen Halledicem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isInternetCheck();
                                }
                            },1000);
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    public final boolean isInternetOn() {

        ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED )
        {
            return true;
        } else if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  )
        {

            return false;
        }
        return false;
    }

}
