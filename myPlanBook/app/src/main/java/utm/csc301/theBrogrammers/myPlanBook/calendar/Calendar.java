package utm.csc301.theBrogrammers.myPlanBook.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;
    TextView myCalendarDate;
    Button addEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myCalendarDate = (TextView) findViewById(R.id.myCalendarDate);
        addEvent = (Button) findViewById(R.id.addEvent);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                myCalendarDate.setText(date);
            }
        });

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
