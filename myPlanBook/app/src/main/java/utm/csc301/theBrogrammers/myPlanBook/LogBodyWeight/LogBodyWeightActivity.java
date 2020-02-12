package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LogBodyWeightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_body_weight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayout bodyWeights = (LinearLayout) findViewById(R.id.layoutBodyWeights);
        TextView view = new TextView(this);
        view.setText("Test");
        view.setTextColor(0x000000);
        view.setTextSize(36f);
        bodyWeights.addView(view);
        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //
    }

}
