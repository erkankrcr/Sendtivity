package com.example.sendtivity.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.sendtivity.Fragments.MessageListFragment;
import com.example.sendtivity.Fragments.PostSendFragment;
import com.example.sendtivity.Fragments.ProfileFragment;
import com.example.sendtivity.Fragments.TimeLineFragment;
import com.example.sendtivity.Fragments.WelcomeFragment;
import com.example.sendtivity.R;
import com.example.sendtivity.Services.MessageService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends Activity implements SpaceOnClickListener {
    private SpaceNavigationView spaceNavigationView;
    public  DatabaseReference databaseReference;
    TimeLineFragment timeLineFragment;
    PostSendFragment postSendFragment;
    MessageListFragment messageListFragment;
    ProfileFragment profileFragment;
    FirebaseAuth mAuth;
    FirebaseMessagingService messagingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fabric.with(this, new Crashlytics());

        spaceNavigationCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        timeLineFragment = new TimeLineFragment();
        postSendFragment = new PostSendFragment();
        messageListFragment = new MessageListFragment();
        profileFragment = new ProfileFragment();
        mAuth = FirebaseAuth.getInstance();
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if(task.isSuccessful()){
                    Log.wtf("Token Key", task.getResult().getId());
                }
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SendtivityChannel","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }




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
                Bundle bundle = new Bundle();
                bundle.putString("mAuthID",mAuth.getUid());
                profileFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.Main_Activity_frame,profileFragment).commit();
                break;
            case 3:
                SharedPreferences preferences = getSharedPreferences("AppInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("UserJson","Null");
                editor.putBoolean("RememberMe",false);
                editor.commit();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                startActivity(intent);

                Toast.makeText(MainActivity.this,"Uygulamadan çıkıldı diye bilin :)",Toast.LENGTH_SHORT).show();
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
