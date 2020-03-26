package utm.csc301.theBrogrammers.myPlanBook.LogBodyFat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyFatActivity extends AppCompatActivity {

    private BodyFatViewUpdater bodyFatViewUpdater;
    private BodyFatModel bodyFatModel;
    private EditText ageInputTextField, chestInputTextField, absInputTextField, thighInputTextField, heightInputTextField, waistInputTextField;
    private LinearLayout bodyFatEntryLayout, scrollViewLayoutLogBodyFat;
    private TextView bodyFatEntryTV, calendarDate, graphMonth;
    private LinearLayout.LayoutParams params1, params2, params3;
    private Button enterBodyFatEntryButton;
    private CalendarView calendarView;
    private CheckBox bodyFatEntryCheckBox;
    private FloatingActionButton editButton, deleteButton;
    private LineChart bodyFatMonthlyGraph;
    private Map<String,String> monthNames;
    private boolean editMode = false;
    private int MARGIN_SIZE, EDIT_MODE_TV_WIDTH, PADDING_SIZE;
    private final String[] dayStrings = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_fat);
        this.bodyFatViewUpdater = new BodyFatViewUpdater();
        this.bodyFatModel = new BodyFatModel();
        this.bodyFatModel.attach(this.bodyFatViewUpdater);
        this.assignParamValues();
        this.assignParams();
        this.assignInputTextFields();
        this.assignTextViews();
        this.assignGraph();
        this.styleGraph();
        this.setMonthNames();
        this.scrollViewLayoutLogBodyFat = (LinearLayout) findViewById(R.id.scrollview_layout_log_body_fat);
        this.bodyFatViewUpdater.setCurrentDate();
        this.createBodyFatEntryLayout();
        this.createBodyFatEntryCheckBox();
        this.createBodyFatEntryTextView();
        this.bodyFatModel.notifyObserversEntryChange(this.calendarDate.getText().toString());
        this.setEnterButtonListener();
        this.setCalendarViewListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
        this.bodyFatViewUpdater.setGraphMonth();
        this.bodyFatModel.notifyObserversGraphChange(this.getMonth(), this.getYear());
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
        bodyFatViewUpdater.setParams(params1, params2, params3);
    }

    private void assignInputTextFields(){
        ageInputTextField = (EditText) findViewById(R.id.edit_text_input_age);
        chestInputTextField = (EditText) findViewById(R.id.edit_text_input_chest);
        absInputTextField = (EditText) findViewById(R.id.edit_text_input_abs);
        thighInputTextField = (EditText) findViewById(R.id.edit_text_input_thigh);
        heightInputTextField = (EditText) findViewById(R.id.edit_text_input_height);
        waistInputTextField = (EditText) findViewById(R.id.edit_text_input_waist);
    }

    private void assignTextViews(){
        calendarDate = (TextView) findViewById(R.id.date_log_body_fat);
        graphMonth = (TextView) findViewById(R.id.graph_month_tv_body_fat);
        bodyFatViewUpdater.setCalendarDateTV(calendarDate);
        bodyFatViewUpdater.setGraphMonthTV(graphMonth);
    }

    private void assignGraph(){
        bodyFatMonthlyGraph = (LineChart) findViewById(R.id.body_fat_graph);
        bodyFatViewUpdater.setBodyFatMonthlyGraph(this.bodyFatMonthlyGraph);
    }

    private void createBodyFatEntryLayout() {
        this.bodyFatEntryLayout = new LinearLayout(this);
        this.bodyFatEntryLayout.setId(ViewCompat.generateViewId());
        this.bodyFatEntryLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.bodyFatEntryLayout.setVisibility(View.GONE);
        this.bodyFatEntryLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        scrollViewLayoutLogBodyFat.addView(this.bodyFatEntryLayout, 22);
        bodyFatViewUpdater.setBodyFatEntryLayout(this.bodyFatEntryLayout);
    }

    private void createBodyFatEntryCheckBox() {
        this.bodyFatEntryCheckBox = new CheckBox(this);
        this.bodyFatEntryCheckBox.setId(ViewCompat.generateViewId());
        this.bodyFatEntryCheckBox.setVisibility(View.GONE);
        LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxParams.setMargins(MARGIN_SIZE, 0, 0, 0);
        this.bodyFatEntryCheckBox.setLayoutParams(checkBoxParams);
        this.bodyFatEntryLayout.addView(this.bodyFatEntryCheckBox, 0);
        bodyFatViewUpdater.setBodyFatEntryCheckBox(this.bodyFatEntryCheckBox);
    }

    private void createBodyFatEntryTextView(){
        this.bodyFatEntryTV = new TextView(this);
        this.bodyFatEntryTV.setBackgroundResource(R.drawable.box_outline_bordered);
        this.bodyFatEntryTV.setLayoutParams(params1);
        this.bodyFatEntryTV.setPadding(PADDING_SIZE,PADDING_SIZE,PADDING_SIZE,PADDING_SIZE);
        this.bodyFatEntryTV.setTextColor(Color.parseColor("#858585"));
        this.bodyFatEntryTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
        this.bodyFatEntryTV.setTypeface(Typeface.SANS_SERIF);
        this.bodyFatEntryTV.setId(ViewCompat.generateViewId());
        this.bodyFatEntryTV.setVisibility(View.GONE);
        this.bodyFatEntryLayout.addView(this.bodyFatEntryTV, 1);
        bodyFatViewUpdater.setBodyFatEntryTextView(this.bodyFatEntryTV);
    }

    private void setEnterButtonListener(){
        enterBodyFatEntryButton = (Button) findViewById(R.id.button_enter_body_fat);
        enterBodyFatEntryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (bodyFatEntryTV.getText().toString().length() < 2) {
                    addBodyFatEntry();
                }
            }
        });
    }

    private void addBodyFatEntry(){
        String age = ageInputTextField.getText().toString();
        String chestSFmm = chestInputTextField.getText().toString();
        String absSFmm = absInputTextField.getText().toString();
        String thighSFmm = thighInputTextField.getText().toString();
        String height = heightInputTextField.getText().toString();
        String waist = waistInputTextField.getText().toString();
        if (age.length() > 10 || age.isEmpty() || chestSFmm.length() > 10 || chestSFmm.isEmpty()
                || absSFmm.length() > 10 || absSFmm.isEmpty() || thighSFmm.length() > 10 || thighSFmm.isEmpty()
                || height.length() > 10 || height.isEmpty() || waist.length() > 10 || waist.isEmpty()) {
            return;
        }
        this.bodyFatModel.addBodyFatEntry(calendarDate.getText().toString(),
                calculateBFAvg(age, chestSFmm, absSFmm, thighSFmm, height, waist));
    }

    private String calculateBFAvg(String age, String chestSFmm, String absSFmm, String thighSFmm, String height, String waist) {
        double sum = Double.valueOf(chestSFmm) + Double.valueOf(absSFmm) + Double.valueOf(thighSFmm);
        int ageValue = Integer.valueOf(age);
        double boneDensity = 1.10938 - 0.0008267 * sum + 0.16 * Math.pow(10, -5) * Math.pow(sum, 2) - 25.74 * Math.pow(10, -5)
                * (double) ageValue;
        double skinFoldCaliperBodyFat1 = (4.95/ boneDensity - 4.5) * 100;
        double skinFoldCaliperBodyFat2 = (4.57/ boneDensity - 4.142) * 100;
        double waistHeightRatioPercent = (Double.valueOf(waist)/ Double.valueOf(height)) * 100;
        double waistBodyFat = (waistHeightRatioPercent / 0.05 - 775) / 10;
        double bodyFatAvg = (skinFoldCaliperBodyFat1 + skinFoldCaliperBodyFat2 + waistBodyFat) / 3;
        String bodyFat = String.format("%.1f", bodyFatAvg);
        return bodyFat;
    }

    private void setCalendarViewListener(){
        calendarView = (CalendarView) findViewById(R.id.calendar_view_log_body_fat);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) { editMode = bodyFatViewUpdater.setEditModeFeatures(); }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                bodyFatViewUpdater.setDate(date, month + 1);
                bodyFatViewUpdater.setGraphMonth();
                bodyFatModel.notifyObserversEntryChange(date);
                bodyFatModel.notifyObserversGraphChange(String.valueOf(month + 1), String.valueOf(year));
            }
        });
    }

    private void setEditFloatingButtonListener(){
        editButton = (FloatingActionButton) findViewById(R.id.edit_button_log_body_fat);
        this.bodyFatViewUpdater.setEditButton(editButton);
        editButton.setImageResource(R.drawable.edit_pencil);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bodyFatEntryTV.getText().toString().length() > 1) {
                    editMode = bodyFatViewUpdater.setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button_log_body_fat);
        this.bodyFatViewUpdater.setDeleteButton(deleteButton);
        deleteButton.setImageResource(R.drawable.garbage_can);
        deleteButton.hide();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBodyFatEntry();
            }
        });
    }

    private void deleteBodyFatEntry(){
        if (editMode == false || bodyFatEntryTV.getText().toString().length() < 2 || !bodyFatEntryCheckBox.isChecked()) {
            return;
        }
        bodyFatModel.deleteBodyFatEntry(calendarDate.getText().toString());
        editMode = bodyFatViewUpdater.setEditModeFeatures();
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
        this.bodyFatViewUpdater.setMonthNames(monthNames);
    }

    private void styleGraph() {
        XAxis xAxis = bodyFatMonthlyGraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dayStrings));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = bodyFatMonthlyGraph.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.removeAllLimitLines();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);

        bodyFatMonthlyGraph.getDescription().setEnabled(false);
        bodyFatMonthlyGraph.getAxisRight().setEnabled(false);
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
