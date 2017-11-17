package com.example.kevin.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1,button2,button3,button4;
    DatabaseHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);


        mydb =new DatabaseHelper(this);
        //construct a database


        button1 =(ImageButton)findViewById(R.id.imageButton1);
        ImageButton nextPageBtn1=(ImageButton)findViewById(R.id.imageButton1);
        nextPageBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
        button2 =(ImageButton)findViewById(R.id.imageButton2);
        ImageButton nextPageBtn2=(ImageButton)findViewById(R.id.imageButton2);
        nextPageBtn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        button3 =(ImageButton)findViewById(R.id.imageButton3);
        ImageButton nextPageBtn3=(ImageButton)findViewById(R.id.imageButton3);
        nextPageBtn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,GraphActivity.class);
                startActivity(intent);
            }
        });
        button4 =(ImageButton)findViewById(R.id.imageButton4);
        ImageButton nextPageBtn4=(ImageButton)findViewById(R.id.imageButton4);
        nextPageBtn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
