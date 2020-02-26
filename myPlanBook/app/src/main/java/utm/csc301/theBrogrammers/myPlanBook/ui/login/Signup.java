package utm.csc301.theBrogrammers.myPlanBook.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class Signup extends AppCompatActivity {

    private String firstName, lastName, username, password, cellphone, email;
    private EditText lastnameInput, usernameInput, passwordInput, firstnameInput, emailInput, cellphoneInput;
    private FirebaseAuth mAuth;
    DatabaseReference ref;
    FirebaseDatabase db;

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

        mAuth = FirebaseAuth.getInstance();


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAllFilled()){
                    System.out.println(email);
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(firstName, lastName, username, password, email, cellphone);
                                Toast.makeText(Signup.this, "Registration Complete", Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(Signup.this, "Registration not Complete", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Intent intent = new Intent(Signup.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

    public boolean checkAllFilled(){

        firstName = firstnameInput.getText().toString().trim();
        lastName = lastnameInput.getText().toString();
        username = usernameInput.getText().toString();
        password = passwordInput.getText().toString().trim();
        email = emailInput.getText().toString().trim();
        cellphone = cellphoneInput.getText().toString();
        //firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("") || email.equals("") || cellphone.equals("")
        if(false){
            Toast.makeText(Signup.this, "One or more fields needs to be filled in", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
