package utm.csc301.theBrogrammers.myPlanBook.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import utm.csc301.theBrogrammers.myPlanBook.Notification.NotificationReceiver;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class CalendarEventsModel extends AppCompatActivity {

    private EditText eventDetails;
    private TextView currentDate, cancel, save;
    Date dateClickedOn;

    ColorSeekBar colorSeekBar;
    TimePicker timePicker;
    String result;
    int color;

    private int notificationId = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventDetails = findViewById(R.id.eventDetails);
        currentDate = findViewById(R.id.currentDate);
        cancel = findViewById(R.id.tv_cancel);
        save = findViewById(R.id.tv_save);
        colorSeekBar = findViewById(R.id.colorSeekBar);
        timePicker = findViewById(R.id.timePicker);

        dateClickedOn = new Date();
        dateClickedOn.setTime(getIntent().getLongExtra("Date", -1));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        currentDate.setText(sdf.format(dateClickedOn));




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        colorSeekBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i) {
                color = i;
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                dateClickedOn.setHours(hourOfDay);
                dateClickedOn.setMinutes(minute);
                dateClickedOn.setSeconds(0);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                result = eventDetails.getText().toString();
                intent.putExtra("dateMilliseconds", dateClickedOn.getTime());
                intent.putExtra("eventDetails",result);
                intent.putExtra("eventColor", color);
                setResult(RESULT_OK, intent);

                //Set notificationId & text
                Intent activityIntent = new Intent(CalendarEventsModel.this, NotificationReceiver.class);
                activityIntent.putExtra("notificationId", notificationId);
                activityIntent.putExtra("event", result);
                activityIntent.putExtra("time", dateClickedOn.getTime());
//
//                //getBroadcast
                PendingIntent pendingIntent = PendingIntent.getBroadcast(CalendarEventsModel.this, 0,
                        activityIntent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//                //set alarm
                long alarmStart = dateClickedOn.getTime();
                alarm.set(AlarmManager.RTC_WAKEUP, alarmStart,pendingIntent);

                finish();
            }
        });
    }

    public static void deleteEvent(Event event){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collection = db.collection("Calendar").document(userName)
                .collection("events");

        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.get("details").equals(event.getData()) && (long) document.get("timeinmillis") == event.getTimeInMillis()){
                            collection.document(document.getId()).delete();
                        }
                        Log.i("Delete event"+ event.getTimeInMillis(), document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("ProcessMonth", "Error deleting documents.", task.getException());
                }
            }
        });

    }

    public static void putEvent(Event event){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        HashMap<String, Object> hm = new HashMap<>();

        hm.put("details", event.getData().toString());
        hm.put("timeinmillis", event.getTimeInMillis());
        hm.put("color", String.valueOf(event.getColor()));

        String uniqueID = UUID.randomUUID().toString();
        db.collection("Calendar").document(userName)
                .collection("events").document(uniqueID).set(hm);

    }

    public interface MyCallback {
        void onCallback(List<Event> events);
    }

    public static void getEvents(MyCallback myCallback){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("Calendar").document(userName).collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    List<Event> events = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int color = Integer.parseInt(document.get("color").toString());
                        long timeinmillis = (long) document.get("timeinmillis");
                        Event new_event = new Event(
                                color,
                                timeinmillis,
                                document.get("details"));
                        events.add(new_event);
                    }

                    myCallback.onCallback(events);

                } else {
                    System.out.println("Error getting documents");
                }

            }
        });
    }

}
