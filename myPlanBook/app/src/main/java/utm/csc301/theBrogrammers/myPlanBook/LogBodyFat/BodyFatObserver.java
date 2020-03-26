package utm.csc301.theBrogrammers.myPlanBook.LogBodyFat;

import java.util.Map;

public interface BodyFatObserver {
    public void updateTV(String bodyFatEntry);
    public void updateGraph(Map<String, Object> bodyFatEntriesForMonth);
}
