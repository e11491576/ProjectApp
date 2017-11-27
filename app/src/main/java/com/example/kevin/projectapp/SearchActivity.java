package com.example.kevin.projectapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends AppCompatActivity {
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView list = (ListView) findViewById(R.id.list);
        Cursor res = myDb.getAllData();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (this,R.layout.finance_row,
                        res,
                        new String[] {"_id","time","term","amount","comsumeLocation"},
                        new int[] {R.id.item_id, R.id.item_time,R.id.item_term,R.id.item_account,R.id.item_location},
                        0);
        list.setAdapter(adapter);
    }

}
