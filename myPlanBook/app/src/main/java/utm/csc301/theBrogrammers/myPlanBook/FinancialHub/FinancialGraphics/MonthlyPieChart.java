package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.MonthlyTransactions;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;

public class MonthlyPieChart {

    PieChart pieChart;

    public MonthlyPieChart(PieChart pieChart){
        this.pieChart = pieChart;
    }

    public void refresh(YearlyTransactions yt){
        ArrayList<PieEntry> entryList = new ArrayList<>();
        if (yt.length == 0) return; // No data yet



        for(MonthlyTransactions monthly: yt.getList()){
            Log.i("[Month]", "Month: "+ monthly.month + ", size = " + Integer.toString(monthly.size()));
            if (monthly.size() < 1) continue;
            entryList.add(new PieEntry(monthly.size(), monthly.month));
        }

        PieDataSet pieData = new PieDataSet(entryList, "");
        PieData data = new PieData(pieData);
        pieChart.setData(data);

        // SET COLORS
        ArrayList<Integer> colors = new ArrayList<>();
        for (int x: ColorTemplate.VORDIPLOM_COLORS) colors.add(x);
        for (int x: ColorTemplate.MATERIAL_COLORS) colors.add(x);
        pieData.setColors(colors);

        pieChart.animateXY(5000, 5000);
        pieChart.setUsePercentValues(true);
        pieChart.setContentDescription("");
        pieChart.getDescription().setEnabled(false);
        pieChart.getData().setDrawValues(false);
        pieChart.getData().setValueTextSize(14f);
        pieChart.getLegend().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);

        pieChart.invalidate();
    }



}
