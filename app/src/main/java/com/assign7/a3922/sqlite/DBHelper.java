package com.assign7.a3922.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class myDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StudentsDB.db";
    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public myDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + COLUMN_ID + " text primary key, " + COLUMN_NAME + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("name", name);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor searchData(String searchInput) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where _id = ?" + searchInput, null);
            return cursor;
    }

    public boolean updateData(String id, String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("name", name);
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where _id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.update(TABLE_NAME, contentValues, "_id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deleteData(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = DB.rawQuery("Select * from "+TABLE_NAME+" where _id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.delete(TABLE_NAME, "_id=?", new String[]{id});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getData () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

}