package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class BodyWeightModel extends BodyWeightObservable {

    public BodyWeightModel(){}

    public void addBodyWeightEntry(String date, String bodyWeight) {
        String[] split = date.split("/");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        Map<String, Object> bodyWeightEntry = new HashMap<String, Object>();
        bodyWeightEntry.put(date, bodyWeight);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("BodyWeightEntries")
                .collection(year).document(month).collection("days").document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("addBodyWeightEntry", "Doc already exists");
                    }
                    else {
                        Log.d("addBodyWeightEntry", "Doc does not exist");
                        setDataForDoc(docRef, bodyWeightEntry, date, month, year);
                    }
                }
                else {
                    Log.d("addBodyWeightEntry", "Error getting doc", task.getException());
                }
            }
        });
    }

    // Reference material: https://firebase.google.com/docs/firestore/manage-data/add-data
    public void setDataForDoc (DocumentReference docRef, Map<String, Object> bodyWeightEntry, String date, String month, String year) {
        docRef.set(bodyWeightEntry).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid){
                notifyObservers((String) bodyWeightEntry.get(date), null);
                notifyObserversGraphChange(month, year);
                Log.d("setDataForDoc", "DocSnapShot successfully added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Log.w("setDataForDoc", "Error writing doc", e);
            }
        });
    }

    public void notifyObserversGraphChange(String month, String year){
        Map<String, Object> bodyWeightEntriesForMonth = new HashMap<String, Object>();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("BodyWeightEntries")
                .collection(year).document(month).collection("days");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> docMap = document.getData();
                        for (Map.Entry<String, Object> entry: docMap.entrySet()) {
                            bodyWeightEntriesForMonth.put(entry.getKey(), entry.getValue());
                        }
                        Log.i("notifyObserversGC", document.getId() + " => " + document.getData());
                    }
                    if (!bodyWeightEntriesForMonth.isEmpty()) {
                        notifyObservers(null, bodyWeightEntriesForMonth);
                    }
                    else {
                        notifyObservers(null, null);
                    }
                } else {
                    Log.d("notifyObserversGC", "Error getting documents.", task.getException());
                }
            }
        });
    }

    public void notifyObserversEntryChange(String date) {
        String[] split = date.split("/");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("BodyWeightEntries")
                .collection(year).document(month).collection("days").document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        notifyObservers((String) document.getData().get(date), null);
                        Log.d("notifyObsEntryChange", "Doc already exists");
                    }
                    else {
                        notifyObservers("0", null);
                        Log.d("notifyObsEntryChange", "Doc does not exist");
                    }
                }
                else {
                    Log.d("notifyObsEntryChange", "Error getting doc", task.getException());
                }
            }
        });
    }

    public void deleteBodyWeightEntry(String date) {
        String[] split = date.split("/");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("BodyWeightEntries")
                .collection(year).document(month).collection("days").document(day).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        notifyObserversEntryChange(date);
                        notifyObserversGraphChange(month, year);
                        Log.d("deleteBWEntry", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("deleteBWEntry", "Error deleting document", e);
                    }
                });
    }

}
