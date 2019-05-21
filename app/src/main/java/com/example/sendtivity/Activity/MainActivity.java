package com.example.sendtivity.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sendtivity.Class.Post;
import com.example.sendtivity.Class.User;
import com.example.sendtivity.Fragments.MessageListFragment;
import com.example.sendtivity.Fragments.PostSendFragment;
import com.example.sendtivity.Fragments.TimeLineFragment;
import com.example.sendtivity.Fragments.WelcomeFragment;
import com.example.sendtivity.Listeners.FragmentListener;
import com.example.sendtivity.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;


public class MainActivity extends Activity implements FragmentListener , SpaceOnClickListener {
    private SpaceNavigationView spaceNavigationView;
    public  DatabaseReference databaseReference;
    TimeLineFragment timeLineFragment;
    PostSendFragment postSendFragment;
    MessageListFragment messageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spaceNavigationCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        timeLineFragment = new TimeLineFragment();
        postSendFragment = new PostSendFragment();
        messageListFragment = new MessageListFragment();




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }


    public void spaceNavigationCreate(Bundle savedInstanceState){
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.Main_Activity_navigation);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Mesajlaşma", R.drawable.baseline_message_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Akış", R.drawable.baseline_home_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Profil", R.drawable.baseline_person_black_48));
        spaceNavigationView.addSpaceItem(new SpaceItem("Ayarlar", R.drawable.baseline_settings_black_48));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.changeCurrentItem(-1);
        getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame,new WelcomeFragment()).commit();

        spaceNavigationView.setSpaceOnClickListener(this);
    }

    @Override
    public void FragmentReplace(Fragment fragmentParameter) {

    }

    @Override
    public void onCentreButtonClick() {
        Toast.makeText(MainActivity.this,"onCentreButtonClick", Toast.LENGTH_SHORT).show();
                /*
                String PostKey = databaseReference.push().getKey();
                SharedPreferences sharedPreferences = getSharedPreferences("AppInfo",MODE_PRIVATE);
                String UserJson = sharedPreferences.getString("UserJson","null");
                Gson gson = new Gson();
                User user = gson.fromJson(UserJson,User.class);
                */
        getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame, postSendFragment,"PS_Fragment").commit();


    }

    @Override
    public void onItemClick(int itemIndex, String itemName) {
        Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

        switch (itemIndex){
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame,messageListFragment).commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame,timeLineFragment).commit();
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemReselected(int itemIndex, String itemName) {
        Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

    }
}
