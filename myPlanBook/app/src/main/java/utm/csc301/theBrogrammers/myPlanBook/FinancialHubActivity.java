package utm.csc301.theBrogrammers.myPlanBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.util.ArrayList;
import java.util.Random;

import static utm.csc301.theBrogrammers.myPlanBook.R.id.previewExpChart;

public class FinancialHubActivity extends AppCompatActivity {
     private LineChart graph;
     final String[] monthStrings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
             "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_hub);
        graph = findViewById(previewExpChart);

        styleGraph();
        setData();
    }

    public void styleGraph() {
        final String[] months = {"Jan", "Feb", "Mar", "Apr"};

        //IndexAxisValueFormatter formatter =
        // Set x,y axis dimensions
        XAxis xAxis = graph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthStrings));
        xAxis.setAxisMaximum(11f);
        xAxis.setAxisMinimum(0f);

        YAxis leftAxis = graph.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);

        graph.getXAxis().setDrawGridLines(false);
        graph.getAxisRight().setEnabled(false);

        // Touch Screen Controls
        graph.setTouchEnabled(true);
        graph.setPinchZoom(true);
    }

    private void setData() {
        // Generate new random data to demo
        ArrayList<Entry> entries = genData(6, 100);
        LineDataSet dSet;

        // Generate new Chart data
        dSet = new LineDataSet(entries, "Monthly Expenditure");
        setDataSetStyling(dSet);

        // Set Graph Data Set
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSet);
        graph.setData(new LineData(dataSets));
    }


    private void setDataSetStyling(LineDataSet dSet){
        dSet.setLineWidth(3f);
        dSet.setCircleRadius(3f);
        dSet.setColor(Color.DKGRAY);
        dSet.setCircleColor(Color.DKGRAY);

        dSet.setDrawCircleHole(false);
        dSet.setDrawFilled(true);
        dSet.setDrawValues(false);

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.blue_gradient);
        dSet.setFillDrawable(drawable);
    }

    private ArrayList<Entry> genData(int input_range, int output_threshold){
        ArrayList<Entry> entries = new ArrayList<>();
        Random rand = new Random();
        for (int x = 0; x < input_range; x++){
            int y = (50) + (rand.nextInt() % 10);
            entries.add(new Entry(x, y));
        }
        return entries;
    }



}
