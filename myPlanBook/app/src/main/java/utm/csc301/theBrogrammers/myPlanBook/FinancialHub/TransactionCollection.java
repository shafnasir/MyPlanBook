package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import java.util.ArrayList;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;

public class TransactionCollection {

    private String date;
    private ArrayList<BankTransaction> transactions;
    private int[] mi; // indices mapping to positions in a csv file

    public TransactionCollection(String date, int[] indexMap){
        this.date = date;
        this.transactions = new ArrayList<>();
        this.mi = indexMap;
    }

    public TransactionCollection(int[] indexMap){
        this.date = "";
        this.transactions = new ArrayList<>();
        this.mi = indexMap;
    }

    public void addTransaction(BankTransaction transaction){
        this.transactions.add(transaction);

    }

    public void importSingleTransaction(String[] rowParameters){

        boolean isDebit = false;
        String date = rowParameters[mi[0]];
        String institution = rowParameters[mi[1]];
//        /float amount = Float.parseFloat(rowParameters[mi[2]]);
        float amount = 0.0f;
        String cardNum = rowParameters[mi[3]];
        if (rowParameters[4] == "true") isDebit = true;


        addTransaction(new BankTransaction(date, institution, amount, cardNum, isDebit));
    }

    public int length(){
        return this.transactions.size();
    }




}
