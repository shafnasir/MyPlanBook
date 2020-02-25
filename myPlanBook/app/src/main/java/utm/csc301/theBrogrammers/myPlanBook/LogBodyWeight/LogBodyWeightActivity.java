package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout calendarLayout;
    private TextView myCalendarDate;
    private TextView bodyWeightTextView;
    private BodyWeightsModel bodyWeightsModel;
    private Button unitsToggleButton;
    private LineChart bodyWeightsMonthlyGraph;

    private final String[] dayStrings = {"1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

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

        // Test out Graph
        bodyWeightsMonthlyGraph = (LineChart) findViewById(R.id.bodyWeightsGraph);
        this.styleGraph();
        this.setBodyWeightData(String.valueOf(currentMonth));

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

    public void styleGraph() {
        // Set x,y axis dimensions
        XAxis xAxis = bodyWeightsMonthlyGraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayStrings));
        //xAxis.setAxisMaximum(11f);
        //xAxis.setAxisMinimum(0f);

        YAxis leftAxis = bodyWeightsMonthlyGraph.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.removeAllLimitLines();
        //leftAxis.setAxisMaximum(100f);
        //leftAxis.setAxisMinimum(0f);

        bodyWeightsMonthlyGraph.getDescription().setEnabled(false);
        bodyWeightsMonthlyGraph.getXAxis().setDrawGridLines(false);
        bodyWeightsMonthlyGraph.getAxisRight().setEnabled(true);
        bodyWeightsMonthlyGraph.getAxisRight().setDrawAxisLine(false);
        bodyWeightsMonthlyGraph.getAxisRight().setDrawGridLines(false);
    }

    private void setBodyWeightData (String month) {
        // Generate new random data to demo
        ArrayList<Entry> entries = genData(month);
        LineDataSet dSet = new LineDataSet(entries, "Body Weights");
        //setDataSetStyling(dSet, false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSet);
        bodyWeightsMonthlyGraph.setData(new LineData(dataSets));
    }

    private ArrayList<Entry> genData(String month){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry (0,0));
        entries.add(new Entry (1,1));
        entries.add(new Entry (2,2));
        entries.add(new Entry (3,3));
        entries.add(new Entry (4,4));
        entries.add(new Entry (5,5));
        entries.add(new Entry (6,6));
        entries.add(new Entry (7,7));
        entries.add(new Entry (8,8));
        entries.add(new Entry (9,9));
        entries.add(new Entry (10,10));
        return entries;
    }
}
