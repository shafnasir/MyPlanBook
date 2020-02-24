package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileSetting extends AppCompatActivity {

    private Button Confirm;
    SharedPreferences sharedPreferences;

    private EditText in_Name;
    String NameKey = "Name";

    private EditText in_Account;
    String AccountKey = "Account";

    private EditText in_Email;
    String EmailKey = "Email";

    private EditText in_Phone;
    String PhoneKey = "Phone";

    private EditText in_City;
    String CityKey = "City";
    //Profile profile = new Profile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        Confirm = findViewById(R.id.confirm);
        in_Name = findViewById(R.id.EditName);
        in_Account = findViewById(R.id.EditAccount);
        in_Email = findViewById(R.id.EditEmail);
        in_Phone = findViewById(R.id.EditPhone);
        in_City = findViewById(R.id.EditCity);

        Confirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickConfirm(v);
            }
        });
   }

   public void clickConfirm(View v){
        if (v.getId() == Confirm.getId()){
            //Toast.makeText(ProfileSetting.this,"Hey, value you entered is saved", Toast.LENGTH_SHORT).show();
            //Intent intent =new Intent(ProfileSetting.this,Profile.class);
            //intent.putExtra("value",in_Account.getText().toString());
            //startActivity(intent);
            saveInformation(v);
        }

   }

    public void saveInformation(View v){
        sharedPreferences = getSharedPreferences("ID", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String NameText = in_Name.getText().toString();
        String AccountText = in_Account.getText().toString();
        String EmailText = in_Email.getText().toString();
        String PhoneText = in_Phone.getText().toString();
        String CityText = in_City.getText().toString();

        editor.putString(NameKey,NameText);
        editor.putString(AccountKey,AccountText);
        editor.putString(EmailKey,EmailText);
        editor.putString(PhoneKey,PhoneText);
        editor.putString(CityKey,CityText);

        editor.commit();
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
        //AccountText = sharedPreferences.getString(AccountKey, AccountKey);

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //SharedPreferences.Editor editor = preferences.edit();
        //editor.putString(key, value);
        //editor.commit();
        Toast.makeText(ProfileSetting.this,"New Information Saved!!", Toast.LENGTH_SHORT).show();
    }
}
