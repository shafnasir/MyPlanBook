package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import java.util.ArrayList;

public class YearlyTransactions {

    int length;
    ArrayList<MonthlyTransactions> yearCollection = new ArrayList<MonthlyTransactions>();

    public YearlyTransactions(){
    }

    public void addTransaction(MonthlyTransactions t){
        this.yearCollection.add(t);
        this.length++;
    }

    public void deleteTransaction(MonthlyTransactions t){
        this.yearCollection.remove(t);
        this.length--;
    }
}
