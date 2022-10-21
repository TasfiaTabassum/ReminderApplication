package com.example.reminderapplication;

import static com.example.reminderapplication.MyWorker.setNotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btn_start);
        createNotificationChannel();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNotification(view.getContext());
            }
        });

    }

    private void createNotificationChannel() {
      //  String id = "Floating notification is working" ;

             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //NotificationChannel channel = notificationManager.getNotificationChannel(id);
            notificationManager.createNotificationChannel(new NotificationChannel("notif", "Alerts", NotificationManager.IMPORTANCE_HIGH));
        }
        else{
          NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify();
        }
    }


}