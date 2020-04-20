package com.example.assignment02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;


/* REFERENCE -  These codes has been developed with the knowledge/and codes taught obtained from the youtube tutorial mentioned below
                             https://www.youtube.com/playlist?list=PLS1QulWo1RIaRdy16cOzBO5Jr6kEagA07     */


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Dictionary.db";
    public static final String TABLE_NAME = "Dictionary_Table";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "PHRASES";

    public static final String TABLE_NAME_2 = "Language_Subscription";
    public static final String Col_2_1 = "ID";
    public static final String Col_2_2 = "LANGUGAES";
    public static final String Col_2_3 = "IsSubscribed";
    public static final String Col_2_4 = "CODE";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        //this will create the db
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating the table with the columns
        db.execSQL(" create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PHRASES TEXT) ");
        db.execSQL(" create table " + TABLE_NAME_2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,LANGUGAES TEXT, IsSubscribed Boolean, CODE TEST) ");

    }

    @Override
    //in case of upgrade this will be called
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME_2);
            onCreate(db);
    }

    //inserting the data into the table column -> Phrases
    public boolean insertPhrase(String phrase){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues tableContent = new ContentValues();
        tableContent.put(Col_2, phrase);
        long results = db.insert(TABLE_NAME, null, tableContent);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }


    //inserting the data into the table column -> Langauges
    public boolean insertDataLanguageSubscription(String Language, String Code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues tableContent2 = new ContentValues();
        tableContent2.put(Col_2_2, Language);
        tableContent2.put(Col_2_3,false);
        tableContent2.put(Col_2_4,Code);
        long results = db.insert(TABLE_NAME_2, null, tableContent2);

        if (results == -1){
            return false;
        }else {
            return true;
        }
    }


    //getting random read right to the DB
    // Storing the data ins the Cursor instance
    public Cursor getAllPhrases(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbData = db.rawQuery(" select * from " + TABLE_NAME , null);
        return dbData;
    }


//getting all added languages from DB to a list
    public Cursor getAllLangauges(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbData = db.rawQuery(" select * from " + TABLE_NAME_2 , null);
        return dbData;
    }


    //Update already existing DB records -> Phrases
    public boolean updatePhrases(int id, String phrase){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValue = new ContentValues();

        updateValue.put(Col_2,phrase);
        db.update(TABLE_NAME,updateValue," ID = ? ", new String[] {String.valueOf(id)});
        return true;
    }


    //Update subscribed languages
    public boolean updateSubscribedLangauges(int id, Boolean subcribedLangs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValue = new ContentValues();

        updateValue.put(Col_2_3,subcribedLangs);
        db.update(TABLE_NAME_2,updateValue," ID = ? ", new String[] {String.valueOf(id)});
        return true;
    }



    //getting all Subscribbed languages from DB to a list
    public Cursor getAllSubLangauges(int IsSelected){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbData = db.rawQuery(" select * from " + TABLE_NAME_2 , new String[]{"IsSelected = ?"});
        return dbData;
    }


}
