package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight.BodyWeightObserver;

public class BodyWeightObservable {
    private ArrayList<BodyWeightObserver> observers = new ArrayList<BodyWeightObserver>();

    public void attach(BodyWeightObserver o) {
        this.observers.add(o);
    }

    public void detach(BodyWeightObserver o) {
        this.observers.remove(o);
    }

    public void notifyObservers(String bodyWeightEntry, Map<String, Object> bodyWeightEntriesForMonth){
        for (BodyWeightObserver o: this.observers) {
            if (bodyWeightEntry != null) {
                if (bodyWeightEntry.equals("0")) {
                    o.updateTV(null);
                }
                else {
                    o.updateTV(bodyWeightEntry);
                }
            }
            if (bodyWeightEntriesForMonth != null) {
                o.updateGraph(bodyWeightEntriesForMonth);
            }
            if (bodyWeightEntry == null && bodyWeightEntriesForMonth == null) {
                o.updateTV(null);
                o.updateGraph(new HashMap<String, Object>());
            }
        }
    }
}
