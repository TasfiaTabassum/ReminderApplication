package com.example.reminderapplication;

import android.accessibilityservice.AccessibilityService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

   // private static final int NOTIFICATION_ID = 100 ;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public void showNotification(){
        String description = "Hello";
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);


        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 , intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "Orders")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My Worker")
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1, builder.build());

    }



    @NonNull
    @Override
    public Result doWork() {

        try{
            showNotification();
            return Result.success();
        }
        catch(Exception e){
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
