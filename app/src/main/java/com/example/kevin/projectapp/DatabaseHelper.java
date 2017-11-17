package com.example.kevin.projectapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by programming on 2017/11/14.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_name = "myaccount.db";
    public static final int VERSION = 1;

    // tableName
    public static final String tableName = "account";
    //id
    public static final String keyID = "_id";
    //remain _column
    public static final String dataTime_column = "time";
    public static final String term_column = "term";
    public static final String amount_column="amount";
    public static final String comsumeLocation_column="comsumeLocation";

    //create table
    public static final String createTable =
            "CREATE TABLE" + tableName + "(" + keyID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    dataTime_column + "DEFAULT CURRENT_TIMESTAMP," + term_column + "TEXT," +
                    amount_column + "INTEGER NOT NULL," + comsumeLocation_column + "TEXT)";


    //private static SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_name, null, VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    /*public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DatabaseHelper(context,DB_name ,null, VERSION).getWritableDatabase();
        }

        return database;
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }

}
