package com.example.sendtivity.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sendtivity.R;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;


public class MainActivity extends Activity{
    private SpaceNavigationView spaceNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceNavigationCreate(savedInstanceState);



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }


    public void spaceNavigationCreate(Bundle savedInstanceState){
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.navigation);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Mesajlaşma", R.drawable.baseline_message_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Akış", R.drawable.baseline_home_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Profil", R.drawable.baseline_person_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Ayarlar", R.drawable.baseline_settings_black_48));
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
