package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import utm.csc301.theBrogrammers.myPlanBook.R;

public class ManuallyLoadTransactions extends AppCompatActivity {

    private Button debitBtn, creditBtn, commitTBtn;
    private ListView listView;
    private EditText cardNumInput, amountInput, category;
    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manually_load_transactions);

        init();

    }

    private void init(){
        this.category = (EditText) findViewById(R.id.category_input);
        this.amountInput = (EditText) findViewById(R.id.amount_input);
        this.cardNumInput = (EditText) findViewById(R.id.card_number_input);
        this.calendar = (CalendarView) findViewById(R.id.date_picker);
    }

    public void commitDebit(View v){

    }

    public void commitCredit(View v){

    }

    public void commitCollection(View v){

    }

}
