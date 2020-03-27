package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.MonthlyTransactions;

public class TransactionCollection {

    private ArrayList<BankTransaction> transactions = new ArrayList<>();
    private int[] mi = {0, 1, 2, 3, 4}; // default map;

//    public TransactionCollection(String date, int[] indexMap){
//        this.transactions = new ArrayList<>();
//        this.mi = indexMap;
//    }

    public TransactionCollection(){

    }

    public TransactionCollection(int[] indexMap){
        this.transactions = new ArrayList<>();
        this.mi = indexMap;
    }

    public void addTransaction(BankTransaction transaction){
        this.transactions.add(transaction);

    }

    public void importSingleFileTransaction(String[] rowParameters){

        boolean isDebit = Boolean.parseBoolean(rowParameters[4]);
        String date = rowParameters[mi[0]];
        String institution = rowParameters[mi[1]];
        float amount = Float.parseFloat(rowParameters[mi[2]]);
        String cardNum = rowParameters[mi[3]];
        Log.i("[Bank Transaction]",  "isDebit = " + isDebit + "date = " + date
        + "\n category  = " + institution + "\n amount = "+ amount + " cardNum "+cardNum);

        addTransaction(new BankTransaction(date, institution, amount, cardNum, isDebit));
    }

    public int length(){
        return this.transactions.size();
    }

    public HashMap<String, MonthlyTransactions> toMonthlyCollection(){
        HashMap<String, MonthlyTransactions> hashMap = new HashMap<>();
        for (BankTransaction bt: transactions){
            if (hashMap.containsKey(bt.getMonthStr())){
                hashMap.get(bt.getMonthStr()).addTransaction(bt);
            } else {
                Log.i("Monthly", "Month Str: "+ bt.getMonthStr());
                MonthlyTransactions mt = new MonthlyTransactions(bt.getMonthStr());
                mt.addTransaction(bt);
                hashMap.put(bt.getMonthStr(), mt);
            }
        } return hashMap;
    }




}
