package utm.csc301.theBrogrammers.myPlanBook.Notification;

//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;
//
//import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import android.provider.Settings;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import utm.csc301.theBrogrammers.myPlanBook.R;

public class Notification_Demo extends AppCompatActivity {
//    private Button send;
//    private NotificationManagerCompat notificationManager;
//    private EditText TextTitle, TextMessage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_notification_demo);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        notificationManager = NotificationManagerCompat.from(this);
//
//        TextTitle = findViewById(R.id.notification_title);
//        TextMessage = findViewById(R.id.notification_message);
//
//        send = findViewById(R.id.send_notification);
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendOnChannel1();
//            }
//        });
//
//    }
//    public void sendOnChannel1(){
//        if (!notificationManager.areNotificationsEnabled()){
//            openNotificationSettings();
//            return;
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
//        isChannelBlocked(NotificationChannels.CHANNEL_1_ID)){
//            openChannelSettings(NotificationChannels.CHANNEL_1_ID);
//            return;
//        }
//
//        String title = TextTitle.getText().toString();
//        String message = TextMessage.getText().toString();
//
//        Intent activityIntent = new Intent(this, Notification_Demo.class);
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, activityIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this, NotificationChannels.CHANNEL_1_ID)
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_REMINDER)
//                .setContentIntent(contentIntent)
//                .build();
//
//
//        notificationManager.notify(1, notification);
//    }
//
//    private void openNotificationSettings() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//            startActivity(intent);
//        }else{
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//        }
//    }
//    @RequiresApi(26)
//    private boolean isChannelBlocked(String channelId){
//        NotificationManager manager = getSystemService(NotificationManager.class);
//        NotificationChannel channel = manager.getNotificationChannel(channelId);
//
//        return channel != null &&
//                channel.getImportance() == NotificationManager.IMPORTANCE_NONE;
//    }
//
//    @RequiresApi(26)
//    private void openChannelSettings (String channelId){
//        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
//        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
//        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
//        startActivity(intent);
//    }
}
