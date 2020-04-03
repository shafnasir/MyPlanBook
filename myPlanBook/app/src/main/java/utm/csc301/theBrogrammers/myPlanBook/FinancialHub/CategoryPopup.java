package utm.csc301.theBrogrammers.myPlanBook.FinancialHub;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.Notification.NotificationChannels;
import utm.csc301.theBrogrammers.myPlanBook.R;

public class CategoryPopup extends AppCompatActivity {


    private String entryLabel;
    private Float entryAmt;
    private TextView labelView;
    private TextView amtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_popup);

        labelView = findViewById(R.id.entryLabel);
        amtView = findViewById(R.id.entryAmount);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w = (int) (0.8 * dm.widthPixels);
        int h = (int) (0.5 * dm.heightPixels);

        getWindow().setLayout(w, h);

        Bundle bundle = getIntent().getExtras();
        entryLabel = bundle.getString("entryLabel");
        entryAmt = bundle.getFloat("entryAmt");
        String amtStr = "$ " + Float.toString(entryAmt);

        labelView.setText(entryLabel);
        amtView.setText(amtStr);

    }

    private void setText(){

    }


}
