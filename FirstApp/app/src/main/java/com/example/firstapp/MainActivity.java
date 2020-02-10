package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private String sign, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.UsernameInput);
        passwordInput = findViewById(R.id.PasswordInput);
        Button login = findViewById(R.id.LoginButton);
        Button signup = findViewById(R.id.SignupButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                valid(username, password);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

    }

    public void valid(String username, String password){

        Signup s = new Signup();
        sign = s.getUsername();
        pass = s.getPassword();
        System.out.println("here " + sign);
        if(username.equals("login") && password.equals("pass") || username.equals(sign) && password.equals(pass)){
            Intent intent = new Intent(MainActivity.this, PlannerSelector.class);
            startActivity(intent);
        }else if(username.equals(sign) && password.equals(pass) && username != null && username != null){
            Intent intent = new Intent(MainActivity.this, PlannerSelector.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, sign, Toast.LENGTH_SHORT).show();
        }

    }

}
