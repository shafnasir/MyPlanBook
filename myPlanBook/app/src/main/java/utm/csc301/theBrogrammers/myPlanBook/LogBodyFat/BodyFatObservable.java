package utm.csc301.theBrogrammers.myPlanBook.LogBodyFat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BodyFatObservable {
    private ArrayList<BodyFatObserver> observers = new ArrayList<BodyFatObserver>();

    public void attach(BodyFatObserver o) {
        this.observers.add(o);
    }

    public void detach(BodyFatObserver o) {
        this.observers.remove(o);
    }

    public void notifyObservers(String bodyFatEntry, Map<String, Object> bodyFatEntriesForMonth){
        for (BodyFatObserver o: this.observers) {
            if (bodyFatEntry != null) {
                if (bodyFatEntry.equals("0")) {
                    o.updateTV(null);
                }
                else {
                    o.updateTV(bodyFatEntry);
                }
            }
            if (bodyFatEntriesForMonth != null) {
                o.updateGraph(bodyFatEntriesForMonth);
            }
            if (bodyFatEntry == null && bodyFatEntriesForMonth == null) {
                o.updateTV(null);
                o.updateGraph(new HashMap<String, Object>());
            }
        }
    }

}
