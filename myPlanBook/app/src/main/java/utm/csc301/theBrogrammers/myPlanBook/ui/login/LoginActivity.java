package utm.csc301.theBrogrammers.myPlanBook.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utm.csc301.theBrogrammers.myPlanBook.MainActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private EditText usernameInput;
    private EditText passwordInput;
    private String sign, pass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);


        usernameInput = findViewById(R.id.EmailInput);
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

                Intent intent = new Intent(LoginActivity.this, Signup.class);
                startActivity(intent);
            }
        });


    }

    public void valid(String username, String password){

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Email/password is incorrect.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}
