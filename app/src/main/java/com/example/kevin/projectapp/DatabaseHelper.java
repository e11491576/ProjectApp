package com.example.kevin.projectapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by programming on 2017/11/14.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    // tableName

    public static final String tableName = "account";
    //id
    public static final String keyID = "_id";
    //remain _column
    public static final String dataTime_column ="time";
    public static final String term_column = "term";
    public static final String amount_column="amount";
    public static final String comsumeLocation_column="comsumeLocation";

    public static final String DB_name = "myaccount.db";
        public DatabaseHelper(Context context) {
            super(context, DB_name, null, VERSION);
          //  SQLiteDatabase db = this.getWritableDatabase();
    }


    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
    String str = formatter.format(curDate);


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName +" (_id INTEGER PRIMARY KEY AUTOINCREMENT,time TEXT,term TEXT,amount INTEGER NOT NULL, comsumeLocation TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }
    public boolean insertData (String dataTime, String term, String amount, String comsumeLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dataTime_column,dataTime);
        contentValues.put(term_column,term);
        contentValues.put(amount_column,amount);
        contentValues.put(comsumeLocation_column,comsumeLocation);
        long result = db.insert(tableName,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sortOrder = null;
        Cursor res = db.query(tableName,null,null,null,null,null,null);
        return res;
    }
}
