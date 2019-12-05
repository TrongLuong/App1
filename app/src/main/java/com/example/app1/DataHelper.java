package com.example.app1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "nhanvienDB";

    public DataHelper( Context context) {
            super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create table nhanvien(id nvarchar(50) primary key," +
                " name varchar(30) not null," +
                " address varchar(20) not null )");
        ContentValues values = new ContentValues();
        values.put("id", "113");
        values.put("name", "Trong");
        values.put("address", "TN");
        sqLiteDatabase.insert("nhanvien", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists nhanvien");
        onCreate(sqLiteDatabase);
    }
}
