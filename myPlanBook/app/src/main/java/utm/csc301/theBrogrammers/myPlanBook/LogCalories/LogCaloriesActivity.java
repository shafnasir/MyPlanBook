package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.Calendar;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogCaloriesActivity extends AppCompatActivity {

    private LinearLayout.LayoutParams params1;
    private LinearLayout.LayoutParams params2;
    private EditText foodInputTextField;
    private EditText caloriesInputTextField;
    private CaloriesModel caloriesModel;
    private int currentMonth;
    private String currentDate;
    private LinearLayout foodItemsLayout;
    private int[] foodItemTVIds;
    private int foodCount;
    private Button enterFoodButton;
    private int maxFoodCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calories);
        this.assignParams();
        this.assignFoodItemsLayout();
        this.setCurrentDate();
        this.setFoodInputTextField();
        this.setCaloriesInputTextField();
        caloriesModel = new CaloriesModel(maxFoodCount);
        this.createFoodItemTextViews();
        this.setFoodItemTextViews();
        this.setEnterFoodButtonListener();
    }

    private void assignParams(){
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(45,45,45,0);
        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(45,45,45,45);
    }

    private void assignFoodItemsLayout() {
        foodItemsLayout = (LinearLayout) findViewById(R.id.layoutCalories);
    }

    private void setCurrentDate(){
        //myCalendarDate = (TextView)findViewById(R.id.bodyWeightDate);
        Calendar newCalendar = Calendar.getInstance();
        int currentYear = newCalendar.get(Calendar.YEAR);
        currentMonth = newCalendar.get(Calendar.MONTH) + 1;
        int currentDay = newCalendar.get(Calendar.DAY_OF_MONTH);
        currentDate = String.valueOf(currentDay) + "/" + String.valueOf(currentMonth) + "/" + String.valueOf(currentYear);
        //myCalendarDate.setText(currentDate);
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
            if (i < (maxFoodCount - 1)) {
                foodItemTV.setLayoutParams(params1);
            }
            else {
                foodItemTV.setLayoutParams(params2);
            }
            foodItemTV.setPadding(40,40,40,40);
            foodItemTV.setTextColor(Color.parseColor("#000000"));
            foodItemTV.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size));
            foodItemTV.setTypeface(Typeface.create("sans-serif-thin", Typeface.NORMAL));
            foodItemTV.setId(ViewCompat.generateViewId());
            this.foodItemTVIds[i] = foodItemTV.getId();
            foodItemTV.setVisibility(View.GONE);
            foodItemsLayout.addView(foodItemTV, i + 3);
        }
    }

    private void setFoodItemTextViews(){
        ArrayList<String> foodForDate = caloriesModel.getFoodCalories(currentDate);
        if (foodForDate == null) {
            this.foodCount = 0;
            return;
        }
        this.foodCount = foodForDate.size();
        if (this.foodCount > 0) {
            for (int i = 0; i < foodCount; i++) {
                TextView foodItemTV = (TextView) findViewById(this.foodItemTVIds[i]);
                foodItemTV.setText(foodForDate.get(i));
                foodItemTV.setVisibility(View.VISIBLE);
            }
        }
        this.hideUnusedFoodItemTextViews();
    }

    private void hideUnusedFoodItemTextViews(){
        if (this.foodCount == maxFoodCount) {
            return;
        }
        for (int i = foodCount; i < maxFoodCount; i++) {
            TextView foodItemTV = (TextView) findViewById(this.foodItemTVIds[i]);
            foodItemTV.setText("");
            foodItemTV.setVisibility(View.GONE);
        }
    }

    private void setEnterFoodButtonListener(){
        enterFoodButton = (Button) findViewById(R.id.enterFoodButton);
        enterFoodButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String food = foodInputTextField.getText().toString();
                String calories = caloriesInputTextField.getText().toString();
                if (food.isEmpty() || calories.isEmpty() || foodCount == maxFoodCount){
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

}
