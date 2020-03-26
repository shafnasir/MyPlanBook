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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.BodyWeightModel;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.BodyWeightViewUpdater;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    private BodyWeightViewUpdater bodyWeightViewUpdater;
    private BodyWeightModel bodyWeightModel;
    private EditText bodyWeightsInputTextField;
    private LinearLayout scrollViewLayoutLogBodyWeight, bodyWeightEntryLayout;
    private TextView calendarDate, graphMonth, bodyWeightEntryTV;
    private LinearLayout.LayoutParams params1, params2, params3;
    private Button enterButton, unitsToggleButton;
    private CalendarView calendarView;
    private CheckBox bodyWeightEntryCheckBox;
    private FloatingActionButton editButton, deleteButton;
    private LineChart bodyWeightMonthlyGraph;
    private Map<String,String> monthNames;
    private boolean editMode = false;
    private int MARGIN_SIZE, EDIT_MODE_TV_WIDTH, PADDING_SIZE;
    private final String[] dayStrings = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_weight);
        this.bodyWeightViewUpdater = new BodyWeightViewUpdater();
        this.bodyWeightModel = new BodyWeightModel();
        this.bodyWeightModel.attach(this.bodyWeightViewUpdater);
        this.assignParamValues();
        this.assignParams();
        this.setBodyWeightInputTextField();
        this.assignTextViews();
        this.assignGraph();
        this.styleGraph();
        this.setMonthNames();
        this.scrollViewLayoutLogBodyWeight = (LinearLayout) findViewById(R.id.scrollview_layout_log_body_weight);
        this.bodyWeightViewUpdater.setCurrentDate();
        this.createBodyWeightEntryLayout();
        this.createBodyWeightEntryCheckBox();
        this.createBodyWeightEntryTextView();
        this.setEnterButtonListener();
        this.setToggleUnitsButtonListener();
        this.setCalendarViewListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
        this.bodyWeightViewUpdater.setGraphMonth();
        this.bodyWeightModel.notifyObserversEntryChange(this.calendarDate.getText().toString());
        this.bodyWeightModel.notifyObserversGraphChange(this.getMonth(), this.getYear());
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
        bodyWeightViewUpdater.setParams(params1, params2, params3);
    }

    private void setBodyWeightInputTextField(){
        bodyWeightsInputTextField = findViewById(R.id.edit_text_input_weight);
        bodyWeightsInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void assignTextViews(){
        calendarDate = (TextView) findViewById(R.id.date_log_body_weight);
        graphMonth = (TextView) findViewById(R.id.graph_month_tv_body_weight);
        bodyWeightViewUpdater.setCalendarDateTV(calendarDate);
        bodyWeightViewUpdater.setGraphMonthTV(graphMonth);
    }

    private void assignGraph(){
        bodyWeightMonthlyGraph = (LineChart) findViewById(R.id.body_weight_graph);
        bodyWeightViewUpdater.setBodyWeightMonthlyGraph(this.bodyWeightMonthlyGraph);
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
        bodyWeightViewUpdater.setBodyWeightEntryLayout(this.bodyWeightEntryLayout);
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
        bodyWeightViewUpdater.setBodyWeightEntryCheckBox(this.bodyWeightEntryCheckBox);
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
        bodyWeightViewUpdater.setBodyWeightEntryTextView(this.bodyWeightEntryTV);
    }

    private void setEnterButtonListener(){
        enterButton = (Button) findViewById(R.id.button_enter_weight);
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (bodyWeightEntryTV.getText().toString().isEmpty()) {
                    addBodyWeightEntry();
                }
            }
        });
    }

    private void addBodyWeightEntry(){
        String bwInput = bodyWeightsInputTextField.getText().toString();
        if (bwInput.isEmpty() || bwInput.length() > 6) {
            return;
        }
        double weight = Double.parseDouble(bwInput);
        String date = calendarDate.getText().toString();
        String units = unitsToggleButton.getText().toString();
        String weightInLbsKg = (units.equals("LBS"))? String.format("%.1f", weight)+ " LBS: "
                + String.format("%.1f", weight*(1/2.20462)) + " KG": String.format("%.1f", weight*(2.20462)) +
                " LBS: " + String.format("%.1f", weight) + " KG";
        this.bodyWeightModel.addBodyWeightEntry(date, weightInLbsKg);
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

    private void setCalendarViewListener(){
        calendarView = (CalendarView) findViewById(R.id.calendar_view_log_body_weight);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) { editMode = bodyWeightViewUpdater.setEditModeFeatures(); }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                bodyWeightViewUpdater.setDate(date, month + 1);
                bodyWeightViewUpdater.setGraphMonth();
                bodyWeightModel.notifyObserversEntryChange(date);
                bodyWeightModel.notifyObserversGraphChange(String.valueOf(month + 1), String.valueOf(year));
            }
        });
    }

    private void setEditFloatingButtonListener(){
        editButton = (FloatingActionButton) findViewById(R.id.edit_button_log_body_weight);
        this.bodyWeightViewUpdater.setEditButton(editButton);
        editButton.setImageResource(R.drawable.edit_pencil);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bodyWeightEntryTV.getText().toString().isEmpty()){
                    editMode = bodyWeightViewUpdater.setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button_log_body_weight);
        this.bodyWeightViewUpdater.setDeleteButton(deleteButton);
        deleteButton.setImageResource(R.drawable.garbage_can);
        deleteButton.hide();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBodyWeightEntry();
            }
        });
    }

    private void deleteBodyWeightEntry() {
        if (editMode == false || bodyWeightEntryTV.getText().toString().isEmpty() || !bodyWeightEntryCheckBox.isChecked()) {
            return;
        }
        bodyWeightModel.deleteBodyWeightEntry(calendarDate.getText().toString());
        editMode = bodyWeightViewUpdater.setEditModeFeatures();
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
        this.bodyWeightViewUpdater.setMonthNames(monthNames);
    }

    private void styleGraph() {
        XAxis xAxis = bodyWeightMonthlyGraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayStrings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = bodyWeightMonthlyGraph.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        bodyWeightMonthlyGraph.getDescription().setEnabled(false);
        bodyWeightMonthlyGraph.getAxisRight().setEnabled(false);
    }

    private String getMonth(){
        String[] split = calendarDate.getText().toString().split("/");
        return split[1];
    }

    private String getYear(){
        String[] split = calendarDate.getText().toString().split("/");
        return split[2];
    }

}
