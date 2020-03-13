package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialHubActivity;
import utm.csc301.theBrogrammers.myPlanBook.HealthFitnessMainMenu.HealthFitnessMainMenuActivity;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.LogBodyWeightActivity;
import utm.csc301.theBrogrammers.myPlanBook.calendar.CalendarEventsActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private Button cal, goals, fitness;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.setting);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickbutton();
            }
        });

        cal = findViewById(R.id.calendar);
        cal.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CalendarEventsActivity.class);
                startActivity(intent);

            }

        });

        goals = findViewById(R.id.goals);
        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinancialHubActivity.class);
                startActivity(intent);
            }
        });

        fitness = findViewById(R.id.fitness);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HealthFitnessMainMenuActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clickbutton() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}