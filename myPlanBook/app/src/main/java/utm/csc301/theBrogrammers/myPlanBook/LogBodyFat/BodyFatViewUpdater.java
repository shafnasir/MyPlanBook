package utm.csc301.theBrogrammers.myPlanBook.LogBodyFat;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class BodyFatViewUpdater implements BodyFatObserver {

    private TextView bodyFatEntryTV, calendarDate, graphMonth;
    private LinearLayout bodyFatEntryLayout;
    private int currentMonth;
    private String currentDate;
    private boolean editMode = false;
    private FloatingActionButton deleteButton, editButton;
    private Map<String, String> monthNames;
    private CheckBox bodyFatEntryCheckBox;
    private LinearLayout.LayoutParams params1, params2, params3;
    private LineChart bodyFatMonthlyGraph;

    public BodyFatViewUpdater(){ }

    public void setCheckBoxVisibility(){
        bodyFatEntryCheckBox.setChecked(false);
        int visibility = editMode == false? View.GONE: View.VISIBLE;
        bodyFatEntryCheckBox.setVisibility(visibility);
    }

    public void setBodyFatEntryTextView(String bodyFatEntry){
        String bodyFat = bodyFatEntry == null? "": bodyFatEntry;
        this.bodyFatEntryTV.setText(bodyFat + "%");
        int visibility = bodyFat.isEmpty()? View.GONE: View.VISIBLE;
        this.bodyFatEntryTV.setVisibility(visibility);
        this.bodyFatEntryLayout.setVisibility(visibility);
    }

    public boolean setEditModeFeatures(){
        editMode = editMode == true? false: true;
        if (editMode == false){
            deleteButton.hide();
        }
        else {
            deleteButton.show();
        }
        int editDrawableId = editMode == false? R.drawable.edit_pencil: R.drawable.ex_cancel;
        editButton.setImageResource(editDrawableId);
        setCheckBoxVisibility();
        setBodyFatEntryTVParams();
        return editMode;
    }

    public void setDate(String date, int month){
        calendarDate.setText(date);
        currentDate = date;
        currentMonth = month;
    }

    public void setGraphMonth(){
        graphMonth.setText(monthNames.get(String.valueOf(currentMonth)));
    }

    public void setBodyFatEntryTVParams(){
        if (editMode == false) {
            bodyFatEntryTV.setLayoutParams(params1);
        }
        else {
            bodyFatEntryTV.setLayoutParams(params3);
        }
    }

    public void setBodyFatGraphData(Map<String, Object> bodyFatEntriesForMonth) {
        ArrayList<Entry> dataEntries = getGraphData(bodyFatEntriesForMonth);
        LineDataSet dSetBF = new LineDataSet(dataEntries, "Body Fat %");
        setDataSetStyling(dSetBF);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSetBF);
        bodyFatMonthlyGraph.setData(new LineData(dataSets));
        bodyFatMonthlyGraph.notifyDataSetChanged();
        bodyFatMonthlyGraph.invalidate();
    }

    private ArrayList<Entry> getGraphData (Map<String, Object> bodyFatEntriesForMonth){
        String[] splitDate = this.currentDate.split("/");
        return getDataSets(splitDate[1], splitDate[2], bodyFatEntriesForMonth);
    }

    private ArrayList<Entry> getDataSets(String month, String year, Map<String, Object> bodyFatEntriesForMonth){
        ArrayList<Entry> bodyFatEntries = new ArrayList<Entry>();
        for (int i = 1; i < 32; i++) {
            String testDate = String.valueOf(i) + "/" + month + "/" + year;
            if (bodyFatEntriesForMonth.containsKey(testDate)){
                bodyFatEntries.add(new Entry (i, Float.valueOf((String) bodyFatEntriesForMonth.get(testDate))));
            }
        }
        return bodyFatEntries;
    }

    private void setDataSetStyling(LineDataSet dSet){
        int colour = Color.BLUE;
        float lineWidth = 3f;
        dSet.setDrawCircleHole(false);
        dSet.setDrawValues(false);
        dSet.setLineWidth(lineWidth);
        dSet.setCircleRadius(lineWidth);
        dSet.setColor(colour);
        dSet.setCircleColor(colour);
    }

    public void setCurrentDate(){
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        calendarDate.setText(currentDate);
    }

    public void setParams(LinearLayout.LayoutParams params1, LinearLayout.LayoutParams params2, LinearLayout.LayoutParams params3){
        this.params1 = params1;
        this.params2 = params2;
        this.params3 = params3;
    }

    public void setCalendarDateTV(TextView calendarDate) {
        this.calendarDate = calendarDate;
    }

    public void setGraphMonthTV(TextView graphMonth){
        this.graphMonth = graphMonth;
    }

    public void setBodyFatEntryLayout(LinearLayout bodyFatEntryLayout) {
        this.bodyFatEntryLayout = bodyFatEntryLayout;
    }

    public void setBodyFatEntryCheckBox(CheckBox bodyFatEntryCheckBox){
        this.bodyFatEntryCheckBox = bodyFatEntryCheckBox;
    }

    public void setBodyFatEntryTextView(TextView bodyFatEntryTV){
        this.bodyFatEntryTV = bodyFatEntryTV;
    }

    public void setBodyFatMonthlyGraph(LineChart bodyFatMonthlyGraph) {
        this.bodyFatMonthlyGraph = bodyFatMonthlyGraph;
    }

    public void setDeleteButton(FloatingActionButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public void setEditButton(FloatingActionButton editButton) {
        this.editButton = editButton;
    }

    public void setMonthNames(Map<String, String> monthNames){
        this.monthNames = monthNames;
    }

    @Override
    public void updateTV(String bodyFatEntry) {
        this.setBodyFatEntryTextView(bodyFatEntry);
    }

    @Override
    public void updateGraph(Map<String, Object> bodyFatEntriesForMonth) {
        this.setBodyFatGraphData(bodyFatEntriesForMonth);
    }
}
