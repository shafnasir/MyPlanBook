package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.FirebaseApiNotAvailableException;


import java.util.ArrayList;
import java.util.Random;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.LogBodyWeightActivity;
import utm.csc301.theBrogrammers.myPlanBook.MainActivity;
import utm.csc301.theBrogrammers.myPlanBook.R;
import utm.csc301.theBrogrammers.myPlanBook.R.drawable;

public class FinancialHubActivity extends AppCompatActivity {

     final String[] monthStrings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
             "Aug", "Sep", "Oct", "Nov", "Dec"};
     int lastly; // the last month's expenditure - for demo purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_hub);

    }

    public void switchToImportScreen(View v){
        Intent myIntent = new Intent(getApplicationContext(), LoadTransactions.class);
        startActivity(myIntent);
    }

    public void switchToGoalsScreen(View v){
        Intent intent = new Intent(getApplicationContext(), GoalProgress.class);
        startActivity(intent);
    }

    public void switchToManageFinancesScreen(View v){
        Intent intent = new Intent(getApplicationContext(), ManageFinances.class);
        startActivity(intent);
    }
}