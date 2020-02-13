package utm.csc301.theBrogrammers.myPlanBook.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class Signup extends AppCompatActivity {

    private String firstName, lastName, username, password, cellphone, email;
    private EditText lastnameInput, usernameInput, passwordInput, firstnameInput, emailInput, cellphoneInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button done = findViewById(R.id.signupDoneButton);
        firstnameInput = findViewById(R.id.firstNameInput);
        lastnameInput = findViewById(R.id.lastNameInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        emailInput = findViewById(R.id.emailInput);
        cellphoneInput = findViewById(R.id.cellInput);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAllFilled();
            }
        });


    }

    public void checkAllFilled(){

        firstName = firstnameInput.getText().toString();
        lastName = lastnameInput.getText().toString();
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString();
        email = emailInput.getText().toString();
        cellphone = cellphoneInput.getText().toString();

        if(firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("") || email.equals("") || cellphone.equals("")){
            Toast.makeText(Signup.this, "One or more fields needs to be filled in", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(Signup.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
