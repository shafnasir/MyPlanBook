package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyTransactions {

    public String month;
    int length = 0;
    ArrayList<BankTransaction> monthCollection = new ArrayList<BankTransaction>();
    HashMap<String, Integer> categoryAmts = new HashMap<>();

    public MonthlyTransactions(String month){
        this.month = month;
    }

    public void addTransaction(BankTransaction t){
        this.monthCollection.add(t);
        this.length++;
    }

    public void deleteTransaction(BankTransaction t){
        this.monthCollection.remove(t);
        this.length--;
    }

    public ArrayList<BankTransaction> getCollection(){
        return this.monthCollection;
    }

    public int size(){
        return this.monthCollection.size();
    }


}
