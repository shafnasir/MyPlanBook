package utm.csc301.theBrogrammers.myPlanBook.Notification;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannels extends Application {
    public static final String CHANNEL_1_ID = "Important Notification";
//    public static final String CHANNEL_2_ID = "Minor Notification0";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            android.app.NotificationChannel channel1 = new android.app.NotificationChannel(
                    CHANNEL_1_ID,
                    "Event Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is the event notifications");

//            android.app.NotificationChannel channel2 = new android.app.NotificationChannel(
//                    CHANNEL_2_ID,
//                    "Minor Notification",
//                    NotificationManager.IMPORTANCE_LOW
//            );
//            channel2.setDescription("This is the minor notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
//            manager.createNotificationChannel(channel2);

        }
    }
}
