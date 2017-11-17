package com.example.kevin.projectapp;

/**
 * Created by programming on 2017/11/14.
 */

public class itemDAO {
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
                    dataTime_column + "DEFAULT CURRENT_TIMESTAMP" + term_column + ""


}
