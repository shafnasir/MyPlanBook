package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    CalendarView calendarView;
    LinearLayout calendarLayout;
    TextView myCalendarDate;
    BodyWeightsModel bodyWeightsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_weight);

        // Set Up Calendar
        calendarView = new CalendarView(this);
        calendarView.setBackgroundResource(R.drawable.round_outline_bordered);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(45,45,45,0);
        calendarView.setLayoutParams(params);
        calendarLayout = (LinearLayout) findViewById(R.id.layoutBodyWeights);
        calendarLayout.addView(calendarView, 0);
        myCalendarDate = (TextView)findViewById(R.id.bodyWeightDate);

        // Set current Date
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        int currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        myCalendarDate.setText(currentDate);

        bodyWeightsModel = new BodyWeightsModel();
        String bodyWeight = bodyWeightsModel.getWeight(currentDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                myCalendarDate.setText(date);
                bodyWeightsModel.getWeight(date);
            }
        });

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
        weightsScrollLayout.setBackgroundColor(Color.TRANSPARENT);

        Button enterWeightButton = (Button) findViewById(R.id.enterWeightButton);
        enterWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText inputWeightEditText = (EditText) findViewById(R.id.inputWeightEditText);
                ToggleButton toggleUnitsButton = (ToggleButton) findViewById(R.id.toggleUnitsButton);

                double weight = Double.parseDouble(inputWeightEditText.getText().toString());
                String units = toggleUnitsButton.getText().toString();

                if (weight > 10000) {
                    return;
                }

                if (units.equals("LBS")) {
                    TextView newBodyWeight = new TextView(LogBodyWeightActivity.this);
                    String weightInLbsKg = String.format("%.1f", weight)+ " lbs: " + String.format("%.1f", weight*(1/2.20462)) + " kg";
                    newBodyWeight.setText(weightInLbsKg);
                    newBodyWeight.setTextColor(Color.parseColor("#000000"));
                    newBodyWeight.setTextSize(30);
                    newBodyWeight.setGravity(Gravity.CENTER);
                    newBodyWeight.setBackgroundColor(Color.parseColor("#B4FC5B"));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);

                    newBodyWeight.setLayoutParams(params);

                    LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
                    weightsScrollLayout.addView(newBodyWeight);
                }
                else {
                    TextView newBodyWeight = new TextView(LogBodyWeightActivity.this);
                    String weightInLbsKg = String.format("%.1f", weight*(2.20462))+ " lbs: " + String.format("%.1f", weight) + " kg";
                    newBodyWeight.setText(weightInLbsKg);
                    newBodyWeight.setTextColor(Color.parseColor("#000000"));
                    newBodyWeight.setTextSize(30);
                    newBodyWeight.setGravity(Gravity.CENTER);
                    newBodyWeight.setBackgroundColor(Color.parseColor("#B4FC5B"));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);

                    newBodyWeight.setLayoutParams(params);

                    LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
                    weightsScrollLayout.addView(newBodyWeight);
                }
            }
        });*/
    }

}
