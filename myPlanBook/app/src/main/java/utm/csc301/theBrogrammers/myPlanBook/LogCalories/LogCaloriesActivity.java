package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.ArrayList;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogCaloriesActivity extends AppCompatActivity {

    private LinearLayout.LayoutParams params1;
    private LinearLayout.LayoutParams params2;
    private EditText foodInputTextField;
    private EditText caloriesInputTextField;
    private CaloriesModel caloriesModel;
    private int currentMonth;
    private String currentDate;
    private TextView foodCaloriesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_calories);
        this.assignParams();
        this.setCurrentDate();
        this.setFoodInputTextField();
        this.setCaloriesInputTextField();
        caloriesModel = new CaloriesModel();
        this.setFoodItemTextViews();
    }

    private void assignParams(){
        params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params2 = new LinearLayout.LayoutParams(
                875,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(45,45,45,0);
        params2.setMargins(45,45,45,0);
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
        foodInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void setCaloriesInputTextField(){
        caloriesInputTextField = findViewById(R.id.inputCaloriesEditText);
        caloriesInputTextField.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    private void setFoodItemTextViews(){
        ArrayList<String> foodForDate = caloriesModel.getFoodCalories(currentDate);
        foodCaloriesTextView = (TextView)findViewById(R.id.foodCaloriesTextView);
        String foodItem = (foodForDate == null)? "": foodForDate.get(0);
        foodCaloriesTextView.setText(foodItem);
        int foodCount = foodForDate.size();
        if (foodForDate != null && foodCount > 1) {
            for (int i = 1; i < foodCount; i++) {
                
            }
        }
    }

}
