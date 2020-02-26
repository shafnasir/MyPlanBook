package utm.csc301.theBrogrammers.myPlanBook.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class CalendarEventsModel extends AppCompatActivity {

    private EditText eventDetails;
    private TextView currentDate, cancel, save;
    Date dateClickedOn;

    ColorSeekBar colorSeekBar;
    TimePicker timePicker;
    String result;
    int color;


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
                finish();
            }
        });

    }
}
