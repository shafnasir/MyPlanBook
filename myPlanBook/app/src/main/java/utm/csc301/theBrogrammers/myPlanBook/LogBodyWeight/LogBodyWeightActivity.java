package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private LinearLayout calendarLayout;
    private TextView myCalendarDate;
    private TextView bodyWeightTextView;
    private TextView bodyWeightsGraphMonth;
    private EditText bodyWeightsInputTextField;
    private BodyWeightsModel bodyWeightsModel;
    private Button unitsToggleButton;
    private Button enterWeightButton;
    private LineChart bodyWeightsMonthlyGraph;
    private int currentMonth;
    private FloatingActionButton editBodyWeightButton;
    private boolean editMode = false;
    private FloatingActionButton bodyWeightDeleteButton;
    private CheckBox bodyWeightCheckBox;
    private LinearLayout.LayoutParams params1;
    private LinearLayout.LayoutParams params2;
    private HashMap<String,String> monthNames;
    private String currentDate;
    private final String[] dayStrings = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_weight);
        this.assignParams();
        this.assignCalendarLayout();
        this.createCalendarView();
        this.setCurrentDate();
        this.setBodyWeightInputTextField();
        bodyWeightsModel = new BodyWeightsModel();
        this.setBodyWeightTextView();
        this.setMonthNames();
        bodyWeightsGraphMonth = (TextView) findViewById(R.id.bodyWeightsGraphMonth);
        this.setMonth(String.valueOf(currentMonth));
        bodyWeightsMonthlyGraph = (LineChart) findViewById(R.id.bodyWeightsGraph);
        this.styleGraph();
        this.setBodyWeightGraphData();
        this.setCalendarViewListener();
        this.setEnterWeightButtonListener();
        this.setToggleUnitsButtonListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
        this.setBodyWeightCheckBox();
    }

    private void assignParams(){
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2 = new LinearLayout.LayoutParams(
                875,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(45,45,45,0);
        params2.setMargins(45,45,45,0);
    }

    private void createCalendarView(){
        calendarView = new CalendarView(this);
        calendarView.setBackgroundResource(R.drawable.round_outline_bordered);
        calendarView.setLayoutParams(params1);
        calendarLayout.addView(calendarView, 0);
    }

    private void assignCalendarLayout(){
        calendarLayout = (LinearLayout) findViewById(R.id.layoutBodyWeights);
    }

    private void setCurrentDate(){
        myCalendarDate = (TextView)findViewById(R.id.bodyWeightDate);
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        myCalendarDate.setText(currentDate);
    }

    private void setBodyWeightInputTextField(){
        bodyWeightsInputTextField = findViewById(R.id.inputWeightEditText);
        bodyWeightsInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void setBodyWeightTextView(){
        String bodyWeight = bodyWeightsModel.getWeight(currentDate);
        bodyWeightTextView = (TextView)findViewById(R.id.bodyWeightTextView);
        bodyWeight = (bodyWeight == null)? "": bodyWeight;
        bodyWeightTextView.setText(bodyWeight);
    }

    private void setMonthNames() {
        monthNames = new HashMap<String,String>();
        monthNames.put("1", "JANUARY");
        monthNames.put("2", "FEBRUARY");
        monthNames.put("3", "MARCH");
        monthNames.put("4", "APRIL");
        monthNames.put("5", "MAY");
        monthNames.put("6", "JUNE");
        monthNames.put("7", "JULY");
        monthNames.put("8", "AUGUST");
        monthNames.put("9", "SEPTEMBER");
        monthNames.put("10", "OCTOBER");
        monthNames.put("11", "NOVEMBER");
        monthNames.put("12", "DECEMBER");
    }

    private void setMonth(String month){
        bodyWeightsGraphMonth.setText(monthNames.get(month));
    }

    private void styleGraph() {
        XAxis xAxis = bodyWeightsMonthlyGraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayStrings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = bodyWeightsMonthlyGraph.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        bodyWeightsMonthlyGraph.getDescription().setEnabled(false);
        bodyWeightsMonthlyGraph.getAxisRight().setEnabled(false);
    }

    private void setBodyWeightGraphData() {
        ArrayList<Entry>[] dataEntries = genGraphData(myCalendarDate.getText().toString());
        LineDataSet dSetLbs = new LineDataSet(dataEntries[0], "BODY WEIGHTS IN LBS");
        LineDataSet dSetKg = new LineDataSet(dataEntries[1], "BODY WEIGHTS IN KG");

        setDataSetStyling(dSetLbs, "lbs");
        setDataSetStyling(dSetKg, "kg");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSetLbs);
        dataSets.add(dSetKg);
        bodyWeightsMonthlyGraph.setData(new LineData(dataSets));
    }

    private ArrayList<Entry>[] genGraphData (String calendarDate){
        HashMap<String, String> bodyWeightsForMonth = bodyWeightsModel.getBodyWeightsForMonth(calendarDate);
        String[] splitDate = calendarDate.split("/");
        return getDataSets(splitDate[1], splitDate[2], bodyWeightsForMonth);
    }

    private ArrayList<Entry>[] getDataSets(String month, String year, HashMap<String, String> bodyWeightsForMonth){
        ArrayList<Entry>[] dataSets = new ArrayList[2];
        ArrayList<Entry> entriesLbs = new ArrayList<Entry>();
        ArrayList<Entry> entriesKg = new ArrayList<Entry>();

        for (int i = 1; i < 32; i++) {
            String testDate = String.valueOf(i) + "/" + month + "/" + year;
            if (bodyWeightsForMonth.containsKey(testDate)){
                String[] splitWeights = bodyWeightsForMonth.get(testDate).split(" ");
                entriesLbs.add(new Entry (i, Float.valueOf(splitWeights[0])));
                entriesKg.add(new Entry (i, Float.valueOf(splitWeights[2])));
            }
        }

        dataSets[0] = entriesLbs;
        dataSets[1] = entriesKg;
        return dataSets;
    }

    private void setDataSetStyling(LineDataSet dSet, String units){
        int colour = (units.equals("lbs"))? Color.BLUE: Color.RED;
        float lineWidth = 3f;
        dSet.setDrawCircleHole(false);
        dSet.setDrawValues(false);
        dSet.setLineWidth(lineWidth);
        dSet.setCircleRadius(lineWidth);
        dSet.setColor(colour);
        dSet.setCircleColor(colour);
    }

    private void setCalendarViewListener(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) {
                    setEditModeFeatures();
                }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                myCalendarDate.setText(date);
                if (currentMonth != month+1){
                    currentMonth = month + 1;
                    setMonth(String.valueOf(currentMonth));
                    setBodyWeightGraphData();
                }
                String weight = bodyWeightsModel.getWeight(date);
                weight = (weight == null)? "": weight;
                bodyWeightTextView.setText(weight);
            }
        });
    }

    private void setEditModeFeatures(){
        if (editMode == true){
            editBodyWeightButton.setImageResource(R.drawable.edit_pencil);
            editMode = false;
            bodyWeightDeleteButton.hide();
            bodyWeightCheckBox.setVisibility(View.GONE);
            bodyWeightTextView.setLayoutParams(params1);
        }
        else {
            editBodyWeightButton.setImageResource(R.drawable.ex_cancel);
            editMode = true;
            bodyWeightDeleteButton.show();
            bodyWeightCheckBox.setVisibility(View.VISIBLE);
            bodyWeightTextView.setLayoutParams(params2);
        }
    }

    private void setEnterWeightButtonListener(){
        enterWeightButton = (Button) findViewById(R.id.bodyWeightsEnterButton);
        enterWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                double weight = Double.parseDouble(bodyWeightsInputTextField.getText().toString());
                String date = myCalendarDate.getText().toString();
                if (bodyWeightsInputTextField.getText().toString().isEmpty() || weight > 10000
                        || bodyWeightsModel.getWeight(date) != null){
                    return;
                }
                String units = unitsToggleButton.getText().toString();
                String weightInLbsKg = (units.equals("LBS"))? String.format("%.1f", weight)+ " LBS: "
                        + String.format("%.1f", weight*(1/2.20462)) + " KG": String.format("%.1f", weight*(2.20462)) +
                        " LBS: " + String.format("%.1f", weight) + " KG";
                bodyWeightsModel.addWeight(date, weightInLbsKg);
                bodyWeightTextView.setText(weightInLbsKg);
                setMonth(String.valueOf(currentMonth));
                setBodyWeightGraphData();
            }
        });
    }

    private void setToggleUnitsButtonListener(){
        unitsToggleButton = (Button) findViewById(R.id.unitsToggleButton);
        unitsToggleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String units = unitsToggleButton.getText().toString();
                units = units.equals("LBS")? "KG": "LBS";
                unitsToggleButton.setText(units);
            }
        });
    }

    private void setEditFloatingButtonListener(){
        editBodyWeightButton = (FloatingActionButton) findViewById(R.id.editBodyWeightButton);
        editBodyWeightButton.setImageResource(R.drawable.edit_pencil);
        editBodyWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bodyWeightTextView.getText().toString().isEmpty()){
                    setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        bodyWeightDeleteButton = (FloatingActionButton) findViewById(R.id.bodyWeightDeleteButton);
        bodyWeightDeleteButton.setImageResource(R.drawable.garbage_can);
        bodyWeightDeleteButton.hide();
        bodyWeightDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode == true && !bodyWeightTextView.getText().toString().isEmpty() && bodyWeightCheckBox.isChecked()) {
                    bodyWeightTextView.setText("");
                    setEditModeFeatures();
                    bodyWeightsModel.removeWeight(myCalendarDate.getText().toString());
                    setBodyWeightGraphData();
                }
            }
        });
    }

    private void setBodyWeightCheckBox(){
        bodyWeightCheckBox = (CheckBox) findViewById(R.id.bodyWeightCheckBox);
        bodyWeightCheckBox.setVisibility(View.GONE);
    }
}
