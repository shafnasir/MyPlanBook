package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankTransaction {

    private String id; // Random unique identifier
    private String category;
    private String cardNum;
    private float amount;
    private boolean isDebit;
    private String date;
    static final List<String> months = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");


    public BankTransaction(Map<String, Object> hashMap){
        this.date = (String) hashMap.get("date");
        this.category = (String) hashMap.get("category");
        this.cardNum = (String) hashMap.get("cardNumber");
        this.amount = (float) hashMap.get("amount");
        this.isDebit = (boolean) hashMap.get("isDebit");
    }

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

    public String getDate() { return date; }
    


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

    public int getMonth() {
        String[] params = this.date.split("-");
        return Integer.parseInt(params[1]);
    }

    public String getMonthStr(){
        return months.get(this.getMonth()-1);
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("catgeory", this.category);
        hm.put("isDebit", this.isDebit);
        hm.put("date", this.date);
        hm.put("cardNumber", this.cardNum);
        hm.put("amount", this.amount);
        return hm;
    }




}
