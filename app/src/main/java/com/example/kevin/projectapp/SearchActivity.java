package com.example.kevin.projectapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends AppCompatActivity {
    ListView list;
    ImageButton btnDelete;
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Cursor res = myDb.getAllData();
        list = (ListView) findViewById(R.id.list);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (this,R.layout.finance_row,
                        res,
                        new String[] {"_id","time","term","amount","comsumeLocation"},
                        new int[] {R.id.item_id, R.id.item_time,R.id.item_term,R.id.item_account,R.id.item_location},
                        0);
        list.setAdapter(adapter);

    }


    public void myClickHandler(View v) {

        v.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConstraintLayout vwParentRow = (ConstraintLayout)v.getParent();
                        TextView child = (TextView)vwParentRow.getChildAt(0);// To get Item ID
                        Integer deletedRows = myDb.deleteData(child.getText().toString());
                        if(deletedRows > 0)
                        {
                            Toast.makeText(SearchActivity.this, "資料刪除成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(SearchActivity.this,SearchActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(SearchActivity.this, "資料刪除失敗，請重新輸入", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

}


