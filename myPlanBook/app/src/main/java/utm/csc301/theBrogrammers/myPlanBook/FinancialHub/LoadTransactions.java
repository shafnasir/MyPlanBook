package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class LoadTransactions extends AppCompatActivity {

    private static final int GET_CSV_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_transactions);

    }

    private void openFileExplorer(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/comma-separated-values"); // open all types of plain text
        startActivityForResult(intent, GET_CSV_FILE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == GET_CSV_FILE
                && resultCode == LoadTransactions.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                readFile(uri);
            }
        }
    }

    private void readFile(Uri uri)  {

        try {
            FinanceModel financeModel = new FinanceModel();
            InputStream inputStream =
                     getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)));
            TransactionCollection tc = ReadCSV.parse(reader);
            FinanceModel.loadMonthlyCollection(tc.toMonthlyCollection());
            Toast.makeText(LoadTransactions.this,
                    "Imported " + tc.length() + " transactions from . Check \"Manage Transactions" +
                           "\" page to view them.",
                    Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(LoadTransactions.this,
                    "Error uploading transactions. Corrupted CSV file and/or format is incorrect.",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void fireFileExplorer(View v){
        openFileExplorer();
    }

    public void switchToManualLoadTransaction(View v){
        Intent myIntent = new Intent(getApplicationContext(),  ManuallyLoadTransactions.class);
        startActivity(myIntent);
    }
}
