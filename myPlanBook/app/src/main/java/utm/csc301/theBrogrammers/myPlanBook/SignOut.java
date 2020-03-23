package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import utm.csc301.theBrogrammers.myPlanBook.ui.login.LoginActivity;

public class SignOut  extends AppCompatActivity {
    private ImageButton button;
    private FirebaseAuth FireLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FireLogout = FirebaseAuth.getInstance();

        button = findViewById(R.id.Log_out);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickLogout();
            }
        });
    }

    public void clickLogout() {
        FireLogout.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

