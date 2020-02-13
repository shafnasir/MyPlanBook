package utm.csc301.theBrogrammers.myPlanBook.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class PlannerSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_selector);

        Button back = findViewById(R.id.backToLoginButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlannerSelector.this, MainLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}