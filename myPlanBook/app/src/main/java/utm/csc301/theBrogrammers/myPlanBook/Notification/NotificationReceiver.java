package utm.csc301.theBrogrammers.myPlanBook.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import utm.csc301.theBrogrammers.myPlanBook.Notification.NotificationChannels;
import utm.csc301.theBrogrammers.myPlanBook.R;
import utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsActivity;
import utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsModel;

public class NotificationReceiver extends BroadcastReceiver {

    private NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        String event = intent.getStringExtra("event");
        long time = intent.getLongExtra("time",0);
        int notId = intent.getIntExtra("notificationId", 0);
        Intent activityIntent = new Intent(context, CalendarEventsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                activityIntent, 0);

        notificationManager = NotificationManagerCompat.from(context);

//        System.out.println(notId);

        Notification notification = new NotificationCompat.Builder(context, NotificationChannels.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(event)
                .setWhen(time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .setGroup("Event")
                .build();

        notificationManager.notify(notId, notification);
    }

}


