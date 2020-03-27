package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManuallyLoadTransactions extends AppCompatActivity {

    //private Button debitBtn, creditBtn, commitTBtn;
    private ListView listView;
    private EditText cardNumInput, amountInput, category;
    private CalendarView calendar;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> transactionsStrings = new ArrayList<String>();
    private TransactionCollection transactions = new TransactionCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_load_transactions);

        init();
    }

    private void init(){
        // Inputs
        this.category = (EditText) findViewById(R.id.category_input);
        this.amountInput = (EditText) findViewById(R.id.amount_input);
        this.cardNumInput = (EditText) findViewById(R.id.card_number_input);
        this.calendar = (CalendarView) findViewById(R.id.date_picker);

        this.listView = (ListView) findViewById(R.id.list_view);
        this.adapter = new ArrayAdapter<String>(ManuallyLoadTransactions.this,
                android.R.layout.simple_list_item_1, transactionsStrings);

        this.listView.setAdapter(adapter);
    }

    public void commitDebit(View v){
        BankTransaction t = null;
        try {
            t = createTransaction(true);
            if (t != null) {
                this.transactions.addTransaction(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Error uploading this transaction.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Update transaction queue UI
        this.transactionsStrings.add("Debit: "+Double.toString(t.getAmount()));
        this.adapter.notifyDataSetChanged();

        Toast.makeText(ManuallyLoadTransactions.this,
                "Queued Transaction",
                Toast.LENGTH_LONG).show();

    }

    public void commitCredit(View v){
        BankTransaction t = null;
        try {
           t =  createTransaction(false);
           if (t != null) this.transactions.addTransaction(t);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Queued Transaction",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Update transaction queue UI
        this.transactionsStrings.add("Credit: "+Double.toString(t.getAmount()));
        this.adapter.notifyDataSetChanged();

        Toast.makeText(ManuallyLoadTransactions.this,
                "Queued Transaction",
                Toast.LENGTH_SHORT).show();
    }

    public void commitCollection(View v){
        Log.i("FUCK","##########################");


        if (this.transactions.length() > 0){
            FinanceModel.loadMonthlyCollection(this.transactions.toMonthlyCollection());
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Imported " + transactionsStrings.size() + " transactions from . Check \"Manage Transactions" +
                            "\" page to view them.",
                    Toast.LENGTH_LONG).show();
        }

        clearCollection(v);
    }

    public void clearCollection(View v){
        this.transactionsStrings.clear();
        this.transactions = new TransactionCollection();
        this.adapter.notifyDataSetChanged();
    }

    public BankTransaction createTransaction(Boolean isDebit) throws Exception {
        String categoryStr = this.category.getText().toString();
        float amountFlt = Float.parseFloat(this.amountInput.getText().toString());
        String cardStr = this.cardNumInput.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
        String date = dateFormat.format(this.calendar.getDate());

        if (categoryStr.equals("") || cardStr.equals("") ||
        cardStr.equals("") || date.equals("")){
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Error uploading this transaction.",
                    Toast.LENGTH_LONG).show();
            return null;
        }

        return new BankTransaction(date, categoryStr, amountFlt, cardStr, isDebit);

    }

}
