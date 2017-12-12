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

    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
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

        addDataSet();
        showcountItem();
    }

    private void addDataSet() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0;i < yData.length;i++){
            pieEntries.add(new PieEntry(yData[i],xData[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"test");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart)findViewById(R.id.PieChart);
        chart.setData(data);
        chart.invalidate();
    }
    public void showcountItem(){
        DatabaseHelper myDb =new DatabaseHelper(this);
        Cursor cursor =myDb.getAllData();
        int rows_num = cursor.getCount();
        if(rows_num!=0){
            cursor.moveToFirst();
            for(int i=0;i<rows_num;i++){
                int value=cursor.getInt(3);
                if(value!=0)
                Toast.makeText(GraphActivity.this, ""+value, Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(GraphActivity.this, "XX", Toast.LENGTH_LONG).show();
                cursor.moveToNext();
            }

        }
    }
}
