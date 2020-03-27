package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogCaloriesActivity extends AppCompatActivity {

    private CaloriesViewUpdater caloriesViewUpdater;
    private CaloriesModel caloriesModel;
    private EditText foodInputTextField, caloriesInputTextField;
    private LinearLayout scrollViewLayoutLogCalories;
    private TextView calendarDate, totalCalsTV;
    private LinearLayout.LayoutParams params1, params2, params3;
    private Button enterButton;
    private CalendarView calendarView;
    private FloatingActionButton editButton, deleteButton;
    private boolean editMode = false;
    private int MARGIN_SIZE, EDIT_MODE_TV_WIDTH, PADDING_SIZE;
    private int[] foodItemTVIds;
    private int[] foodItemLayoutIds;
    private int[] foodItemCheckBoxIds;
    private int maxFoodCount = 100;
    private int maxFoodStringLength = 200;
    private int maxCalorieLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calories);
        this.caloriesViewUpdater = new CaloriesViewUpdater();
        this.caloriesModel = new CaloriesModel(maxFoodCount);
        this.caloriesModel.attach(this.caloriesViewUpdater);
        this.assignParamValues();
        this.assignParams();
        this.assignInputTextFields();
        this.assignTextViews();
        this.caloriesViewUpdater.setMaxFoodCount(this.maxFoodCount);
        scrollViewLayoutLogCalories = (LinearLayout) findViewById(R.id.scrollview_layout_log_calories);
        this.caloriesViewUpdater.setCurrentDate();
        this.createFoodItemLayouts();
        this.createFoodItemCheckBoxes();
        this.createFoodItemTextViews();
        this.setEnterButtonListener();
        this.setCalendarViewListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
        this.caloriesModel.notifyObserversEntryChange(this.calendarDate.getText().toString());
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
        caloriesViewUpdater.setParams(params1, params2, params3);
    }

    private void assignInputTextFields(){
        foodInputTextField = findViewById(R.id.edit_text_input_food);
        caloriesInputTextField = findViewById(R.id.edit_text_input_calories);
    }

    private void assignTextViews(){
        calendarDate = (TextView) findViewById(R.id.date_log_calories);
        totalCalsTV = (TextView) findViewById(R.id.total_calories_tv);
        caloriesViewUpdater.setCalendarDateTV(calendarDate);
        caloriesViewUpdater.setTotalCalsTV(totalCalsTV);
    }

    private void createFoodItemLayouts() {
        this.foodItemLayoutIds = new int[maxFoodCount];
        LinearLayout[] foodItemLayouts = new LinearLayout[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++) {
            LinearLayout foodItemLayout = new LinearLayout(this);
            foodItemLayout.setId(ViewCompat.generateViewId());
            this.foodItemLayoutIds[i] = foodItemLayout.getId();
            foodItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            foodItemLayout.setVisibility(View.GONE);
            foodItemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            foodItemLayouts[i] = foodItemLayout;
            scrollViewLayoutLogCalories.addView(foodItemLayout, i + 10);
        }
        this.caloriesViewUpdater.setFoodItemLayouts(foodItemLayouts);
    }

    private void createFoodItemCheckBoxes() {
        this.foodItemCheckBoxIds = new int[maxFoodCount];
        CheckBox[] foodItemCheckBoxes = new CheckBox[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++) {
            CheckBox foodItemCheckBox = new CheckBox(this);
            foodItemCheckBox.setId(ViewCompat.generateViewId());
            this.foodItemCheckBoxIds[i] = foodItemCheckBox.getId();
            foodItemCheckBox.setVisibility(View.GONE);
            LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            checkBoxParams.setMargins(MARGIN_SIZE, 0, 0, 0);
            foodItemCheckBox.setLayoutParams(checkBoxParams);
            foodItemCheckBoxes[i] = foodItemCheckBox;
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemCheckBox, 0);
        }
        this.caloriesViewUpdater.setFoodItemCheckBoxes(foodItemCheckBoxes);
    }

    private void createFoodItemTextViews(){
        this.foodItemTVIds = new int[maxFoodCount];
        TextView[] foodItemTextViews = new TextView[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++){
            TextView foodItemTV = new TextView(this);
            foodItemTV.setBackgroundResource(R.drawable.box_outline_bordered);
            foodItemTV.setLayoutParams(params1);
            foodItemTV.setPadding(PADDING_SIZE,PADDING_SIZE,PADDING_SIZE,PADDING_SIZE);
            foodItemTV.setTextColor(Color.parseColor("#858585"));
            foodItemTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            foodItemTV.setTypeface(Typeface.SANS_SERIF);
            foodItemTV.setId(ViewCompat.generateViewId());
            this.foodItemTVIds[i] = foodItemTV.getId();
            foodItemTV.setVisibility(View.GONE);
            foodItemTextViews[i] = foodItemTV;
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemTV, 1);
        }
        this.caloriesViewUpdater.setFoodItemTextViews(foodItemTextViews);
    }

    private void setEnterButtonListener(){
        enterButton = (Button) findViewById(R.id.button_enter_calories);
        enterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addCalorieEntry();
            }
        });
    }

    private void addCalorieEntry(){
        String food = foodInputTextField.getText().toString();
        String calories = caloriesInputTextField.getText().toString();
        if (food.isEmpty() || calories.isEmpty() || caloriesViewUpdater.getFoodCount() == maxFoodCount
                || food.length() > maxFoodStringLength || calories.length() > maxCalorieLength){
            return;
        }
        String foodItemCalories = food.toUpperCase() + ":" + calories + " CALS";
        caloriesModel.addCalorieEntry(calendarDate.getText().toString(), foodItemCalories);
    }

    private void setCalendarViewListener(){
        calendarView = (CalendarView) findViewById(R.id.calendar_view_log_calories);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) { editMode = caloriesViewUpdater.setEditModeFeatures(); }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                caloriesViewUpdater.setDate(date, month + 1);
                caloriesModel.notifyObserversEntryChange(date);
            }
        });
    }

    private void setEditFloatingButtonListener(){
        editButton = (FloatingActionButton) findViewById(R.id.edit_button_log_calories);
        this.caloriesViewUpdater.setEditButton(editButton);
        editButton.setImageResource(R.drawable.edit_pencil);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView foodItemTV = (TextView) findViewById(foodItemTVIds[0]);
                if (!foodItemTV.getText().toString().isEmpty()){
                    editMode = caloriesViewUpdater.setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_button_log_calories);
        this.caloriesViewUpdater.setDeleteButton(deleteButton);
        deleteButton.setImageResource(R.drawable.garbage_can);
        deleteButton.hide();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCalorieEntries();
            }
        });
    }

    private void deleteCalorieEntries(){
        if (editMode == false) { return; }
        int[] indicesToDelete = getIndicesToDelete();
        if (indicesToDelete == null) { return; }
        caloriesModel.deleteCalorieEntries(calendarDate.getText().toString(), indicesToDelete);
        editMode = caloriesViewUpdater.setEditModeFeatures();
    }

    private int[] getIndicesToDelete() {
        int[] indicesToDelete = new int[maxFoodCount];
        int currentIndex = 0;
        int numChecked = 0;
        for (int i = 0; i < maxFoodCount; i++) {
            CheckBox foodItemCheckBox = (CheckBox) findViewById(foodItemCheckBoxIds[i]);
            TextView foodItemTV = (TextView) findViewById(foodItemTVIds[i]);
            if (!foodItemTV.getText().toString().isEmpty() && foodItemCheckBox.isChecked()) {
                indicesToDelete[currentIndex] = i;
                currentIndex++;
                numChecked++;
            }
        }
        if (numChecked == 0) {
            return null;
        }
        return indicesToDelete;
    }

}
