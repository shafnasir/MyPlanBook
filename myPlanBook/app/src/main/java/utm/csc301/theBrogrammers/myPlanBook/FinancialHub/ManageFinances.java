package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.MonthlyPieChart;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.TransactionPieChart;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.MonthlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManageFinances extends AppCompatActivity {

    static final String ROOT = "Finances";
    static final List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    // Data
    YearlyTransactions yearlyTransactions;

    // Graphics
    TransactionPieChart pieChart;
    MonthlyPieChart monthlyPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_finances);

        initializeData(); // so we don't block on the database retrieval
        initializeGraphics(); // display graphics

        refreshGraphics();
        refreshData(); // Load in all banking information
    }

    private void initializeData(){
        this.yearlyTransactions = new YearlyTransactions();
    }

    private void initializeGraphics(){
        pieChart = new TransactionPieChart(findViewById(R.id.categoryPieChart));
        monthlyPieChart = new MonthlyPieChart(findViewById(R.id.monthlyPieChart));

    }

    // Event driven basis
    private void refreshGraphics(){
        this.pieChart.refresh(this.yearlyTransactions);
        this.monthlyPieChart.refresh(this.yearlyTransactions);

    }

    public void refreshData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int index = 0;
        for (String month: months) {
            CollectionReference collectionReference = db.collection(ROOT).
                    document(userName).collection(month);
            if (index == 11){
                refreshMonthlyTransactions(month, collectionReference.get(), true);
            } else refreshMonthlyTransactions(month, collectionReference.get(), false);
            index++;
        }


    }



    private void refreshMonthlyTransactions(String month, Task<QuerySnapshot> task, boolean breaker){
        MonthlyTransactions mt = new MonthlyTransactions(month);
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        BankTransaction bt = new BankTransaction(document.getData());
                        mt.addTransaction(bt);

                    }
                    yearlyTransactions.replaceMonth(mt);
                    if (breaker) {refreshGraphics();}

                } else {
                    Log.i("[RefreshData]", "Error loading documents.");
                }

            }
        });
    }




}
