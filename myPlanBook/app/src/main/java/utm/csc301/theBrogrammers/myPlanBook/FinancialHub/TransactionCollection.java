package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import java.util.ArrayList;

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

    public boolean importSingleTransaction(String[] rowParameters){
        if (rowParameters.length != this.mi.length) return false;

        String date = rowParameters[mi[0]];
        String institution = rowParameters[mi[1]];
        int amount = Integer.parseInt(rowParameters[mi[2]]);
        boolean isDebit = Boolean.getBoolean(rowParameters[mi[3]]);
        int cardNum = Integer.parseInt(rowParameters[mi[4]]);

        addTransaction(new BankTransaction(date, institution, amount, isDebit, cardNum));
        return true;
    }




}
