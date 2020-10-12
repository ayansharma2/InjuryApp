package com.example.injury_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.injury_app.Notification_Channel.CHANNEL_ID;

public class Background_Service extends Service implements SensorEventListener {
    long lastTime=0;
    boolean first=true;
    NotificationManager notificationManager;;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Ensuring your Safety",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
        Intent notificationIntent=new Intent(this,Service_Running.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Safety App")
                .setContentText("Ensuring Your Safety")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_baseline_accessibility_24)
                .build();
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void Notification()
    {
        Intent intent=new Intent(this, injured_activity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,115,intent,0);
        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String Channel_Id="Test Channel";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(Channel_Id,"Test",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,Channel_Id)
                .setSmallIcon(R.drawable.ic_baseline_accessibility_24)
                .setAutoCancel(false)
                .setContentTitle("Detected A fall")
                .setContentText("Please Verify you are Ok :)")
                .setFullScreenIntent(pendingIntent,true);
        notificationManager.notify(0,builder.build());

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long curTime = System.currentTimeMillis();
            if(z<=0)
            {
                Notification();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

