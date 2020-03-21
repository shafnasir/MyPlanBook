package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MonthlyTransactions {

    String month;
    int length;
    ArrayList<BankTransaction> monthCollection = new ArrayList<BankTransaction>();

    public MonthlyTransactions(String month){
        this.month = "month";
    }

    public void addTransaction(BankTransaction t){
        this.monthCollection.add(t);
        this.length++;
    }

    public void deleteTransaction(BankTransaction t){
        this.monthCollection.remove(t);
        this.length--;
    }
}
