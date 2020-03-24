package utm.csc301.theBrogrammers.myPlanBook.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

import utm.csc301.theBrogrammers.myPlanBook.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsModel.putEvent;
import static utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsModel.getEvents;
import static utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsModel.deleteEvent;

public class CalendarEventsActivity extends AppCompatActivity {

    boolean checkCancel = false;
    CompactCalendarView compactCalendarView = null;
    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    TableLayout displayEvents;
    CalendarView calendarView;
    TextView myCalendarMonth;
    TextView myEvent;
    Button addEvent;

    Date dateClickedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        myCalendarMonth = (TextView) findViewById(R.id.myCalendarMonth);
        addEvent = (Button) findViewById(R.id.addEvent);
        displayEvents = (TableLayout) findViewById(R.id.displayEvents);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        myCalendarMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        dateClickedOn = new Date();

        getEvents(new CalendarEventsModel.MyCallback() {
            @Override
            public void onCallback(List<Event> eventList) {
                compactCalendarView.addEvents(eventList);
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                displayEvents.removeAllViews();
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                displayEvents(events);
               // System.out.println("Day was clicked: " + dateClicked + " with events " + events);
                dateClickedOn = dateClicked;
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                myCalendarMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEvent();
            }
        });

    }

    public void displayEvents(List<Event> events){

        String displayEvent = "";

        for(int i = 0; i < events.size(); i++){
            TextView rowTextView = new TextView(this);
            Button deleteEvent = new Button(this);
            Button updateEvent = new Button(this);
            deleteEvent.setBackgroundResource(R.drawable.garbage_can);
            deleteEvent.setLayoutParams(new TableRow.LayoutParams(100, 100));
            updateEvent.setBackgroundResource(R.drawable.edit_pencil);
            updateEvent.setLayoutParams(new TableRow.LayoutParams(100, 100));
            Event event = events.get(i);

            updateEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAddEvent();
                    if(checkCancel){
                        deleteEvent(event);
                        compactCalendarView.removeEvent(event);
                    }
                }
            });

            deleteEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEvent(event);
                    compactCalendarView.removeEvent(event);

                }
            });

            TableRow displayEventLayout = new TableRow(this);
            displayEventLayout.setOrientation(TableRow.HORIZONTAL);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(events.get(i).getTimeInMillis());

            String am_pm;
            long hour = calendar.get(Calendar.HOUR);
            long minutes = calendar.get(Calendar.MINUTE);
            if(calendar.get(Calendar.AM_PM) == Calendar.AM){
                am_pm = "AM";
            }else{
                am_pm = "PM";
            }
            String time = String.format("%2d:%02d ", hour, minutes);
            displayEvent = events.get(i).getData().toString() + ", Time: " + time + am_pm;

            rowTextView.setText(displayEvent);
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.leftMargin = 20;
            rowTextView.setLayoutParams(params);
            rowTextView.setTextColor(Color.BLACK);
            rowTextView.setGravity(Gravity.CENTER);
            rowTextView.setTextSize(18);

            displayEventLayout.addView(rowTextView);
            displayEventLayout.addView(deleteEvent);
            displayEventLayout.addView(updateEvent);

            displayEvents.addView(displayEventLayout);
        }

    }

    public void openAddEvent(){
        Intent intent = new Intent(this, CalendarEventsModel.class);
        intent.putExtra("Date", dateClickedOn.getTime());
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Event new_event = new Event(
                        data.getIntExtra("eventColor", 0),
                        data.getLongExtra("dateMilliseconds", 0),
                        data.getStringExtra("eventDetails"));

                compactCalendarView.addEvent(new_event);
                putEvent(new_event);
                checkCancel = true;
            }
            else{
                checkCancel = false;
            }

        }
    }

}
