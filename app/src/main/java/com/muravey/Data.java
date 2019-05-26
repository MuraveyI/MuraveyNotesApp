package com.muravey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Data extends SQLiteOpenHelper {


    public static final String DB_NAME = "ToDoDB";
    public static final String TABLE_NAME = "ItemsToDo";
    public static final String COLUMN_NAME = "Items";
    public static final int DB_VERSION = 1;


    public Data(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //creating table and giving them id
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (ID INTEGER  PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);", TABLE_NAME, COLUMN_NAME);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXIST  %s", TABLE_NAME);
        db.execSQL(query);
        onCreate(db);

    }

    //inserting new items

    public void inserNewItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteItem(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + "=?", new String[]{item});
        db.close();
    }


    public ArrayList<String> getToDoList() {
        ArrayList<String> todoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_NAME);
            todoList.add(cursor.getString(index));

        }
        cursor.close();
        db.close();
        return todoList;
    }


}

