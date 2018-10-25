package com.example.danghao195.notificationproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static int notiId = 0;
    NotificationManager notificationManager;
    PendingIntent pIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String longText = "Notification.Builder cung cấp một interface builder để tạo ra một đối tượng Notification. Bạn sử dụng một PendingIntent để chỉ định";
        Intent intent = new Intent(this, MainActivity.class);
        pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis()+500000, intent, 0);
        Notification n;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        n = createNotification(pIntent);
        notificationManager.notify(notiId++, n);

    }

    public void getNotification(View view) {
        notificationManager.notify(notiId, createNotification( pIntent ));
    }
    public Notification createNotification(PendingIntent pIntent){
        Notification n;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";//getString(R.string.channel_name);
            String description ="this is description of notification";// getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            String KEY_TEXT_REPLY = "key_text_reply";
            String replyLabel = "reply";
            RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                    .setLabel(replyLabel)
                    .build();
            // Create the reply action and add the remote input.
            NotificationCompat.Action action =
                    new NotificationCompat.Action.Builder(R.drawable.ic_launcher_background,
                            "action", pIntent)
//                            .addRemoteInput(remoteInput)
                            .build();
            n  = new NotificationCompat.Builder(this, "CHANNEL_ID")
                    .setStyle(new NotificationCompat.MessagingStyle("Me")
                            .setConversationTitle("Team lunch")
                            .addMessage("Hi", System.currentTimeMillis(), "adg") // Pass in null for user.
                            .addMessage("What's up?", System.currentTimeMillis()+ 2000, "Coworker")
                            .addMessage("Not much", System.currentTimeMillis()+ 3000, "gda")
                            .addMessage("How about lunch?", System.currentTimeMillis() + 5000, "Coworker")
                    )
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setAutoCancel(true)
                    .addAction(action)
                    .addAction(R.mipmap.ic_launcher, "More", pIntent)
                    .addAction(R.mipmap.ic_launcher, "And more", pIntent)
                    .build();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123");
        } else {
            n = new Notification.Builder(this)
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pIntent)
                    .setStyle(new Notification.BigTextStyle().bigText("longText"))
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher, "Call", pIntent)
                    .addAction(R.mipmap.ic_launcher, "More", pIntent)
                    .addAction(R.mipmap.ic_launcher, "And more", pIntent).build();
        }
        return n;
    }
}
