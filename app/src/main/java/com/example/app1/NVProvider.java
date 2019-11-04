package com.example.app1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;


public class NVProvider extends ContentProvider {

    static final String TAG = "NVProvider";
    static final String TB_NAME = "nhanvien";
    static final String AUTHORITY = "content://com.example.app1.NVProvider/nhanvien";
    static final Uri CONTENT_URI = Uri.parse(AUTHORITY);

    private SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DataHelper helper = new DataHelper(context);

        db = helper.getWritableDatabase();
        if (db!=null) return true;
        return false;
    }


    @Override
    public Cursor query( Uri uri,  String[] strings,  String s,  String[] strings1,  String s1) {
        Cursor cur = db.rawQuery(s, null);
        return cur;
    }


    @Override
    public String getType( Uri uri) {
        String ret = getContext().getContentResolver().getType(Settings.System.CONTENT_URI);
        Log.d(TAG,"getType returning: "+ret);
        return ret;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        long rowID = db.insert(TB_NAME, null, contentValues);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            Log.d("Thêm thành công", "------");
            return _uri;
        }
        throw new SQLException("Loi " + uri);
    }

    @Override
    public int delete( Uri uri,  String s,  String[] strings) {
        int count = 0;
        count = db.delete(TB_NAME, "id = '" + s + "'", strings);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,  String s,  String[] strings) {
        int count = 0;
        count = db.update(TB_NAME, contentValues, "id = '" + s + "'", null);
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d("Cập nhật thành công", "------");
        return count;
    }
}
