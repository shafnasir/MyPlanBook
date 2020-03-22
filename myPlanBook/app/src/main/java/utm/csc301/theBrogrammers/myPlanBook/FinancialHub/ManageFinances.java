package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManageFinances extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_finances);

        FinanceModel.collectMonthlyCollection();
    }


}
