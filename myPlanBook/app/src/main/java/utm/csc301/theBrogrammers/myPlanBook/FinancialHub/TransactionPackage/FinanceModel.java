package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class FinanceModel {

    // URI Collection constants
    static final String ROOT = "Finances";
    static final List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    YearlyTransactions yearlyTransactions;
    FirebaseFirestore db;
    CollectionReference financeCol;
    FirebaseUser user;
    String userName;


    public FinanceModel() {
        this.yearlyTransactions = new YearlyTransactions();
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.userName = user.getUid();
        this.db = FirebaseFirestore.getInstance();

    }



    public void loadMonthCollection(MonthlyTransactions monthlyTransactions){
        ArrayList<BankTransaction> monthCol = monthlyTransactions.getCollection();
        HashSet<String> uidSet = new HashSet<>();
        for (BankTransaction bt: monthCol){
            Map<String, Object> map = bt.toMap();

            String uniqueID = UUID.randomUUID().toString();
            db.collection(ROOT).document(userName).collection(monthlyTransactions.month)
                    .document(uniqueID).set(map);
        }
    }

    public void loadMonthlyCollection(HashMap<String, MonthlyTransactions> map){
        for(MonthlyTransactions m: map.values()){
            loadMonthCollection(m);
        }
    }


//    public void collectMonths() {
//
//        for (String month : months) {
//            CollectionReference collection = db.collection(ROOT).document(userName).collection(month);
//            processMonth(month, collection.get());
//        }
//    }
//
//    public void processMonth(String month, Task<QuerySnapshot> task){
//        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.i("ProcessMonth", document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d("ProcessMonth", "Error getting documents.", task.getException());
//                }
//            }
//        });
//    }
}
