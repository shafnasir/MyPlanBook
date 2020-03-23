package utm.csc301.theBrogrammers.myPlanBook.LogBodyFat;

import java.util.HashMap;
import java.util.Map;

public class BodyFatModel {

    private Map<String, String> bodyFatEntriesByDate;

    public BodyFatModel() {
        bodyFatEntriesByDate = new HashMap<String, String>();
    }

    public String getBodyFatEntry(String date) {
        return this.bodyFatEntriesByDate.get(date);
    }

    public void addBodyFatEntry(String date, String bodyFat) {
        if (this.bodyFatEntriesByDate.containsKey(date)) {
            return;
        }
        this.bodyFatEntriesByDate.put(date, bodyFat);
    }

}
