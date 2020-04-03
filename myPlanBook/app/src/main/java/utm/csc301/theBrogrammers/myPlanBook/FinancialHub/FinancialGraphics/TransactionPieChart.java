package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics;



import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.OnDrawLineChartTouchListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.CategoryPopup;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.ManageFinances;
import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;

public class TransactionPieChart implements OnChartValueSelectedListener {

    public PieChart pieChart;
    public ManageFinances manageFinances;
    public HashMap<String, Integer> topSixCategories;


    public TransactionPieChart(PieChart pieChart, ManageFinances manageFinances){

        this.pieChart = pieChart;
        this.manageFinances = manageFinances;
    }

    public void refresh(YearlyTransactions yt){
        ArrayList<PieEntry>  entryList = new ArrayList<>();
        if (yt.length == 0) return; // No data yet

        HashMap<String, Integer> topSixCategories = yt.getTransactionsCollection();

        for(Map.Entry<String, Integer> entry: topSixCategories.entrySet()){
            entryList.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet pieData = new PieDataSet(entryList, "");
        PieData data = new PieData(pieData);
        pieChart.setData(data);

        // SET COLORS
        ArrayList<Integer> colors = new ArrayList<>();
        for (int x: ColorTemplate.LIBERTY_COLORS) colors.add(x);
        for (int x: ColorTemplate.MATERIAL_COLORS) colors.add(x);
        pieData.setColors(colors);

        pieChart.animateXY(5000, 5000);
        pieChart.setUsePercentValues(true);
        pieChart.setContentDescription("");
        pieChart.getDescription().setEnabled(false);
        pieChart.getData().setDrawValues(true);
        pieChart.getData().setValueTextSize(14f);
        pieChart.setDrawSliceText(false);
        pieChart.getLegend().setEnabled(false);

        pieChart.setOnChartValueSelectedListener(this);
        pieChart.setClickable(true);

        pieChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry entry = (PieEntry) e;
        manageFinances.popUp(entry.getLabel(), entry.getValue());
    }

    @Override
    public void onNothingSelected() {
    }
}
