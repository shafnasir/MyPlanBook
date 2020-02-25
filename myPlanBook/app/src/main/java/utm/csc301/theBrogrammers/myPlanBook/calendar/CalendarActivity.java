package utm.csc301.theBrogrammers.myPlanBook.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import utm.csc301.theBrogrammers.myPlanBook.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    CalendarView calendarView;
    TextView myCalendarMonth;
    TextView myEvent;
    Button addEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        myCalendarMonth = (TextView) findViewById(R.id.myCalendarMonth);
        myEvent = (TextView) findViewById(R.id.myEvent);
        addEvent = (Button) findViewById(R.id.addEvent);

        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);

        myCalendarMonth.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                String displayEvent = "";
                for(int i = 0; i < events.size(); i++){
                    displayEvent += "Event " + (i + 1) + ": " + events.get(i).getData().toString() + "\n";
                }
                myEvent.setText(displayEvent);
               // System.out.println("Day was clicked: " + dateClicked + " with events " + events);
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
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent); 
    }
}
