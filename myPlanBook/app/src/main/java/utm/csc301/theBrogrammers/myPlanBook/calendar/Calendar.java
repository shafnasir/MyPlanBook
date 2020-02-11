package utm.csc301.theBrogrammers.myPlanBook.calendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class Calendar extends AppCompatActivity {

    CalendarView calendarView;
    TextView myCalendarDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        myCalendarDate = findViewById(R.id.myCalendarDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + dayOfMonth + "/" + year;
                myCalendarDate.setText(date);
            }
        });



    }
}
