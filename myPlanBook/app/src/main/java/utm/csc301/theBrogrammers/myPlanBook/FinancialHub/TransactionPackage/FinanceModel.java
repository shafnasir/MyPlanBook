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

import java.time.Month;
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


    public static void loadMonthlyCollection(HashMap<String, MonthlyTransactions> map){
        for(MonthlyTransactions m: map.values()){
            loadMonthCollection(m);
        }
    }

    public static void loadMonthCollection(MonthlyTransactions monthlyTransactions){
        // Db details
        YearlyTransactions yearlyTransactions = new YearlyTransactions();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        ArrayList<BankTransaction> monthCol = monthlyTransactions.getCollection();
        HashSet<String> uidSet = new HashSet<>();
        for (BankTransaction bt: monthCol){
            Map<String, Object> map = bt.toMap();

            // LOAD INTO THE FIRESTORE DB FOR THIS USER
            String uniqueID = UUID.randomUUID().toString();
            db.collection(ROOT).document(userName).collection(monthlyTransactions.month)
                    .document(uniqueID).set(map);
        }
    }

    public static YearlyTransactions collectMonthlyCollection() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        YearlyTransactions yt = new YearlyTransactions();
        for (String month : months) {
            CollectionReference collection = db.collection(ROOT).document(userName).collection(month);
            yt.addMonthCollection(processMonth(month, collection.get()));
        } return yt;
    }

    public static MonthlyTransactions processMonth(String month, Task<QuerySnapshot> task){
        MonthlyTransactions mt = new MonthlyTransactions(month);
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        BankTransaction bt = new BankTransaction(document.getData());
                        mt.addTransaction(bt);
                        Log.i("ProcessMonth", document.getId() + " => " + document.getData());
//                        Log.i("ProcessMonth", document.getId() + " => " + document.getData());
                    }
                } else {
//                    Log.d("ProcessMonth", "Error getting documents.", task.getException());
                }
            }
        });
        Log.i("[Monthly Size]", "SIZE : "+ mt.length);
        return mt;
    }

    public static void deleteMonth(String month){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collection = db.collection(ROOT).document(userName).collection(month);
        collection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        collection.document(document.getId()).delete();
                        Log.i("Delete "+month, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("ProcessMonth", "Error deleting documents.", task.getException());
                }
            }
        });

    }


}
