package com.dmuench.healthtracker;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Notifications extends BroadcastReceiver {

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

    // Resourced from: gist.github.com/BrandonSmith/6679223 and developer.android.com/training/notify-user/build-notification
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        android.app.Notification notification = intent.getParcelableExtra("notification");
        int id = intent.getIntExtra("notification_id",0);
        notificationManager.notify(id, notification);
    }
}
