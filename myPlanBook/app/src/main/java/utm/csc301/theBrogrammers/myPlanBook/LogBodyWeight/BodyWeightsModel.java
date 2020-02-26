package utm.csc301.theBrogrammers.myPlanBook.LogBodyWeight;

import java.util.HashMap;

public class BodyWeightsModel {

    HashMap<String, String> bodyWeightsByDate;

    public BodyWeightsModel() {
        bodyWeightsByDate = new HashMap<String, String>();
    }

    public void addWeight(String date, String weight) {
        if (this.bodyWeightsByDate.containsKey(date)){
            this.bodyWeightsByDate.remove(date);
            this.bodyWeightsByDate.put(date, weight);
            return;
        }
        else {
            this.bodyWeightsByDate.put(date, weight);
            return;
        }
    }

    public void removeWeight(String date) {
        if (this.bodyWeightsByDate.containsKey(date)){
            this.bodyWeightsByDate.remove(date);
            return;
        }
        else {
            System.out.println("No weight entry for this date!");
            return;
        }
    }

    public String getWeight(String date) {
        if (this.bodyWeightsByDate.containsKey(date)){
            return this.bodyWeightsByDate.get(date);
        }
        else {
            System.out.println("No weight entry for this date!");
            return  null;
        }
    }

    public HashMap<String,String> getBodyWeightsForMonth(String date){
        String[] split = date.split("/");
        String month = split[1];
        String year = split[2];

        HashMap<String,String> bodyWeightsForMonth = new HashMap<String, String>();

        for (int i = 1; i < 32; i++) {
            String testDate = String.valueOf(i) + "/" + month + "/" + year;
            if (this.bodyWeightsByDate.containsKey(testDate)){
                bodyWeightsForMonth.put(testDate, this.bodyWeightsByDate.get(testDate));
            }
        }

        return bodyWeightsForMonth;
    }
}
