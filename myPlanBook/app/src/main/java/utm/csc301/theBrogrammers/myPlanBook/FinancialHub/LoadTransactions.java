package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class LoadTransactions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_transactions);
        test();
    }

    public void test(){
        File root = Environment.getRootDirectory();
        File[] folders = root.listFiles();
        for (File f: folders){
            Log.i("--------Folder", "File name: "+ f.getName());

        }

    }
}
