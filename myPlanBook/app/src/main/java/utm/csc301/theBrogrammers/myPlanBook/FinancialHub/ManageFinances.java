package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.CreditLineGraph;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.DebitLineGraph;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.MonthlyPieChart;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics.TransactionPieChart;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.MonthlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManageFinances extends AppCompatActivity {

    static final String ROOT = "Finances";
    static final List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    public int total=0;

    // Data
    YearlyTransactions yearlyTransactions;

    // Graphics
    TransactionPieChart pieChart;
    MonthlyPieChart monthlyPieChart;
    DebitLineGraph debitLineGraph;
    CreditLineGraph creditLineGraph;

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
        debitLineGraph = new DebitLineGraph(findViewById(R.id.debitChart), this);
        creditLineGraph = new CreditLineGraph(findViewById(R.id.creditChart), this);

    }

    // Event driven basis
    private void refreshGraphics(){
        this.pieChart.refresh(this.yearlyTransactions);
        this.monthlyPieChart.refresh(this.yearlyTransactions);
        this.debitLineGraph.refresh(this.yearlyTransactions);
        this.creditLineGraph.refresh(this.yearlyTransactions);
        Log.i("[OnComplete]", "Total: "+total);

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
                        total += bt.getAmount();
                    }
                    refreshGraphics();
                    yearlyTransactions.replaceMonth(mt);

                } else {
                    Log.i("[RefreshData]", "Error loading documents.");
                }

            }
        });
    }




}
