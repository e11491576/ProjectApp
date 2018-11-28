package com.example.kevin.projectapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    ListView list;
    ImageButton btnDelete;
    TextView datepick;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
    Date curDate = new Date(System.currentTimeMillis()); // 獲取當前時間
    String date = formatter.format(curDate);
    DatabaseHelper myDb = new DatabaseHelper(this);
    Calendar m_Calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Cursor res = myDb.getDataByDate(date);
        list = (ListView) findViewById(R.id.list);
        datepick = (TextView) findViewById(R.id.datepick);
        datepick.setText(date);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (this, R.layout.finance_row,
                        res,
                        new String[]{"_id", "time", "term", "amount", "comsumeLocation"},
                        new int[]{R.id.item_id, R.id.item_time, R.id.item_term, R.id.item_account, R.id.item_location},
                        0);
        list.setAdapter(adapter);

        datepick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == datepick.getId()){
            DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener()
            {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                {
                    m_Calendar.set(Calendar.YEAR, year);
                    m_Calendar.set(Calendar.MONTH, month);
                    m_Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "yyyy年MM月dd日";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                    curDate = m_Calendar.getTime();
                    date = formatter.format(curDate);
                    datepick.setText(sdf.format(m_Calendar.getTime()));
                    //更改內容
                    Cursor res = myDb.getDataByDate(date);
                    showContent(res);
                }
            };
            DatePickerDialog dialog =
                    new DatePickerDialog(SearchActivity.this,
                            datepicker,
                            m_Calendar.get(Calendar.YEAR),
                            m_Calendar.get(Calendar.MONTH),
                            m_Calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
    }

    public void myClickHandler(View v) {
        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout vwParentRow = (ConstraintLayout) v.getParent();
                        TextView child = (TextView) vwParentRow.getChildAt(0);// To get Item ID
                        Integer deletedRows = myDb.deleteData(child.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText(SearchActivity.this, "資料刪除成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            intent.setClass(SearchActivity.this, SearchActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SearchActivity.this, "資料刪除失敗，請重新輸入", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void showContent(Cursor res){
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (this, R.layout.finance_row,
                        res,
                        new String[]{"_id", "time", "term", "amount", "comsumeLocation"},
                        new int[]{R.id.item_id, R.id.item_time, R.id.item_term, R.id.item_account, R.id.item_location},
                        0);
        list.setAdapter(adapter);
    }


}


