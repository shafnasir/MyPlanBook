package utm.csc301.theBrogrammers.myPlanBook.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import utm.csc301.theBrogrammers.myPlanBook.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarEventsActivity extends AppCompatActivity {

    CompactCalendarView compactCalendarView = null;
    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
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
        myEvent = (TextView) findViewById(R.id.myEvent);
        addEvent = (Button) findViewById(R.id.addEvent);

        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        myCalendarMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        dateClickedOn = new Date();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                String displayEvent = "";
                for(int i = 0; i < events.size(); i++){
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
                    displayEvent += "Event " + (i + 1) + ": " + events.get(i).getData().toString() + ", Time: " + time + am_pm + "\n";
                }
                myEvent.setText(displayEvent);
               // System.out.println("Day was clicked: " + dateClicked + " with events " + events);
                dateClickedOn = dateClicked;
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                myCalendarMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        Event ev1 = new Event(Color.GREEN, 1581829200000L, "CSC301 Midterm");
        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(new Event(Color.GREEN, 1581829200000L, "Birthday"));

        //System.out.println(compactCalendarView.getEvents(1581829200000L).get(0).getData());

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEvent();
            }
        });

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
            }
        }
    }

}
