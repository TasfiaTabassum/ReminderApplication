package com.example.reminderapplication;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters ;

import java.util.ConcurrentModificationException;
import  java.util.concurrent.TimeUnit ;


public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    private void showNotification(){
        String description = "";
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
        notificationManagerCompat.notify(198, builder.build());
    }



    @NonNull
    @Override
    public Result doWork() {
        showNotification();
        return null;
    }


    public static void setNotification(Context context){
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 1, TimeUnit.MINUTES)
                .setInitialDelay(1, TimeUnit.MINUTES) .build();
        WorkManager.getInstance(context).enqueue(periodicWorkRequest);
    }

}