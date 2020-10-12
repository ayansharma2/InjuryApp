package com.example.injury_app;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.Toast;

public class Notification_Channel extends Application {
    public static final String CHANNEL_ID = "Safety Channel";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

    }

    private void createNotificationChannel() {

    }
}
