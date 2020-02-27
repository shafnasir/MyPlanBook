package utm.csc301.theBrogrammers.myPlanBook.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class MainLoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private String sign, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.EmailInput);
        passwordInput = findViewById(R.id.PasswordInput);
        Button login = findViewById(R.id.LoginButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                valid(username, password);

            }
        });


    }
    public void valid(String username, String password){

        System.out.println("here " + sign);
        if(username.equals("login") && password.equals("pass") || username.equals(sign) && password.equals(pass)){
            Intent intent = new Intent(MainLoginActivity.this, PlannerSelector.class);
            startActivity(intent);
        }else if(username.equals(sign) && password.equals(pass) && username != null && username != null){
            Intent intent = new Intent(MainLoginActivity.this, PlannerSelector.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainLoginActivity.this, sign, Toast.LENGTH_SHORT).show();
        }

    }

}
