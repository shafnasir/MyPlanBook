package utm.csc301.theBrogrammers.myPlanBook.FinancialHub.FinancialGraphics;



import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utm.csc301.theBrogrammers.myPlanBook.FinancialHub.TransactionPackage.YearlyTransactions;

public class TransactionPieChart {

    PieChart pieChart;

    public TransactionPieChart(PieChart pieChart){
        this.pieChart = pieChart;
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

        pieChart.invalidate();
    }






}
