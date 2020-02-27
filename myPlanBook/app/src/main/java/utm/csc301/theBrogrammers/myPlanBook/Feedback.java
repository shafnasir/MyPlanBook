package utm.csc301.theBrogrammers.myPlanBook;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class Feedback extends AppCompatActivity {
    EditText input;
    Button submit,clear;
    String feedback;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //Toast.makeText(this, "Firebase connection successed", Toast.LENGTH_SHORT).show();

        input = findViewById(R.id.feedbackinput);
        submit = findViewById(R.id.submitbutton);
        clear = findViewById(R.id.clearbutton);
        reff = FirebaseDatabase.getInstance().getReference().child("Feedback");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save text from EditText
                feedback = input.getText().toString().trim();
                //only act when user entered something
                if (feedback.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please provide your feedback",Toast.LENGTH_SHORT).show();
                }else{
                    //for now just copy the text
                    //ClipboardManager  clipb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    //ClipData clipdata = ClipData.newPlainText("Feedback", feedback);
                   // clipb.setPrimaryClip(clipdata);
                    //Toast.makeText(getApplicationContext(),"Feedback copied",Toast.LENGTH_SHORT).show();
                    reff.push().setValue(feedback);
                    Toast.makeText(getApplicationContext(),"We have received your feedback",Toast.LENGTH_SHORT).show();
                    input.setText("");

                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (text.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Already empty", Toast.LENGTH_SHORT).show();
                }else{
                    input.setText("");
                }
            }
        });

    }
}
