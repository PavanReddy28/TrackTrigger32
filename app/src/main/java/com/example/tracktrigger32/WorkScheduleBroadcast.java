package com.example.tracktrigger32;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class WorkScheduleBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String notifContent = intent.getStringExtra("message");
        String notifTitle = intent.getStringExtra("title");

        //if we want ring on notifcation then uncomment below line//
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent1 = new Intent(context, WorkScheduleFragment.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi= PendingIntent.getActivity(context,300,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notify2User").
                setContentIntent(pi).
                setSmallIcon(R.mipmap.ic_launcher_foreground).
                setContentText(notifContent).
                setContentTitle("REMINDER!! " + notifTitle)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context  );


        notificationManagerCompat.notify(300,builder.build());
    }
}
