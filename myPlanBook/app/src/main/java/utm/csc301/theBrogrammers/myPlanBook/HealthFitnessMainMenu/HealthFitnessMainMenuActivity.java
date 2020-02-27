package utm.csc301.theBrogrammers.myPlanBook.HealthFitnessMainMenu;

import androidx.appcompat.app.AppCompatActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.LogBodyWeightActivity;
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
        logWorkoutsLink = (Button) findViewById(R.id.logWorkoutsLinkButton);
        logBodyFatLink = (Button) findViewById(R.id.logBodyFatLinkButton);
    }
}
