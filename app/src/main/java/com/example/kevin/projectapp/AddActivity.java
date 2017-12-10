package com.example.kevin.projectapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTerm, editAmount, editLocation,editTextID;
    Spinner itemSpinner;
    Button btnSubmit,btnUpdate;
    TextView currentTime;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
    String str = formatter.format(curDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDb = new DatabaseHelper(this);


      //  editTerm = (EditText) findViewById(R.id.editText2);
        editAmount = (EditText) findViewById(R.id.editText3);
        editLocation = (EditText) findViewById(R.id.editText4);
        editTextID=(EditText)findViewById(R.id.edittext);
        btnSubmit = (Button) findViewById(R.id.button2);
        btnUpdate=(Button)findViewById(R.id.btnupdate);
        currentTime=(TextView)findViewById(R.id.textView);

        itemSpinner=(Spinner)findViewById(R.id.spinner_item);
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.item_array, android.R.layout.simple_spinner_item);
        itemSpinner.setAdapter(nAdapter);

        currentTime.setText(str);//set_curret_time
        AddData();
        UpdateData();
    }

    public void AddData() {
        btnSubmit.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                boolean isInserted =  myDb.insertData(currentTime.getText().toString(),
                            itemSpinner.getSelectedItem().toString(),
                            editAmount.getText().toString(),
                            editLocation.getText().toString());
                        if(isInserted=true) {
                            Toast.makeText(AddActivity.this, "帳目輸入成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(AddActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(AddActivity.this,"帳目輸入失敗",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextID.getText().toString(),
                                currentTime.getText().toString(),
                                itemSpinner.getSelectedItem().toString(),
                                editAmount.getText().toString(),
                                editLocation.getText().toString()
);
                        if(isUpdate == true) {
                            Toast.makeText(AddActivity.this, "資料修改成功", Toast.LENGTH_LONG).show();
                            Intent intent =new Intent();
                            intent.setClass(AddActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AddActivity.this, "資料修改失敗 請重新輸入", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
 /*   public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Name :"+ res.getString(1)+"\n");
                            buffer.append("Surname :"+ res.getString(2)+"\n");
                            buffer.append("Marks :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
*/
}
