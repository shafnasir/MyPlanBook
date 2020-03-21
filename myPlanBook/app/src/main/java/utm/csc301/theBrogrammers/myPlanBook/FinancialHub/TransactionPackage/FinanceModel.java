package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinanceModel {

    YearlyTransactions yearlyTransactions;
    DatabaseReference userFinanceCollection;
    FirebaseUser user;

    public FinanceModel() {
        this.yearlyTransactions = new YearlyTransactions();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        String user_email = user.getUid();

        // Finance Collection
        userFinanceCollection = FirebaseDatabase.getInstance().
                getReference().child("Finances").child(user_email);
    }

}
