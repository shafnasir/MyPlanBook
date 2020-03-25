package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class YearlySavings extends AppCompatActivity {

    private Button enter, change;
    private String userName, amount, spend, gain;
    FirebaseFirestore db;
    private EditText amountText, spendText, gainText;
    int spendInt, gainInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_savings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enter = findViewById(R.id.enterYearlyButton);
        change = findViewById(R.id.changeYearlyButton);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userName = user.getUid();
        db = FirebaseFirestore.getInstance();

        amountText = findViewById(R.id.amountText);
        gainText = findViewById(R.id.gainedYearlyText);
        spendText = findViewById(R.id.spendYearlyText);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    updateDBGoal(amountText.getText().toString().trim());
                }catch (Exception e){
                    Toast.makeText(YearlySavings.this, "Please input a number for the amount.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputs();
            }
        });





    }

    public void updateDBGoal(String amount){
        //Update databse
        if(Integer.parseInt(amount) < 0){
            Toast.makeText(YearlySavings.this, "Please input a positive number.", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void checkInputs(){

        spend = spendText.getText().toString().trim();
        try{
            if(spend.equals("") || spend == null){
                spendInt = 0;
            }else {
                spendInt = Integer.parseInt(spend);
            }
        }catch (Exception e) {
            Toast.makeText(YearlySavings.this, "Please input a number for the spending.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(spendInt < 0){
            Toast.makeText(YearlySavings.this, "Please input a positive number for the spending.", Toast.LENGTH_SHORT).show();
            return;
        }

        gain = gainText.getText().toString().trim();
        try{
            if(gain.equals("") || gain == null){
                gainInt = 0;
            }else{
                gainInt = Integer.parseInt(gain);
            }

        }catch (Exception e) {
            Toast.makeText(YearlySavings.this, "Please input a number for the gained.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(gainInt < 0){
            Toast.makeText(YearlySavings.this, "Please input a positive number for the gained.", Toast.LENGTH_SHORT).show();
            return;
        }
    }



}
