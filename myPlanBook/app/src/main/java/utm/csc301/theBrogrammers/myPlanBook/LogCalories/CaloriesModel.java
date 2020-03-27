package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaloriesModel extends CaloriesObservable {

    private int maxFoodCount;

    public CaloriesModel(int maxFoodCount) {
        this.maxFoodCount = maxFoodCount;
    }

    public void addCalorieEntry(String date, String calories) {
        String[] split = date.split("/");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("CalorieEntries")
                .collection(year).document(month).collection("days").document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<String> calorieEntries;
                    if (document.exists()) {
                        calorieEntries = (ArrayList<String>) document.getData().get("calorieEntries");
                        Log.d("addCalorieEntry", "Doc already exists");
                    }
                    else {
                        calorieEntries = new ArrayList<String>();
                        Log.d("addCalorieEntry", "Doc does not exist");
                    }
                    calorieEntries.add(calories);
                    setDataForDoc(docRef, calorieEntries);
                }
                else {
                    Log.d("addCalorieEntry", "Error getting doc", task.getException());
                }
            }
        });
    }

    public void setDataForDoc (DocumentReference docRef, ArrayList<String> calorieEntries) {
        Map<String, Object> docMap = new HashMap<String, Object>();
        docMap.put("calorieEntries", calorieEntries);
        docRef.set(docMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid){
                notifyObservers(calorieEntries);
                Log.d("setDataForDoc", "DocSnapShot successfully added");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e){
                Log.w("setDataForDoc", "Error writing doc", e);
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
                .collection("HealthAndFitnessDocs").document("CalorieEntries")
                .collection(year).document(month).collection("days").document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    ArrayList<String> calorieEntries;
                    if (document.exists()) {
                        calorieEntries = (ArrayList<String>) document.getData().get("calorieEntries");
                        Log.d("notifyObsEntryChange", "Doc already exists");
                    }
                    else {
                        calorieEntries = new ArrayList<String>();
                        Log.d("notifyObsEntryChange", "Doc does not exist");
                    }
                    notifyObservers(calorieEntries);
                }
                else {
                    Log.d("notifyObsEntryChange", "Error getting doc", task.getException());
                }
            }
        });
    }

    public void deleteCalorieEntries(String date, int[] indicesToDelete) {
        String[] split = date.split("/");
        String day = split[0];
        String month = split[1];
        String year = split[2];

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = user.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("HealthAndFitness").document(userName)
                .collection("HealthAndFitnessDocs").document("CalorieEntries")
                .collection(year).document(month).collection("days").document(day);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> calorieEntries = (ArrayList<String>) document.getData().get("calorieEntries");
                        int indexOffset = 0;
                        for (int i = 0; i < maxFoodCount; i++) {
                            if (indicesToDelete[i] == 0 && indexOffset > 0) { break; }
                            calorieEntries.remove(indicesToDelete[i] - indexOffset);
                            indexOffset++;
                        }
                        setDataForDoc(docRef, calorieEntries);
                        Log.d("delCalorieEntries", "Doc already exists");
                    }
                    else {
                        Log.d("delCalorieEntries", "Doc does not exist");
                    }
                }
                else {
                    Log.d("delCalorieEntries", "Error getting doc", task.getException());
                }
            }
        });
    }

}
