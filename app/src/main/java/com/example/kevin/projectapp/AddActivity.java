package com.example.kevin.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTime, editTerm, editAmount, editLocation;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDb = new DatabaseHelper(this);

        editTime = (EditText) findViewById(R.id.editText);
        editTerm = (EditText) findViewById(R.id.editText2);
        editAmount = (EditText) findViewById(R.id.editText3);
        editLocation = (EditText) findViewById(R.id.editText4);
        btnSubmit = (Button) findViewById(R.id.button2);

        setContentView(R.layout.activity_add);
    }

    public void AddData() {
        btnSubmit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                boolean isInserted =  myDb.insertData(editTime.getText(),toString(),
                            editTerm.getText().toString(),
                            editAmount.getText().toString(),
                            editLocation.getText().toString());
                    }
                }
        );
    }
}
