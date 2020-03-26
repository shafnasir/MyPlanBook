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
    private Button enterFoodButton;
    private FloatingActionButton editCaloriesButton;
    private FloatingActionButton deleteCaloriesButton;
    private CalendarView calendarView;
    private TextView calendarDate;
    private TextView totalCalsTV;
    private boolean editMode = false;

    private int[] foodItemTVIds;
    private int[] foodItemLayoutIds;
    private int[] foodItemCheckBoxIds;
    private int currentMonth;
    private int foodCount;
    private int totalCals;
    private int maxFoodCount = 100;
    private int maxFoodStringLength = 200;
    private int maxCalorieLength = 10;
    private int MARGIN_SIZE, EDIT_MODE_TV_WIDTH, PADDING_SIZE;
    private LinearLayout scrollViewLayoutLogCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calories);
        this.assignParamValues();
        this.assignParams();
        scrollViewLayoutLogCalories = (LinearLayout) findViewById(R.id.scrollview_layout_log_calories);
        calendarView = (CalendarView) findViewById(R.id.calendar_view_log_calories);
        this.assignInputTextFields();
        this.setCurrentDate();
        this.setTotalCalsTextView();
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

    private void assignInputTextFields(){
        foodInputTextField = findViewById(R.id.edit_text_input_food);
        caloriesInputTextField = findViewById(R.id.edit_text_input_calories);
    }

    private void setCurrentDate(){
        calendarDate = (TextView)findViewById(R.id.date_log_calories);
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        calendarDate.setText(currentDate);
    }

    private void setTotalCalsTextView() {
        totalCalsTV = findViewById(R.id.total_calories_tv);
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
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            scrollViewLayoutLogCalories.addView(foodItemLayout, i + 10);
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
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            checkBoxParams.setMargins(MARGIN_SIZE, 0, 0, 0);
            foodItemCheckBox.setLayoutParams(checkBoxParams);
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemCheckBox, 0);
        }
    }

    private void createFoodItemTextViews(){
        this.foodItemTVIds = new int[maxFoodCount];
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
            LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[i]);
            foodItemLayout.addView(foodItemTV, 1);
        }
    }

    private void setFoodItemTextViews(){
        ArrayList<String> foodForDate = caloriesModel.getFoodCalories(currentDate);
        this.totalCals = 0;
        if (foodForDate == null) {
            this.foodCount = 0;
        }
        else {
            this.foodCount = foodForDate.size();
            if (this.foodCount > 0) {
                for (int i = 0; i < foodCount; i++) {
                    TextView foodItemTV = (TextView) findViewById(this.foodItemTVIds[i]);
                    LinearLayout foodItemLayout = (LinearLayout) findViewById(this.foodItemLayoutIds[i]);
                    foodItemTV.setText(String.valueOf(i+1) + " " + foodForDate.get(i));
                    foodItemTV.setVisibility(View.VISIBLE);
                    foodItemLayout.setVisibility(View.VISIBLE);
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
            foodItemCheckBox.setChecked(false);
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
                foodItemTV.setLayoutParams(params1);
            }
            else {
                foodItemTV.setLayoutParams(params3);
            }
        }
    }

    private void setEnterFoodButtonListener(){
        enterFoodButton = (Button) findViewById(R.id.button_enter_calories);
        enterFoodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String food = foodInputTextField.getText().toString();
                String calories = caloriesInputTextField.getText().toString();
                if (food.isEmpty() || calories.isEmpty() || foodCount == maxFoodCount || food.length() > maxFoodStringLength || calories.length() > maxCalorieLength){
                    return;
                }
                String foodItemCalories = food.toUpperCase() + ":" + calories + " CALS";
                caloriesModel.addFoodCalories(currentDate, foodItemCalories);
                TextView foodItemTV = (TextView) findViewById(foodItemTVIds[foodCount]);
                LinearLayout foodItemLayout = (LinearLayout) findViewById(foodItemLayoutIds[foodCount]);
                foodItemTV.setText(String.valueOf(foodCount + 1) + " " + foodItemCalories);
                foodItemTV.setVisibility(View.VISIBLE);
                foodItemLayout.setVisibility(View.VISIBLE);
                foodCount++;
                totalCals += Integer.parseInt(calories);
                setTotalCalsTVText();
            }
        });
    }

    private void setEditFloatingButtonListener(){
        editCaloriesButton = (FloatingActionButton) findViewById(R.id.edit_button_log_calories);
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
        deleteCaloriesButton = (FloatingActionButton) findViewById(R.id.delete_button_log_calories);
        deleteCaloriesButton.setImageResource(R.drawable.garbage_can);
        deleteCaloriesButton.hide();
        deleteCaloriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode == true) { deleteCheckedCalorieEntries(); }
            }
        });
    }

    private void deleteCheckedCalorieEntries(){
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
        if (numChecked == 0) { return; }
        int indexOffset = 0;
        for (int i = 0; i < maxFoodCount; i++) {
            if (indicesToDelete[i] == 0 && indexOffset > 0) { break; }
            caloriesModel.removeFoodCalories(currentDate, indicesToDelete[i] - indexOffset);
            indexOffset++;
        }
        setEditModeFeatures();
        setFoodItemTextViews();
    }
}
