package com.pranavprksn.isa_vitapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pranavprksn.isa_vitapp.activity.SplashScreen;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static int NOTIFICATION_ID = 1;

    @Override
    public void onNewToken(@NonNull @NotNull String s) {
        super.onNewToken(s);

        Log.d("TokenTAG", s);
    }

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        createNotificationChannel();

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            Log.d("NotificationTAG", body + " | " + title);
            generateNotification(body, title);
        }
    }

    private void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(
                "channel_id",
                "Test notification channel",
                NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    private void generateNotification(String body, String title) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, SplashScreen.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);


        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());

    }
}