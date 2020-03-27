package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

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

public class CaloriesViewUpdater implements CaloriesObserver {

    private int currentMonth, maxFoodCount, totalCals, foodCount;
    private CheckBox[] foodItemCheckBoxes;
    private TextView[] foodItemTextViews;
    private LinearLayout[] foodItemLayouts;
    private String currentDate;
    private boolean editMode = false;
    private FloatingActionButton deleteButton, editButton;
    private LinearLayout.LayoutParams params1, params2, params3;
    private TextView totalCalsTV, calendarDate;

    public CaloriesViewUpdater(){ }

    private void setCheckBoxVisibility(){
        for (int i = 0; i < maxFoodCount; i++) {
            foodItemCheckBoxes[i].setChecked(false);
            int visibility = editMode == false? View.GONE: View.VISIBLE;
            foodItemCheckBoxes[i].setVisibility(visibility);
        }
    }

    public void setFoodItemTextViews(ArrayList<String> foodForDate) {
        this.totalCals = 0;
        if (foodForDate == null) {
            this.foodCount = 0;
        }
        else {
            this.foodCount = foodForDate.size();
            if (this.foodCount > 0) {
                for (int i = 0; i < foodCount; i++) {
                    foodItemTextViews[i].setText(String.valueOf(i+1) + " " + foodForDate.get(i));
                    foodItemTextViews[i].setVisibility(View.VISIBLE);
                    foodItemLayouts[i].setVisibility(View.VISIBLE);
                    addCaloriesToTotal(foodForDate.get(i));
                }
            }
        }
        this.setTotalCalsTVText();
        this.hideUnusedFoodItemTextViews();
    }

    private void addCaloriesToTotal(String foodCalories) {
        String[] foodCalSplitColon = foodCalories.split(":");
        String[] foodCalSplitSpace = foodCalSplitColon[foodCalSplitColon.length - 1].split(" ");
        int calsToAdd = Integer.parseInt(foodCalSplitSpace[0]);
        totalCals += calsToAdd;
    }

    private void setTotalCalsTVText() {
        this.totalCalsTV.setText("TOTAL: " + String.valueOf(totalCals) + " CALS");
    }

    private void hideUnusedFoodItemTextViews(){
        if (this.foodCount == this.maxFoodCount) { return; }
        for (int i = foodCount; i < maxFoodCount; i++) {
            foodItemTextViews[i].setText("");
            foodItemTextViews[i].setVisibility(View.GONE);
            foodItemLayouts[i].setVisibility(View.GONE);
        }
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
        setTVParams();
        return editMode;
    }

    private void setTVParams(){
        for (int i = 0; i < maxFoodCount; i++) {
            if (editMode == false) {
                foodItemTextViews[i].setLayoutParams(params1);
            }
            else {
                foodItemTextViews[i].setLayoutParams(params3);
            }
        }
    }

    public void setDate(String date, int month){
        calendarDate.setText(date);
        currentDate = date;
        currentMonth = month;
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

    public void setMaxFoodCount(int maxFoodCount){
        this.maxFoodCount = maxFoodCount;
    }

    public void setFoodItemCheckBoxes(CheckBox[] foodItemCheckBoxes){
        this.foodItemCheckBoxes = foodItemCheckBoxes;
    }

    public void setFoodItemTextViews(TextView[] foodItemTextViews) {
        this.foodItemTextViews = foodItemTextViews;
    }

    public void setFoodItemLayouts(LinearLayout[] foodItemLayouts) {
        this.foodItemLayouts = foodItemLayouts;
    }

    public void setTotalCalsTV(TextView totalCalsTV) {
        this.totalCalsTV = totalCalsTV;
    }

    public void setCalendarDateTV(TextView calendarDate) {
        this.calendarDate = calendarDate;
    }

    public void setDeleteButton(FloatingActionButton deleteButton) { this.deleteButton = deleteButton; }

    public void setEditButton(FloatingActionButton editButton) {
        this.editButton = editButton;
    }

    public int getFoodCount(){
        return this.foodCount;
    }

    @Override
    public void updateTVs(ArrayList<String> foodForDate) {
        this.setFoodItemTextViews(foodForDate);
    }
}
