package com.belajar.latihan5.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 170076 on 1/14/2018.
 */

public class dbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "coba.db";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_TABLE_NAME = "person";
    private HashMap hp;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table person " +
                        "(id integer primary key, name text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

    public boolean insertNama (String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("name",nama);
        db.insert("person",null,value);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM person where id="+id+"",null);
        return result;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateNama (Integer id, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("name",nama);
        db.update("person",value,"id = ? ", new String[] { Integer.toString(id)} );
        return true;
    }

    public Integer deleteNama (String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("person","name = ? ",new String[] { nama });
    }

    public ArrayList<String> getAllNama(){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result =  db.rawQuery( "select * from person", null );
        result.moveToFirst();

        while(result.isAfterLast() == false){
            array_list.add(result.getString(result.getColumnIndex(CONTACTS_COLUMN_NAME)));
            result.moveToNext();
        }
        return array_list;
    }
}
