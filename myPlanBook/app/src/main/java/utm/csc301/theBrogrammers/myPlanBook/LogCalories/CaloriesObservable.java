package utm.csc301.theBrogrammers.myPlanBook.LogCalories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaloriesObservable {

    private ArrayList<CaloriesObserver> observers = new ArrayList<CaloriesObserver>();

    public void attach(CaloriesObserver o) {
        this.observers.add(o);
    }

    public void detach(CaloriesObserver o) {
        this.observers.remove(o);
    }

    public void notifyObservers(ArrayList<String> foodForDate){
        for (CaloriesObserver o: this.observers) {
            o.updateTVs(foodForDate);
        }
    }

}
