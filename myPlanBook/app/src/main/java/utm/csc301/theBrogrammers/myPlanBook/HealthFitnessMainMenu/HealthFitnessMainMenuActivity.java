package utm.csc301.theBrogrammers.myPlanBook.HealthFitnessMainMenu;

import androidx.appcompat.app.AppCompatActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyFat.LogBodyFatActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.LogBodyWeightActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogCalories.LogCaloriesActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogWorkouts.LogWorkoutsActivity;
import utm.csc301.theBrogrammers.myPlanBook.MainActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;
import utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HealthFitnessMainMenuActivity extends AppCompatActivity {

    Button logBodyWeightsLink;
    Button logCaloriesLink;
    Button logWorkoutsLink;
    Button logBodyFatLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_fitness_main_menu);

        logBodyWeightsLink = (Button) findViewById(R.id.logBodyWeightLinkButton);
        logBodyWeightsLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(HealthFitnessMainMenuActivity.this, LogBodyWeightActivity.class);
                startActivity(intent);
            }
        });

        logCaloriesLink = (Button) findViewById(R.id.logCaloriesLinkButton);
        logCaloriesLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(HealthFitnessMainMenuActivity.this, LogCaloriesActivity.class);
                startActivity(intent);
            }
        });

        logWorkoutsLink = (Button) findViewById(R.id.logWorkoutsLinkButton);
        logWorkoutsLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(HealthFitnessMainMenuActivity.this, LogWorkoutsActivity.class);
                startActivity(intent);
            }
        });

        logBodyFatLink = (Button) findViewById(R.id.logBodyFatLinkButton);
        logBodyFatLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(HealthFitnessMainMenuActivity.this, LogBodyFatActivity.class);
                startActivity(intent);
            }
        });
    }
}
