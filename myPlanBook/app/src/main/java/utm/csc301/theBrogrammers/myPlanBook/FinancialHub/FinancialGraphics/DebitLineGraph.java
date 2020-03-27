package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.Year;
import java.util.ArrayList;
import java.util.Random;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.BankTransaction;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.FinanceModel;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.MonthlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.R;

import static utm.csc301.theBrogrammers.myPlanBook.R.id.previewExpChart;

public class DebitLineGraph {

    private Context context;
    private LineChart graph;
    final String[] monthStrings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};

    public DebitLineGraph(LineChart graph, Context context) {
        this.context = context;
        this.graph = graph;
        styleGraph();
        setExpenditureData(null);

    }

    /**
     * Style this classes chart axes.
     */
    public void styleGraph() {
        // Set x,y axis dimensions
        XAxis xAxis = graph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthStrings));
        xAxis.setAxisMaximum(11f);
        xAxis.setAxisMinimum(0f);

        YAxis leftAxis = graph.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.removeAllLimitLines();
//        leftAxis.setAxisMaximum(100f);
//        leftAxis.setAxisMinimum(0f);

        graph.getDescription().setEnabled(false);
        graph.getXAxis().setDrawGridLines(false);
        graph.getAxisRight().setEnabled(true);
        graph.getAxisRight().setDrawAxisLine(false);
        graph.getAxisRight().setDrawGridLines(false);

        // Touch Screen Controls
        graph.setTouchEnabled(true);
        graph.setPinchZoom(true);
    }


    /**
     * Generate data to be displayed by the preview chart.
     */
    private void setExpenditureData(YearlyTransactions yearlyTransactions) {
        // Generate new random data to demo
        ArrayList<Entry> entries = genData(yearlyTransactions);
        LineDataSet dSet;

        // Generate new Chart data
        dSet = new LineDataSet(entries, "Monthly Expenditure");
        setDataSetStyling(dSet);

        // Set Graph Data Set
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dSet);
        graph.setData(new LineData(dataSets));
    }

    /**
     * Generate data to be displayed by the preview chart; this data
     * is generated only when a yearly expenditure goal has been set
     * by the user.
     */


    /**
     * Style how the data and lines are represented in the preview chart.
     */
    private void setDataSetStyling(LineDataSet dSet){
        int fill = R.drawable.red_gradient;
        int colour = Color.DKGRAY;
        float lWidth = 3;

        dSet.setLineWidth(3f);
        dSet.setCircleRadius(3f);
        dSet.setDrawCircleHole(false);
        dSet.setDrawFilled(true);
        dSet.setDrawValues(false);

        dSet.setLineWidth(lWidth);
        dSet.setColor(colour);
        dSet.setCircleColor(colour);
        Drawable drawable = ContextCompat.getDrawable(context, fill);
        dSet.setFillDrawable(drawable);
    }

    // Generate Random Data for the Demo
    private ArrayList<Entry> genData(YearlyTransactions yt){
        ArrayList<Entry> entries = new ArrayList<>();
        if (yt == null || yt.length == 0) return entries;

        for (int monthIndex=0; monthIndex< yt.getList().size(); monthIndex++){
            MonthlyTransactions monthly = yt.getIndexedMonth(monthIndex);
            if (monthly == null) continue;
            double total = 0;
            Log.i("["+monthIndex+"]", "Month = "+ monthly.month+ ", Amount = "+total);
            for (BankTransaction b: monthly.getCollection()){
                Log.i("["+monthly.month+"]", "Amount = "+b.getAmount());
                if (b.isDebit()) total += b.getAmount();
            }
            entries.add(new Entry(monthIndex, (float) total));
        } return entries;

    }

    public void refresh(YearlyTransactions yt){
        setExpenditureData(yt);
        this.graph.invalidate();
    }

}
