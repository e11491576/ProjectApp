package com.example.kevin.projectapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends AppCompatActivity {
    Button btnDelete,btnUpdate;
    EditText editTextId;
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ListView list = (ListView) findViewById(R.id.list);
        Cursor res = myDb.getAllData();
        btnDelete=(Button)findViewById(R.id.btndelete);
        btnUpdate=(Button)findViewById(R.id.btnupdate);
        editTextId=(EditText)findViewById(R.id.delete_id);

        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(SearchActivity.this, AddActivity.class);
                        startActivity(intent);
                    }
                });
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (this,R.layout.finance_row,
                        res,
                        new String[] {"_id","time","term","amount","comsumeLocation"},
                        new int[] {R.id.item_id, R.id.item_time,R.id.item_term,R.id.item_account,R.id.item_location},
                        0);
        list.setAdapter(adapter);

        DeleteData();
    }
    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
                        if(deletedRows > 0)
                        {
                            Toast.makeText(SearchActivity.this, "資料刪除成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(SearchActivity.this,MainActivity.class);
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
