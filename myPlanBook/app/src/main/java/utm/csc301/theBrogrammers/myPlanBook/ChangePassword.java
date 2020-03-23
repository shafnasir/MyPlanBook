package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends AppCompatActivity {
    EditText oldpass, newpass, confirmpass;
    Button button, BackPassword;
    String oldpassword, newpassword, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        oldpass = findViewById(R.id.oldpassword);
        newpass = findViewById(R.id.newpassword);
        confirmpass = findViewById(R.id.confirmpassword);
        button = findViewById(R.id.confirmbutton);
        newpassword = newpass.getText().toString();
        oldpassword = oldpass.getText().toString();
        confirmpassword = confirmpass.getText().toString();
        oldpass.addTextChangedListener(passwordTextWatcher);
        newpass.addTextChangedListener(passwordTextWatcher);
        confirmpass.addTextChangedListener(passwordTextWatcher);

                button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConfirmNewPassword()){
                   DatabasePaswordChange();
                };

            }
        });

        BackPassword = findViewById(R.id.backPassword);
        BackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backPassword(v);
            }
        });

    }

    private void backPassword(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newpassword = newpass.getText().toString();
            oldpassword = oldpass.getText().toString();
            confirmpassword = confirmpass.getText().toString();
            button.setEnabled(!newpassword.isEmpty() && !oldpassword.isEmpty() && !confirmpassword.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    public boolean ConfirmNewPassword(){
        if(newpassword.equals(confirmpassword)){
            return true;
        }else{
            Toast.makeText(getApplicationContext(),"Please confirm your new password",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void DatabasePaswordChange(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldpassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Password Not Changed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Old password not correct.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
