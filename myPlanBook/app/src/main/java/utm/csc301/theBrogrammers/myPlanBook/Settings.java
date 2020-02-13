package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import utm.csc301.theBrogrammers.myPlanBook.ui.login.LoginActivity;

public class Settings extends AppCompatActivity {

    private Button button;
    private ImageButton LogoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        button = findViewById(R.id.Change_Password);
        button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickbutton();
            }
        });

        LogoutButton = findViewById(R.id.Log_out);
        LogoutButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickLogout();
            }
        });

    }

    public void clickbutton() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    public void clickLogout(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}