package com.rs.notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Mydatabase extends SQLiteOpenHelper {

    public static final String DBNAME = "notes";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "employee_details";
    public static final String COL1 = "ID";
    public static final String COL2 = "TITLE";
    public static final String COL3 = "DESCP";
    public static final String COL4 = "CREATED_AT";
    public static final String COL5 = "UPDATED_AT";


    public Mydatabase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String qry="Create table "+TABLE_NAME+"("+COL1+" INTEGER primary key AUTOINCREMENT,"+COL2+" text," +
                ""+COL3+" text,"+COL4+" text,"+COL5+" text)";
        db.execSQL(qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
