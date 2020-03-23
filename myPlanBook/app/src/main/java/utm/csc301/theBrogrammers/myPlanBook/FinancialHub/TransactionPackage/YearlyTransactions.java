package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class YearlyTransactions {

    public int length = 0;
    CopyOnWriteArrayList<MonthlyTransactions> yearCollection = new CopyOnWriteArrayList<>();
//    public ArrayList<MonthlyTransactions> yearCollection = new ArrayList<MonthlyTransactions>();

    public YearlyTransactions(){
    }

    public void addMonthCollection(MonthlyTransactions t){
        this.yearCollection.add(t);
        this.length += t.length;

    }

    public void deleteMonthCollection(MonthlyTransactions t){
        this.yearCollection.remove(t);
        this.length -= t.length;

    }

    public void replaceMonth(MonthlyTransactions monthlyTransactions){
        MonthlyTransactions removed = null;
        for (MonthlyTransactions m: this.yearCollection){
            if (m.month.equals(monthlyTransactions.month)){
                removed = m;
                this.yearCollection.remove(m);
            }
        } this.yearCollection.add(monthlyTransactions);
        if (removed != null) this.length = this.length - removed.size() + monthlyTransactions.length;
        else this.length += monthlyTransactions.length;
    }


    public CopyOnWriteArrayList<MonthlyTransactions> getList(){
        return this.yearCollection;
    }

    public HashMap<String, Integer> getTransactionsCollection(){

        HashMap<String, Integer> categoricalTransactions = new HashMap<>();
        for (MonthlyTransactions monthly: this.yearCollection){
            for (BankTransaction bTrans: monthly.getCollection()){
                if (categoricalTransactions.containsKey(bTrans.getCategory())){
                    int quantity = categoricalTransactions.get(bTrans.getCategory());
                    categoricalTransactions.put(bTrans.getCategory(), quantity+1);
                } else {
                    categoricalTransactions.put(bTrans.getCategory(), 1);
                }
            }
        }

        return getTop5Category(categoricalTransactions);
    }

    public HashMap<String, Integer> getTop5Category(HashMap<String, Integer> aggregatedByCategory){
        print();
        Map.Entry<String, Integer> maxEntry = null;
        HashMap<String, Integer> maxMap = new HashMap<>();
        int i = 0;
        while (i < 5){
            for (Map.Entry<String, Integer> entry: aggregatedByCategory.entrySet()){
                Log.i("Set", "Category: " +entry.getKey() + ", Size: "+ Integer.toString(entry.getValue()));
                if (maxEntry == null) maxEntry = entry;
                if (entry.getValue() > maxEntry.getValue()){
                    maxEntry = entry;
                }
            } i++;
            if (maxEntry != null) {
                aggregatedByCategory.remove(maxEntry.getKey());
                maxMap.put(maxEntry.getKey(), maxEntry.getValue());
                maxEntry = null;
            }
        }
        int otherTotal = 0;

        for (Map.Entry<String, Integer> entry: aggregatedByCategory.entrySet()){
            otherTotal += entry.getValue();
        }
        maxMap.put("Other", otherTotal);
        return maxMap;
    }

    public void print(){
        Log.i("In top 5 category", "Print #");
        for (MonthlyTransactions m: this.yearCollection){
            Log.i("[YEARLY]", "[MONTH] = " + m.month + " size: "+m.size());
        }
    }


}
