package com.example.sendtivity.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sendtivity.Listeners.FragmentListener;
import com.example.sendtivity.R;


public class MainActivity extends Activity{
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_message:

                        return true;
                    case R.id.navigation_home:

                        return true;
                    case R.id.navigation_profil:

                        return true;
                    case R.id.navigation_settings:

                        return true;
                    default:
                        return true;
                }

            }
        });
    }

}
