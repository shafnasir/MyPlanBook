package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManuallyLoadTransactions extends AppCompatActivity {

    //private Button debitBtn, creditBtn, commitTBtn;
    private ListView listView;
    private EditText cardNumInput, amountInput, category;
    private CalendarView calendar;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> transactions = new ArrayList<String>();

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
        // Buttons
//        this.debitBtn = (Button) findViewById(R.id.commit_debit);
//        this.creditBtn = (Button) findViewById(R.id.commit_credit);
//        this.commitTBtn = (Button) findViewById(R.id.commit_transaction);
        // ListView
        this.listView = (ListView) findViewById(R.id.list_view);
        this.adapter = new ArrayAdapter<String>(ManuallyLoadTransactions.this,
                android.R.layout.simple_list_item_1, transactions);

        this.listView.setAdapter(adapter);
    }

    public void commitDebit(View v){
        BankTransaction t = null;
        try {
            t = createTransaction(true);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Error uploading this transaction.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        this.transactions.add("Debit: "+Double.toString(t.getAmount()));
        this.adapter.notifyDataSetChanged();

        Toast.makeText(ManuallyLoadTransactions.this,
                "Queued Transaction",
                Toast.LENGTH_LONG).show();

    }

    public void commitCredit(View v){
        BankTransaction t = null;
        try {
           t =  createTransaction(false);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ManuallyLoadTransactions.this,
                    "Queued Transaction",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        this.transactions.add("Credit: "+Double.toString(t.getAmount()));
        this.adapter.notifyDataSetChanged();

        Toast.makeText(ManuallyLoadTransactions.this,
                "Queued Transaction",
                Toast.LENGTH_SHORT).show();
    }

    public void commitCollection(View v){
        Toast.makeText(ManuallyLoadTransactions.this,
                "Imported " + transactions.size() + " transactions from . Check \"Manage Transactions" +
                        "\" page to view them.",
                Toast.LENGTH_LONG).show();
        clearCollection(v);
    }

    public void clearCollection(View v){
        this.transactions.clear();
        this.adapter.notifyDataSetChanged();
    }

    public BankTransaction createTransaction(Boolean isDebit) throws Exception {
        String categoryStr = this.category.getText().toString();
        float amountFlt = Float.parseFloat(this.amountInput.getText().toString());
        String cardStr = this.cardNumInput.getText().toString();
        String date = Long.toString(this.calendar.getDate());

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
