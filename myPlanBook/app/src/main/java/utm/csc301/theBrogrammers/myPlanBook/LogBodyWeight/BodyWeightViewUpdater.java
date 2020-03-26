package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

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
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class BodyWeightViewUpdater implements BodyWeightObserver {

    private TextView bodyWeightEntryTV, calendarDate, graphMonth;
    private LinearLayout bodyWeightEntryLayout;
    private int currentMonth;
    private String currentDate;
    private boolean editMode = false;
    private FloatingActionButton deleteButton, editButton;
    private Map<String, String> monthNames;
    private CheckBox bodyWeightEntryCheckBox;
    private LinearLayout.LayoutParams params1, params2, params3;
    private LineChart bodyWeightMonthlyGraph;

    public BodyWeightViewUpdater(){ }

    public void hideCheckBox(){
        bodyWeightEntryCheckBox.setChecked(false);
        int visibility = editMode == false? View.GONE: View.VISIBLE;
        bodyWeightEntryCheckBox.setVisibility(visibility);
    }

    public void setBodyWeightEntryTextView(String bodyWeightEntry) {
        String bodyWeight = bodyWeightEntry == null? "": bodyWeightEntry;
        this.bodyWeightEntryTV.setText(bodyWeight);
        int visibility = bodyWeight.isEmpty()? View.GONE: View.VISIBLE;
        this.bodyWeightEntryTV.setVisibility(visibility);
        this.bodyWeightEntryLayout.setVisibility(visibility);
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
        hideCheckBox();
        setBodyWeightEntryTVParams();
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

    public void setBodyWeightEntryTVParams(){
        if (editMode == false) {
            bodyWeightEntryTV.setLayoutParams(params1);
        }
        else {
            bodyWeightEntryTV.setLayoutParams(params3);
        }
    }

    public void setBodyWeightGraphData(Map<String, Object> bodyWeightEntriesForMonth) {
        ArrayList<Entry>[] dataEntries = getGraphData(bodyWeightEntriesForMonth);
        LineDataSet dSetLbs = new LineDataSet(dataEntries[0], "BODY WEIGHTS IN LBS");
        LineDataSet dSetKg = new LineDataSet(dataEntries[1], "BODY WEIGHTS IN KG");

        setDataSetStyling(dSetLbs, "lbs");
        setDataSetStyling(dSetKg, "kg");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSetLbs);
        dataSets.add(dSetKg);
        bodyWeightMonthlyGraph.setData(new LineData(dataSets));
        bodyWeightMonthlyGraph.notifyDataSetChanged();
        bodyWeightMonthlyGraph.invalidate();
    }

    private ArrayList<Entry>[] getGraphData (Map<String, Object> bodyWeightEntriesForMonth){
        String[] splitDate = this.currentDate.split("/");
        return getDataSets(splitDate[1], splitDate[2], bodyWeightEntriesForMonth);
    }

    private ArrayList<Entry>[] getDataSets(String month, String year, Map<String, Object> bodyWeightEntriesForMonth){
        ArrayList<Entry>[] dataSets = new ArrayList[2];
        ArrayList<Entry> entriesLbs = new ArrayList<Entry>();
        ArrayList<Entry> entriesKg = new ArrayList<Entry>();

        for (int i = 1; i < 32; i++) {
            String testDate = String.valueOf(i) + "/" + month + "/" + year;
            if (bodyWeightEntriesForMonth.containsKey(testDate)){
                String[] splitWeights = ((String)bodyWeightEntriesForMonth.get(testDate)).split(" ");
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

    public void setBodyWeightEntryLayout(LinearLayout bodyWeightEntryLayout) {
        this.bodyWeightEntryLayout = bodyWeightEntryLayout;
    }

    public void setBodyWeightEntryCheckBox(CheckBox bodyWeightEntryCheckBox){
        this.bodyWeightEntryCheckBox = bodyWeightEntryCheckBox;
    }

    public void setBodyWeightEntryTextView(TextView bodyWeightEntryTV){
        this.bodyWeightEntryTV = bodyWeightEntryTV;
    }

    public void setBodyWeightMonthlyGraph(LineChart bodyWeightMonthlyGraph) {
        this.bodyWeightMonthlyGraph = bodyWeightMonthlyGraph;
    }

    public void setDeleteButton(FloatingActionButton deleteButton) { this.deleteButton = deleteButton; }

    public void setEditButton(FloatingActionButton editButton) {
        this.editButton = editButton;
    }

    public void setMonthNames(Map<String, String> monthNames){
        this.monthNames = monthNames;
    }

    @Override
    public void updateTV(String bodyWeightEntry) {
        this.setBodyWeightEntryTextView(bodyWeightEntry);
    }

    @Override
    public void updateGraph(Map<String, Object> bodyWeightEntriesForMonth) {
        this.setBodyWeightGraphData(bodyWeightEntriesForMonth);
    }

}
