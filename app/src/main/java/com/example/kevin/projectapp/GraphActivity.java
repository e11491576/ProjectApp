package com.example.kevin.projectapp;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    private static String TAG = "GraphActivity";

    private int[] termMoney=new int[7];//to calculate every term total cost
    private String[] xData = {"食", "衣" , "住" , "行", "育", "樂", "其它"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        //Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) findViewById(R.id.PieChart);

        //pieChart.setDescription("wrong");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("花錢項目分析");
        pieChart.setCenterTextSize(15);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        countItem();
        addDataSet();
    }

    private void addDataSet() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0;i < termMoney.length;i++){
            if(termMoney[i]>0)
            pieEntries.add(new PieEntry(termMoney[i],xData[i]));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(92,173,173));
        colors.add(Color.rgb(255,82,82));
        colors.add(Color.rgb(82,255,82));
        colors.add(Color.rgb(158,76,149));
        colors.add(Color.rgb(61,61,61));
        colors.add(Color.rgb(245,91,45));
        colors.add(Color.rgb(183,176,253));

        PieDataSet dataSet = new PieDataSet(pieEntries,"test");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart)findViewById(R.id.PieChart);
        chart.setData(data);
        chart.invalidate();
    }
    public void countItem(){
        DatabaseHelper myDb =new DatabaseHelper(this);
        Cursor cursor =myDb.getAllData();
        int rows_num = cursor.getCount();
        int sum=0;//count sum of money
        if(rows_num!=0){
            cursor.moveToFirst();
            for(int i=0;i<rows_num;i++){
                //_id=0 time=1 term=2 amount=3 comsumeLocation=4
                String item=cursor.getString(2);
                int value=cursor.getInt(3);//amount

                switch (item){
                    case "食":
                        termMoney[0]+=value;
                        break;
                    case "衣":
                        termMoney[1]+=value;
                        break;
                    case "住":
                        termMoney[2]+=value;
                        break;
                    case "行":
                        termMoney[3]+=value;
                        break;
                    case "育":
                        termMoney[4]+=value;
                        break;
                    case "樂":
                        termMoney[5]+=value;
                        break;
                    case "其它":
                        termMoney[6]+=value;
                        break;
                }
                sum+=value;//calculate the sum amount
                cursor.moveToNext();
            }
        }
    }
}
