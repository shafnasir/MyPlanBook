package utm.csc301.theBrogrammers.myPlanBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
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

import utm.csc301.theBrogrammers.myPlanBook.R.drawable;

import static utm.csc301.theBrogrammers.myPlanBook.R.id.previewExpChart;

public class FinancialHubActivity extends AppCompatActivity {
     private LineChart graph;
     final String[] monthStrings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
             "Aug", "Sep", "Oct", "Nov", "Dec"};
     int lastly; // the last month's expenditure - for demo purposes
     boolean doProjDemo = true;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide(); // Get rid of toolbar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_hub);
        graph = findViewById(previewExpChart);

        styleGraph();
        setExpenditureData();
        if (doProjDemo) setProjectionData();
    }

    public void styleGraph() {
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

        graph.getDescription().setEnabled(false);
        graph.getXAxis().setDrawGridLines(false);
        graph.getAxisRight().setEnabled(true);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getAxisRight().setDrawGridLines(false);

        // Touch Screen Controls
        graph.setTouchEnabled(true);
        graph.setPinchZoom(true);
    }

    private void setExpenditureData() {
        // Generate new random data to demo
        ArrayList<Entry> entries = genData(6, 100);
        LineDataSet dSet;

        // Generate new Chart data
        dSet = new LineDataSet(entries, "Monthly Expenditure");
        setDataSetStyling(dSet, false);

        // Set Graph Data Set
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSet);
        graph.setData(new LineData(dataSets));
    }

    // For the demo
    private void setProjectionData(){
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(5, lastly));
        for (int x = 6; x < 12; x++){
            entries.add(new Entry(x, 50));
        }
        LineDataSet pSet = new LineDataSet(entries, "Projected Expenditure");
        setDataSetStyling(pSet, true);

        // Set Graph Data Set
        graph.getLineData().addDataSet(pSet);

    }


    private void setDataSetStyling(LineDataSet dSet, boolean isProj){
        int fill = drawable.blue_gradient;
        int colour = Color.DKGRAY;
        float lWidth = 3;

        dSet.setLineWidth(3f);
        dSet.setCircleRadius(3f);
        dSet.setDrawCircleHole(false);
        dSet.setDrawFilled(true);
        dSet.setDrawValues(false);


        if (isProj){
            fill = drawable.red_gradient;
            colour = Color.RED;
            dSet.enableDashedLine(10f, 5f, 0f);
            dSet.enableDashedHighlightLine(10f, 5f, 0f);
            lWidth = 1;
        }

        dSet.setLineWidth(lWidth);
        dSet.setColor(colour);
        dSet.setCircleColor(colour);
        Drawable drawable = ContextCompat.getDrawable(this, fill);
        dSet.setFillDrawable(drawable);
    }



    // Generate Random Data for the Demo
    private ArrayList<Entry> genData(int input_range, int output_threshold){
        ArrayList<Entry> entries = new ArrayList<>();
        Random rand = new Random();
        for (int x = 0; x < input_range; x++){
            int y = (50) + (rand.nextInt() % 10);
            entries.add(new Entry(x, y));
            if (x == input_range-1) lastly = y;
        }
        return entries;
    }



}
