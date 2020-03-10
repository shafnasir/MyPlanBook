package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WishList extends AppCompatActivity {

    private TextView wishes;
    private EditText in_wish;
    private Button Add;
    private Button Delete;
    private Button Share;
    SharedPreferences sharedPreferences;
    private String WishKey = "Wishes";
    private String out_wish = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        wishes = findViewById(R.id.wishes);
        in_wish = findViewById(R.id.WishText);
        Add = findViewById(R.id.Add);
        Delete = findViewById(R.id.Delete);
        Share = findViewById(R.id.Share);

        sharedPreferences = getSharedPreferences("WISH", 0);
        String n = sharedPreferences.getString(WishKey , "");
        wishes.setText(n);
        wishes.setMovementMethod(new ScrollingMovementMethod());
        //String n = in_wish.getText().toString();
        Add.setEnabled(false);

        in_wish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String UserInput = in_wish.getText().toString().trim();
                Add.setEnabled(!UserInput.isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String UserInput = in_wish.getText().toString().trim();
                Add.setEnabled(!UserInput.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SaveWish(v);
            }
        });

        Delete.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DeleteWish(v);
            }
        });

        Share.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "Please Complete my Wish";
                String shareSub = n;
                intent.putExtra(intent.EXTRA_SUBJECT, shareBody);
                intent.putExtra(intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
    }

    public void SaveWish(View v){
        sharedPreferences = getSharedPreferences("WISH", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String new_wish = in_wish.getText().toString();
        String store_string = sharedPreferences.getString(WishKey , "") + new_wish +" \n";
        editor.putString(WishKey,store_string);
        editor.commit();
        Toast.makeText(WishList.this,new_wish +" Saved!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }

    public void DeleteWish(View v){
        sharedPreferences = getSharedPreferences("WISH", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(WishKey);
        editor.commit();
        Toast.makeText(WishList.this,"All Wishes Deleted :(", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WishList.class);
        startActivity(intent);
    }
}
