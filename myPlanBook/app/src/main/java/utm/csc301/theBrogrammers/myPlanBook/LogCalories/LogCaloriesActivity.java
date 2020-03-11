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

    private LinearLayout.LayoutParams params1;
    private LinearLayout.LayoutParams params2;
    private LinearLayout.LayoutParams params3;
    private EditText foodInputTextField;
    private EditText caloriesInputTextField;
    private CaloriesModel caloriesModel;
    private String currentDate;
    private LinearLayout foodItemsLayout;
    private Button enterFoodButton;
    private FloatingActionButton editCaloriesButton;
    private FloatingActionButton deleteCaloriesButton;
    private CalendarView calendarView;
    private TextView calendarDate;
    private boolean editMode = false;

    private int[] foodItemTVIds;
    private int[] foodItemLayoutIds;
    private int[] foodItemCheckBoxIds;
    private int currentMonth;
    private int foodCount;
    private int maxFoodCount = 3;
    private int maxFoodStringLength = 200;
    private int maxCalorieLength = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calories);
        this.assignParams();
        this.assignFoodItemsLayout();
        this.createCalendarView();
        this.setCurrentDate();
        this.setFoodInputTextField();
        this.setCaloriesInputTextField();
        caloriesModel = new CaloriesModel(maxFoodCount);
        this.createFoodItemLayouts();
        this.createFoodItemCheckBoxes();
        this.createFoodItemTextViews();
        this.setFoodItemTextViews();
        this.setCalendarViewListener();
        this.setEnterFoodButtonListener();
        this.setEditFloatingButtonListener();
        this.setDeleteFloatingButtonListener();
    }

    private void assignParams(){
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(45,45,45,0);
        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(45,0,45,45);
        params3 = new LinearLayout.LayoutParams(
                875,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params3.setMargins(45,0,45,45);
    }

    private void assignFoodItemsLayout() {
        foodItemsLayout = (LinearLayout) findViewById(R.id.layoutCalories);
    }

    private void createCalendarView(){
        calendarView = new CalendarView(this);
        calendarView.setBackgroundResource(R.drawable.round_outline_bordered);
        calendarView.setLayoutParams(params1);
        foodItemsLayout.addView(calendarView, 0);
    }

    private void setCurrentDate(){
        calendarDate = (TextView)findViewById(R.id.caloriesDate);
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        calendarDate.setText(currentDate);
    }

    private void setFoodInputTextField(){
        foodInputTextField = findViewById(R.id.inputFoodEditText);
    }

    private void setCaloriesInputTextField(){
        caloriesInputTextField = findViewById(R.id.inputCaloriesEditText);
    }

    private void createFoodItemTextViews(){
        this.foodItemTVIds = new int[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++){
            TextView foodItemTV = new TextView(this);
            foodItemTV.setBackgroundResource(R.drawable.round_outline);
            foodItemTV.setLayoutParams(params2);
            foodItemTV.setPadding(40,40,40,40);
            foodItemTV.setTextColor(Color.parseColor("#000000"));
            foodItemTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            foodItemTV.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
            foodItemTV.setId(ViewCompat.generateViewId());
            this.foodItemTVIds[i] = foodItemTV.getId();
            foodItemTV.setVisibility(View.GONE);
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemTV, 1);
        }
    }

    private void createFoodItemLayouts() {
        this.foodItemLayoutIds = new int[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++) {
            LinearLayout foodItemLayout = new LinearLayout(this);
            foodItemLayout.setId(ViewCompat.generateViewId());
            this.foodItemLayoutIds[i] = foodItemLayout.getId();
            foodItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            foodItemLayout.setVisibility(View.GONE);
            foodItemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            foodItemsLayout.addView(foodItemLayout, i + 5);
        }
    }

    private void createFoodItemCheckBoxes() {
        this.foodItemCheckBoxIds = new int[maxFoodCount];
        for (int i = 0; i < maxFoodCount; i++) {
            CheckBox foodItemCheckBox = new CheckBox(this);
            foodItemCheckBox.setId(ViewCompat.generateViewId());
            this.foodItemCheckBoxIds[i] = foodItemCheckBox.getId();
            foodItemCheckBox.setVisibility(View.GONE);
            LinearLayout.LayoutParams checkBoxParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            checkBoxParams.setMargins(45, 45, 0, 0);
            foodItemCheckBox.setLayoutParams(checkBoxParams);
            foodItemCheckBox.setPadding(40,40,40,40);
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemCheckBox, 0);
        }
    }

    private void setFoodItemTextViews(){
        ArrayList<String> foodForDate = caloriesModel.getFoodCalories(currentDate);
        if (foodForDate == null) {
            this.foodCount = 0;
        }
        else {
            this.foodCount = foodForDate.size();
            if (this.foodCount > 0) {
                for (int i = 0; i < foodCount; i++) {
                    TextView foodItemTV = (TextView) findViewById(this.foodItemTVIds[i]);
                    foodItemTV.setText(foodForDate.get(i));
                    foodItemTV.setVisibility(View.VISIBLE);
                }
            }
        }
        this.hideUnusedFoodItemTextViews();
    }

    private void hideUnusedFoodItemTextViews(){
        if (this.foodCount == this.maxFoodCount) {
            return;
        }
        for (int i = foodCount; i < maxFoodCount; i++) {
            TextView foodItemTV = (TextView) findViewById(this.foodItemTVIds[i]);
            LinearLayout foodItemLayout = (LinearLayout) findViewById(this.foodItemLayoutIds[i]);
            foodItemTV.setText("");
            foodItemTV.setVisibility(View.GONE);
            foodItemLayout.setVisibility(View.GONE);
        }
    }

    private void setCalendarViewListener(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (editMode == true) {
                    setEditModeFeatures();
                }
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                calendarDate.setText(date);
                currentDate = date;
                currentMonth = (currentMonth != month + 1)? month + 1: currentMonth;
                setFoodItemTextViews();
            }
        });
    }

    private void setEnterFoodButtonListener(){
        enterFoodButton = (Button) findViewById(R.id.enterFoodButton);
        enterFoodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String food = foodInputTextField.getText().toString();
                String calories = caloriesInputTextField.getText().toString();
                if (food.isEmpty() || calories.isEmpty() || foodCount == maxFoodCount || food.length() > maxFoodStringLength || calories.length() > maxCalorieLength){
                    return;
                }
                String foodItemCalories = String.valueOf(foodCount + 1) + " " + food.toUpperCase() + ":" + calories + " CALS";
                caloriesModel.addFoodCalories(currentDate, foodItemCalories);
                TextView foodItemTV = (TextView) findViewById(foodItemTVIds[foodCount]);
                foodItemTV.setText(foodItemCalories);
                foodItemTV.setVisibility(View.VISIBLE);
                foodCount++;
            }
        });
    }

    private void setEditModeFeatures(){
        if (editMode == true){
            editCaloriesButton.setImageResource(R.drawable.edit_pencil);
            editMode = false;
            deleteCaloriesButton.hide();
        }
        else {
            editCaloriesButton.setImageResource(R.drawable.ex_cancel);
            editMode = true;
            deleteCaloriesButton.show();
        }
        setCheckBoxVisibility();
        setTextViewParams();
    }

    private void setCheckBoxVisibility(){
        for (int i = 0; i < maxFoodCount; i++) {
            CheckBox foodItemCheckBox = (CheckBox) findViewById(foodItemCheckBoxIds[i]);
            if (editMode == false) {
                foodItemCheckBox.setVisibility(View.GONE);
            }
            else {
                foodItemCheckBox.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setTextViewParams(){
        for (int i = 0; i < maxFoodCount; i++) {
            TextView foodItemTV = (TextView) findViewById(foodItemTVIds[i]);
            if (editMode == false) {
                foodItemTV.setLayoutParams(params2);
            }
            else {
                foodItemTV.setLayoutParams(params2);
            }
        }
    }

    private void setEditFloatingButtonListener(){
        editCaloriesButton = (FloatingActionButton) findViewById(R.id.editCaloriesButton);
        editCaloriesButton.setImageResource(R.drawable.edit_pencil);
        editCaloriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView foodItemTV = (TextView) findViewById(foodItemTVIds[0]);
                if (!foodItemTV.getText().toString().isEmpty()){
                    setEditModeFeatures();
                }
            }
        });
    }

    private void setDeleteFloatingButtonListener(){
        deleteCaloriesButton = (FloatingActionButton) findViewById(R.id.caloriesDeleteButton);
        deleteCaloriesButton.setImageResource(R.drawable.garbage_can);
        deleteCaloriesButton.hide();
        deleteCaloriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode == true) {
                    deleteCheckedCalorieEntries();
                }
            }
        });
    }

    private void deleteCheckedCalorieEntries(){
        int[] indicesToDelete = new int[maxFoodCount];
        int currentIndex = 0;
        for (int i = 0; i < maxFoodCount; i++) {
            CheckBox foodItemCheckBox = (CheckBox) findViewById(foodItemCheckBoxIds[i]);
            TextView foodItemTV = (TextView) findViewById(foodItemTVIds[i]);
            if (!foodItemTV.getText().toString().isEmpty() && foodItemCheckBox.isChecked()) {
                indicesToDelete[currentIndex] = i;
                currentIndex++;
            }
        }
        int indexOffset = 0;
        for (Object index: indicesToDelete) {
            if (index == null) {
                break;
            }
            caloriesModel.removeFoodCalories(currentDate, ((int) index) - indexOffset);
            indexOffset++;
        }
        setEditModeFeatures();
        setFoodItemTextViews();
    }
}
