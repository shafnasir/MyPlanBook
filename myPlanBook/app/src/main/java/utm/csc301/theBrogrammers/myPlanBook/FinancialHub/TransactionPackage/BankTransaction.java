package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import java.util.HashMap;

public class BankTransaction {

    private String category;
    private String cardNum;
    private float amount;
    private boolean isDebit;
    private String date;

    // Empty constructor
    public BankTransaction(){}

    public BankTransaction(String date, String category, float amount,
                           String cardNum, boolean isDebit){
        this.category = category;
        this.amount = amount;
        this.isDebit = isDebit;
        this.cardNum = cardNum;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public String getCardNum() {
        return cardNum;
    }

    public float getAmount() {
        return amount;
    }
    


    public boolean isDebit() {
        return isDebit;
    }

    public void setCategory(String institution) {
        this.category = category;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDebit(boolean debit) {
        isDebit = debit;
    }

    public HashMap<String, Object> toMap(){
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Catgeory", this.category);
        hm.put("isDebit", this.isDebit);
        hm.put("Date", this.date);
        hm.put("cardNumber", this.cardNum);
        hm.put("Amount", this.amount);
        return hm;
    }




}
