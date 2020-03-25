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

    public void deleteBodyFatEntry(String date) {
        this.bodyFatEntriesByDate.remove(date);
    }

    public Map<String,String> getBodyFatEntriesForMonth(String date){
        String[] split = date.split("/");
        String month = split[1];
        String year = split[2];

        Map<String,String> bodyFatEntriesForMonth = new HashMap<String, String>();

        for (int i = 1; i < 32; i++) {
            String testDate = String.valueOf(i) + "/" + month + "/" + year;
            if (this.bodyFatEntriesByDate.containsKey(testDate)){
                bodyFatEntriesForMonth.put(testDate, this.bodyFatEntriesByDate.get(testDate));
            }
        }

        return bodyFatEntriesForMonth;
    }

}
