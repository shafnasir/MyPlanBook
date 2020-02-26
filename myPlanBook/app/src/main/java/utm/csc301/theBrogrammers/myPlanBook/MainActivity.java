package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.LogBodyWeightActivity;
import utm.csc301.theBrogrammers.myPlanBook.calendar.Calendar;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;
    private Button cal, goals, fitness;
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

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //"https://myplanbook-a1467.firebaseio.com/"
        DatabaseReference ref = db.getReference("myplanbook-a1467");
        System.out.println("After db");
        ref.setValue("Hello").addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("here");
            }
        });

        cal = findViewById(R.id.calendar);
        cal.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Calendar.class);
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
                Intent intent = new Intent(MainActivity.this, LogBodyWeightActivity.class);
                startActivity(intent);
            }
        });
    }

    public void clickbutton() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}