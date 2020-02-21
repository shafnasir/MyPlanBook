package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
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
    TextView bodyWeightTextView;
    BodyWeightsModel bodyWeightsModel;
    Button unitsToggleButton;

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

        EditText bodyWeightsInputTextField = findViewById(R.id.inputWeightEditText);
        bodyWeightsInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        bodyWeightsModel = new BodyWeightsModel();
        String bodyWeight = bodyWeightsModel.getWeight(currentDate);

        bodyWeightTextView = (TextView)findViewById(R.id.bodyWeightTextView);

        if (bodyWeight == null) {
            bodyWeightTextView.setVisibility(View.INVISIBLE);
        }
        else {
            bodyWeightTextView.setText(bodyWeight);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                myCalendarDate.setText(date);
                String weight = bodyWeightsModel.getWeight(date);
                if (weight == null) {
                    bodyWeightTextView.setVisibility(View.INVISIBLE);
                }
                else {
                    bodyWeightTextView.setText(weight);
                    bodyWeightTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        Button enterWeightButton = (Button) findViewById(R.id.bodyWeightsEnterButton);
        enterWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText inputWeightEditText = (EditText) findViewById(R.id.inputWeightEditText);
                Button toggleUnitsButton = (Button) findViewById(R.id.unitsToggleButton);

                if (inputWeightEditText.getText().toString().isEmpty()){
                    return;
                }

                double weight = Double.parseDouble(inputWeightEditText.getText().toString());
                String units = toggleUnitsButton.getText().toString();

                String date = myCalendarDate.getText().toString();

                if (weight > 10000 || bodyWeightsModel.getWeight(date) != null) {
                    return;
                }

                if (units.equals("LBS")) {
                    String weightInLbsKg = String.format("%.1f", weight)+ " LBS: " + String.format("%.1f", weight*(1/2.20462)) + " KG";
                    bodyWeightsModel.addWeight(date, weightInLbsKg);
                    bodyWeightTextView.setText(weightInLbsKg);
                    bodyWeightTextView.setVisibility(View.VISIBLE);
                }
                else {
                    String weightInLbsKg = String.format("%.1f", weight*(2.20462))+ " LBS: " + String.format("%.1f", weight) + " KG";
                    bodyWeightsModel.addWeight(date, weightInLbsKg);
                    bodyWeightTextView.setText(weightInLbsKg);
                    bodyWeightTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        unitsToggleButton = (Button) findViewById(R.id.unitsToggleButton);
        unitsToggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String units = unitsToggleButton.getText().toString();

                if (units.equals("LBS")) {
                    unitsToggleButton.setText("KG");
                }
                else {
                    unitsToggleButton.setText("LBS");
                }
            }
        });
    }

}
