package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
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
import androidx.core.view.ViewCompat;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    private TextView myCalendarDate;
    private TextView bodyWeightsGraphMonth;
    private EditText bodyWeightsInputTextField;
    private BodyWeightsModel bodyWeightsModel;
    private Button unitsToggleButton;
    private Button enterButton;
    private LineChart bodyWeightsMonthlyGraph;
    private int currentMonth;
    private FloatingActionButton editButton;
    private boolean editMode = false;
    private FloatingActionButton deleteButton;
    private LinearLayout.LayoutParams params1;
    private LinearLayout.LayoutParams params2;
    private LinearLayout.LayoutParams params3;
    private HashMap<String,String> monthNames;
    private String currentDate;
    private final String[] dayStrings = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private LinearLayout scrollViewLayoutLogBodyWeight;
    private LinearLayout bodyWeightEntryLayout;
    private TextView bodyWeightEntryTV;
    private CheckBox bodyWeightEntryCheckBox;
    private int MARGIN_SIZE, EDIT_MODE_TV_WIDTH, PADDING_SIZE;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_weight);
        this.assignParamValues();
        this.assignParams();
        scrollViewLayoutLogBodyWeight = (LinearLayout) findViewById(R.id.scrollview_layout_log_body_weight);
        this.setBodyWeightInputTextField();
        this.setCurrentDate();
        bodyWeightsModel = new BodyWeightsModel();
        this.createBodyWeightEntryLayout();
        this.createBodyWeightEntryCheckBox();
        this.createBodyWeightEntryTextView();
        this.setBodyWeightEntryTextView();
        this.setEnterButtonListener();
        this.setToggleUnitsButtonListener();
        this.setCalendarViewListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
        this.setMonthNames();
        bodyWeightsGraphMonth = (TextView) findViewById(R.id.graph_month_tv_body_weight);
        bodyWeightsMonthlyGraph = (LineChart) findViewById(R.id.body_weight_graph);
        this.setMonth(String.valueOf(currentMonth));
        this.styleGraph();
        this.setBodyWeightGraphData();
    }

    private void assignParamValues(){
        this.PADDING_SIZE = getResources().getDimensionPixelSize(R.dimen.padding_size);
        this.EDIT_MODE_TV_WIDTH = 875;
        this.MARGIN_SIZE = getResources().getDimensionPixelSize(R.dimen.margin_size);
    }

    private void assignParams(){
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(MARGIN_SIZE, 0,MARGIN_SIZE,MARGIN_SIZE);
        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE);
        params3 = new LinearLayout.LayoutParams(
                EDIT_MODE_TV_WIDTH,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params3.setMargins(MARGIN_SIZE,0,MARGIN_SIZE,MARGIN_SIZE);
    }

    private void setCurrentDate(){
        myCalendarDate = (TextView)findViewById(R.id.date_log_body_weight);
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        myCalendarDate.setText(currentDate);
    }

    private void setBodyWeightInputTextField(){
        bodyWeightsInputTextField = findViewById(R.id.edit_text_input_weight);
        bodyWeightsInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void createBodyWeightEntryLayout() {
        this.bodyWeightEntryLayout = new LinearLayout(this);
        this.bodyWeightEntryLayout.setId(ViewCompat.generateViewId());
        this.bodyWeightEntryLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.bodyWeightEntryLayout.setVisibility(View.GONE);
        this.bodyWeightEntryLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        scrollViewLayoutLogBodyWeight.addView(this.bodyWeightEntryLayout, 6);
    }

    private void createBodyWeightEntryCheckBox() {
        this.bodyWeightEntryCheckBox = new CheckBox(this);
        this.bodyWeightEntryCheckBox.setId(ViewCompat.generateViewId());
        this.bodyWeightEntryCheckBox.setVisibility(View.GONE);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxParams.setMargins(MARGIN_SIZE, 0, 0, 0);
        this.bodyWeightEntryCheckBox.setLayoutParams(checkBoxParams);
        this.bodyWeightEntryLayout.addView(this.bodyWeightEntryCheckBox, 0);
    }

    private void createBodyWeightEntryTextView(){
        this.bodyWeightEntryTV = new TextView(this);
        this.bodyWeightEntryTV.setBackgroundResource(R.drawable.box_outline_bordered);
        this.bodyWeightEntryTV.setLayoutParams(params1);
        this.bodyWeightEntryTV.setPadding(PADDING_SIZE,PADDING_SIZE,PADDING_SIZE,PADDING_SIZE);
        this.bodyWeightEntryTV.setTextColor(Color.parseColor("#858585"));
        this.bodyWeightEntryTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        this.bodyWeightEntryTV.setTypeface(Typeface.SANS_SERIF);
        this.bodyWeightEntryTV.setId(ViewCompat.generateViewId());
        this.bodyWeightEntryTV.setVisibility(View.GONE);
        this.bodyWeightEntryLayout.addView(this.bodyWeightEntryTV, 1);
    }

    private void setBodyWeightEntryTextView(){
        String bodyWeightEntryForDate = bodyWeightsModel.getWeight(currentDate);
        bodyWeightEntryForDate = bodyWeightEntryForDate == null? "": bodyWeightEntryForDate;
        this.bodyWeightEntryTV.setText(bodyWeightEntryForDate);
        int visibility = bodyWeightEntryForDate.isEmpty()? View.GONE: View.VISIBLE;
        this.bodyWeightEntryTV.setVisibility(visibility);
        this.bodyWeightEntryLayout.setVisibility(visibility);
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

        bodyWeightsMonthlyGraph.notifyDataSetChanged();
        bodyWeightsMonthlyGraph.invalidate();
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
        calendarView = (CalendarView) findViewById(R.id.calendar_view_log_body_weight);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) {
                    setEditModeFeatures();
                }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                myCalendarDate.setText(date);
                currentDate = date;
                if (currentMonth != month+1){
                    currentMonth = month + 1;
                    setMonth(String.valueOf(currentMonth));
                    setBodyWeightGraphData();
                }
                setBodyWeightEntryTextView();
            }
        });
    }

    private void setEditModeFeatures(){
        if (editMode == true){
            editButton.setImageResource(R.drawable.edit_pencil);
            editMode = false;
            deleteButton.hide();
            bodyWeightEntryCheckBox.setVisibility(View.GONE);
            bodyWeightEntryTV.setLayoutParams(params1);
        }
        else {
            editButton.setImageResource(R.drawable.ex_cancel);
            editMode = true;
            deleteButton.show();
            bodyWeightEntryCheckBox.setVisibility(View.VISIBLE);
            bodyWeightEntryTV.setLayoutParams(params3);
        }
        bodyWeightEntryCheckBox.setChecked(false);
        int visibility = editMode == false? View.GONE: View.VISIBLE;
        bodyWeightEntryCheckBox.setVisibility(visibility);
    }

    private void setEnterButtonListener(){
        enterButton = (Button) findViewById(R.id.button_enter_weight);
        enterButton.setOnClickListener(new View.OnClickListener(){
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
                setBodyWeightEntryTextView();
                setMonth(String.valueOf(currentMonth));
                setBodyWeightGraphData();
            }
        });
    }

    private void setToggleUnitsButtonListener(){
        unitsToggleButton = (Button) findViewById(R.id.button_units_toggle);
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
        editButton = (FloatingActionButton) findViewById(R.id.edit_button_log_body_weight);
        editButton.setImageResource(R.drawable.edit_pencil);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bodyWeightEntryTV.getText().toString().isEmpty()){
                    setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button_log_body_weight);
        deleteButton.setImageResource(R.drawable.garbage_can);
        deleteButton.hide();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode == false || bodyWeightEntryTV.getText().toString().isEmpty() || !bodyWeightEntryCheckBox.isChecked()) {
                    return;
                }
                setEditModeFeatures();
                bodyWeightsModel.removeWeight(myCalendarDate.getText().toString());
                setBodyWeightEntryTextView();
                setBodyWeightGraphData();
            }
        });
    }
}
