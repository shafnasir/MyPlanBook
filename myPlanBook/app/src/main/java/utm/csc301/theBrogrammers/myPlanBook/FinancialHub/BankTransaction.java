package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

public class BankTransaction {

    private String institution;
    private String cardNum;
    float amount;
    private boolean isDebit;
    private String date;

    // Empty constructor
    public BankTransaction(){}

    public BankTransaction(String date, String institution, float amount,
                           String cardNum, boolean isDebit){
        this.institution = institution;
        this.amount = amount;
        this.isDebit = isDebit;
        this.cardNum = cardNum;
        this.date = date;
    }

    public String getInstitution() {
        return institution;
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

    public void setInstitution(String institution) {
        this.institution = institution;
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
}
