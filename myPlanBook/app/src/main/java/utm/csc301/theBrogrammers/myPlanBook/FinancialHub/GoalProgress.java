package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class GoalProgress extends AppCompatActivity {

    private Button enter, change;
    private EditText goalText, priceText, gainText, spendText;
    private TextView progressText;
    private String goal, price, gain, spend;
    private float goal_int = 1;
    private float add = 0;
    private float sub = 0;
    private float currently_at = 0;
    private ProgressBar bar;
    float set = 0;
    Drawable bgDrawable;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bar = findViewById(R.id.progressBar);
        bgDrawable = bar.getProgressDrawable();
        bgDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
        bar.setProgressDrawable(bgDrawable);
        bar.setMax(100);

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
                update_progress();
            }
        });



    }

    public void add_goal(){
        goalText = findViewById(R.id.goalText);
        priceText = findViewById(R.id.priceText);
        goal = goalText.getText().toString().trim();
        price = priceText.getText().toString().trim();
        //db.child("Goal").setValue(goal);
        //db.child(goal).child("Price").setValue(price);
        Log.i("price", price);
        try {
            goal_int = Float.parseFloat(price);
            set = (currently_at/goal_int) * 100;
            Log.i("goal_int", Float.toString(goal_int));
        }catch(Exception e){

        }
    }

    public void update_progress(){

        Log.i("goal_int 2", Float.toString(goal_int));

        spendText = findViewById(R.id.spendText);
        gainText = findViewById(R.id.gainedText);
        progressText = findViewById(R.id.progressText);


        if(goal_int >= 0){
            spend = spendText.getText().toString().trim();
            gain = gainText.getText().toString().trim();
            Log.i("spend", spend);
            Log.i("gain", gain);
            if(spend != null || !spend.equals("")){
                sub = Float.parseFloat(spend);
                //Log.i("sub", Integer.toString(sub));
            }
            if(gain != null || !gain.equals("")){
                add = Float.parseFloat(gain);
                //Log.i("add", Integer.toString(add));
            }

            currently_at += add-sub;
            set = (currently_at/goal_int) * 100;
            Log.i("current", Float.toString(currently_at));
            Log.i("add", Float.toString(add));
            Log.i("sub", Float.toString(sub));
            Log.i("goal_int", Float.toString(goal_int));
            Log.i("add-sub", Float.toString((add-sub)/goal_int));
            Log.i("set", Float.toString(set));
            Log.i("set", Integer.toString((int)set));
            if(set > 100){
                set = 100;
                currently_at = 100;
            }else if(currently_at < 0){
                set = 0;
                bgDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.MULTIPLY);
                bar.setProgressDrawable(bgDrawable);
            }else{
                bgDrawable.setColorFilter(Color.GREEN, android.graphics.PorterDuff.Mode.MULTIPLY);
                bar.setProgressDrawable(bgDrawable);
            }
            //db.child(goal).child("CurrentProgress").setValue(currently_at);
            progressText.setText("Progress: $" + currently_at + " (" + set + "%)");
            ObjectAnimator.ofInt(bar, "progress", (int)set).setDuration(300).start();
        }else{
            //Tell user to enter goal
        }
    }


}