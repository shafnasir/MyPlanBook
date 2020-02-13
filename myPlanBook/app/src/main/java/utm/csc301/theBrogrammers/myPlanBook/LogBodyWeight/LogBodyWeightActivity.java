package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

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

        LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
        weightsScrollLayout.setBackgroundColor(Color.TRANSPARENT);

        Button enterWeightButton = (Button) findViewById(R.id.enterWeightButton);
        enterWeightButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText inputWeightEditText = (EditText) findViewById(R.id.inputWeightEditText);
                ToggleButton toggleUnitsButton = (ToggleButton) findViewById(R.id.toggleUnitsButton);

                double weight = Double.parseDouble(inputWeightEditText.getText().toString());
                String units = toggleUnitsButton.getText().toString();

                if (weight > 10000) {
                    return;
                }

                if (units.equals("LBS")) {
                    TextView newBodyWeight = new TextView(LogBodyWeightActivity.this);
                    String weightInLbsKg = String.format("%.1f", weight)+ " lbs: " + String.format("%.1f", weight*(1/2.20462)) + " kg";
                    newBodyWeight.setText(weightInLbsKg);
                    newBodyWeight.setTextColor(Color.parseColor("#000000"));
                    newBodyWeight.setTextSize(30);
                    newBodyWeight.setGravity(Gravity.CENTER);
                    newBodyWeight.setBackgroundColor(Color.parseColor("#B4FC5B"));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);

                    newBodyWeight.setLayoutParams(params);

                    LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
                    weightsScrollLayout.addView(newBodyWeight);
                }
                else {
                    TextView newBodyWeight = new TextView(LogBodyWeightActivity.this);
                    String weightInLbsKg = String.format("%.1f", weight*(2.20462))+ " lbs: " + String.format("%.1f", weight) + " kg";
                    newBodyWeight.setText(weightInLbsKg);
                    newBodyWeight.setTextColor(Color.parseColor("#000000"));
                    newBodyWeight.setTextSize(30);
                    newBodyWeight.setGravity(Gravity.CENTER);
                    newBodyWeight.setBackgroundColor(Color.parseColor("#B4FC5B"));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10,10,10,10);

                    newBodyWeight.setLayoutParams(params);

                    LinearLayout weightsScrollLayout = (LinearLayout) findViewById(R.id.weightsScrollLayout);
                    weightsScrollLayout.addView(newBodyWeight);
                }
            }
        });

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
