package utm.csc301.theBrogrammers.myPlanBook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private Button ChangeInformation;
    private Button BackProfile;
    private ImageView User;
    private TextView out_Name;
    private TextView out_Account;
    private TextView out_Email;
    private TextView out_Phone;
    private TextView out_City;

    SharedPreferences sharedPreferences;
    String NameText;
    String AccountText;
    String EmailText;
    String PhoneText;
    String CityText;

    String NameKey = "Name";
    String AccountKey = "Account";
    String EmailKey = "Email";
    String PhoneKey = "Phone";
    String CityKey = "City";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ChangeInformation = findViewById(R.id.ChangeInformation);
        ChangeInformation.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                clickInformation();
            }
        });

        out_Name = findViewById(R.id.Name);
        out_Account = findViewById(R.id.Account);
        out_Email = findViewById(R.id.Email);
        out_Phone = findViewById(R.id.Phone);
        out_City = findViewById(R.id.City);
        BackProfile = findViewById(R.id.backProfile);

        //AccountText=getIntent().getStringExtra("Account");
        sharedPreferences = getSharedPreferences("ID", 0);
        NameText = sharedPreferences.getString(NameKey, NameKey);
        AccountText = sharedPreferences.getString(AccountKey, AccountKey);
        EmailText = sharedPreferences.getString(EmailKey,EmailKey);
        PhoneText = sharedPreferences.getString(PhoneKey,PhoneKey);
        CityText = sharedPreferences.getString(CityKey,CityKey);
        //Toast.makeText(Profile.this,AccountText, Toast.LENGTH_SHORT).show();
        out_Name.setText(NameText);
        out_Account.setText(AccountText);
        out_Email.setText(EmailText);
        out_Phone.setText(PhoneText);
        out_City.setText(CityText);

        User = findViewById(R.id.User);
        User.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        BackProfile.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                backProfile(v);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            User.setImageURI(selectedImage);
            //Toast.makeText(Profile.this,AccountText, Toast.LENGTH_SHORT).show();
        }
    }

    public void clickInformation() {
        Intent intent = new Intent(this, ProfileSetting.class);
        startActivity(intent);
    }

    private void backProfile(View v){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }


}
