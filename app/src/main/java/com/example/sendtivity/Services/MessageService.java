package com.example.sendtivity.Services;


import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import com.example.sendtivity.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



public class MessageService extends FirebaseMessagingService {
    private static final String TAG = "MsgService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title , String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"SendtivityChannel")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.baseline_call_merge_black_18)
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());
    }


}
