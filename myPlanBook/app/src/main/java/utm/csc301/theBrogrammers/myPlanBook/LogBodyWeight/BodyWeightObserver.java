package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import java.util.Map;

public interface BodyWeightObserver {
    public void updateTV(String bodyWeightEntry);
    public void updateGraph(Map<String, Object> bodyWeightEntriesForMonth);
}
