package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import utm.csc301.theBrogrammers.myPlanBook.ui.login.LoginActivity;

public class Settings extends AppCompatActivity {

    private Button changepass, feedback, notificationdemo;
    private ImageButton ProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        changepass = findViewById(R.id.Change_Password);
        changepass.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickchangepass();
            }
        });


        feedback = findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickfeedback();
            }
        });

        ProfileButton = findViewById(R.id.Profile);
        ProfileButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickProfile();
            }
        });

        notificationdemo = findViewById(R.id.button4);
        notificationdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNotificationDemo();
            }
        });

    }

    public void clickchangepass() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }


    public void clickfeedback(){
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }

    public void clickProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void clickNotificationDemo(){
        Intent intent = new Intent(this, Notification_Demo.class);
        startActivity(intent);
    }
}