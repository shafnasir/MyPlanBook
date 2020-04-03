package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class GoalProgress extends AppCompatActivity {

    private Button enter, change, switchToYearly;
    private EditText goalText, priceText, gainText, spendText;
    private TextView progressText;
    private String goal_name, price, gain, spend, user_email;
    private float goal_int = 1;
    private float add = 0;
    private float sub = 0;
    private float currently_at = 0;
    private ProgressBar bar;
    float set = 0;
    Drawable bgDrawable;
    DatabaseReference db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting up the progress bar
        bar = findViewById(R.id.progressBar);
        bgDrawable = bar.getProgressDrawable();
        bgDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
        bar.setProgressDrawable(bgDrawable);
        bar.setMax(100);

        goalText = findViewById(R.id.goalText);
        priceText = findViewById(R.id.priceText);
        progressText = findViewById(R.id.progressText);

        //Get User ID, add child to "Goals" to distinguish between users
        user = FirebaseAuth.getInstance().getCurrentUser();
        user_email = user.getUid();

        db = FirebaseDatabase.getInstance().getReference().child("Goals").child(user_email);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount() < 3){
                    db.child("currently_at").setValue("0");
                    db.child("set").setValue("0");
                    return;
                    
                }

                goal_name = dataSnapshot.child("goal_name").getValue().toString();
                price = dataSnapshot.child("price").getValue().toString();
                currently_at = Float.parseFloat(dataSnapshot.child("currently_at").getValue().toString());
                set = Float.parseFloat(dataSnapshot.child("set").getValue().toString());
                try {
                    goal_int = Float.parseFloat(price);
                    update_bar(currently_at, set);
                    if(goal_name != null && price != null){
                        priceText.setText(price);
                        goalText.setText(goal_name);
                        update_bar(currently_at, set);
                    }
                }catch (Exception e){
                    Toast.makeText(GoalProgress.this, "Please input a number for price", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        enter = findViewById(R.id.enterButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_goal();
            }
        });

        change = findViewById(R.id.changeButton);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spend = "";
                gain = "";
                update_progress();
            }
        });



    }

    public void add_goal(){

        goal_name = goalText.getText().toString().trim();

        try {
            goal_int = Float.parseFloat(priceText.getText().toString().trim());
            price = priceText.getText().toString().trim();
            if(goal_int < 0){
                Toast.makeText(GoalProgress.this, "Please input a positive price", Toast.LENGTH_SHORT).show();
                return;
            }

            set = (currently_at/goal_int) * 100;
            update_database(goal_name, price);
        }catch(Exception e){
            Toast.makeText(GoalProgress.this, "Please input a number for price", Toast.LENGTH_SHORT).show();
        }
    }

    public void update_progress(){

        spendText = findViewById(R.id.spendText);
        gainText = findViewById(R.id.gainedText);


        if(goal_int >= 0){

            if(spendText == null || spendText.getText().toString().equals("")){
                sub = 0;
                spend = "0";
            }else{
                try{
                    spend = spendText.getText().toString().trim();
                    sub = Float.parseFloat(spend);
                }catch (Exception e){
                    Toast.makeText(GoalProgress.this, "Please input a number for spend", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(gainText == null || gainText.getText().toString().equals("")){
                add = 0;
                gain = "0";
            }else{
                try{
                    gain = gainText.getText().toString().trim();
                    add = Float.parseFloat(gain);
                }catch (Exception e){
                    Toast.makeText(GoalProgress.this, "Please input a number for gain", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            currently_at += add-sub;
            set = (currently_at/goal_int) * 100;


            update_database(goal_name, price, Float.toString(currently_at), Float.toString(set));
            update_bar(currently_at, set);
            spend = "";
            gain = "";
            spendText = null;
            gainText = null;
        }else{
            Toast.makeText(GoalProgress.this, "Please input a positive price", Toast.LENGTH_SHORT).show();
        }


    }

    public void update_bar(float currently_at, float set){

        progressText = findViewById(R.id.progressText);
        if(set > 100){
            set = 100;
            //currently_at = 100;
        }else if(currently_at < 0){
            set = 0;
            bgDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.MULTIPLY);
            bar.setProgressDrawable(bgDrawable);
        }else{
            bgDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
            bar.setProgressDrawable(bgDrawable);
        }

        progressText.setText("Progress: $" + currently_at + " (" + set + "%)");
        ObjectAnimator.ofInt(bar, "progress", (int)set).setDuration(300).start();

    }

    public void update_database(String goal_name, String price){

        db.child("goal_name").setValue(goal_name);
        db.child("price").setValue(price);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Float temp_current = Float.parseFloat(dataSnapshot.child("currently_at").getValue().toString());
                if(temp_current == 0){
                    db.child("currently_at").setValue("0");
                    db.child("set").setValue("0");
                }else{
                    set = (temp_current/Float.parseFloat(price)) *100;
                    db.child("set").setValue(set);
                    update_bar(temp_current, set);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void update_database(String goal_name, String price, String currently_at, String set){
        db.child("goal_name").setValue(goal_name);
        db.child("price").setValue(price);
        db.child("currently_at").setValue(currently_at);
        db.child("set").setValue(set);
    }

    public void saved_database(){

    }


}