package com.example.kevin.projectapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    DatabaseHelper myDb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewText();
    }

    public void viewText(){
        Cursor res = myDb.getAllData();
        if(res.getCount()==0){
            Toast.makeText(SearchActivity.this,"無資料",Toast.LENGTH_LONG).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("id : "+res.getString(0)+"\n");
            buffer.append("date : "+res.getString(1)+"\n");
            buffer.append("term : "+res.getString(2)+"\n");
            buffer.append("amount : "+res.getString(3)+"\n");
            buffer.append("location : "+res.getString(4)+"\n");
        }
        showMessage("Data",buffer.toString());
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
