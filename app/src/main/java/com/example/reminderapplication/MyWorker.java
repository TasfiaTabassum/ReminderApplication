package com.example.reminderapplication;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters ;

import java.sql.SQLOutput;
import java.util.ConcurrentModificationException;

import  java.util.concurrent.TimeUnit ;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public void showNotification(){
        String description = "Hello";
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Orders");
               // .setSmallIcon(R.drawable.ic_launcher_background)
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle("My Worker");
        builder.setContentText(description);
        builder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description));
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, builder.build());

    }


    public void showNotification1(){
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("There is a text");
        bigText.setBigContentTitle("Today's Verse");
        bigText.setSummaryText("Text in detail");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }



    @NonNull
    @Override
    public Result doWork() {

        try{
            showNotification1();
            Log.d("tasfia", "error neiii" );
            return Result.success();
        }
        catch(Exception e){
            Log.d("tasfia", "error ashtese ");
            System.out.println(e.getMessage());
            return  Result.failure();
        }

        //return null;
    }


    public static void setNotification(Context context){
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 1000, TimeUnit.MILLISECONDS)
                .setInitialDelay(1000, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }

}
