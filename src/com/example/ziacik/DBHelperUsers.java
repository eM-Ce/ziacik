package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperUsers extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBdeti.db";
    private static final String TABLE_KIDS = "deti";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_WORDS = "pocetslovicok";
    private static final String KEY_ARITHMETICS = "pocetprikladov";
    private static final String KEY_LIMITW = "limitslovicka";
    private static final String KEY_LIMITM = "limitmatematika";
    private static final String KEY_MAXPLUS = "maxplus";
    private static final String KEY_MAXMINUS = "maxminus";
    private static final String KEY_NEGATIVERESULT = "zapornyvysledok";
    private static final String KEY_MAXMULTIPLY = "maxnasobenie";
    private static final String KEY_MAXDIVIDE = "maxdelenie";
    

	public DBHelperUsers(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_KIDS + "( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, "
				+ KEY_WORDS + " INTEGER, " + KEY_ARITHMETICS + " INTEGER, " 
                + KEY_LIMITW + " INTEGER, " + KEY_LIMITM + " INTEGER, "
				+ KEY_MAXPLUS + " INTEGER, " + KEY_MAXMINUS + " INTEGER, "
                + KEY_NEGATIVERESULT + " BOOLEAN, " + KEY_MAXMULTIPLY + " INTEGER, "
				+ KEY_MAXDIVIDE + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_KIDS);
	    onCreate(db);
	}
	
	public void pridajDieta(String meno){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
	    values.put(KEY_NAME, meno);
		values.put(KEY_WORDS, 10);
	    values.put(KEY_ARITHMETICS, 10);
	    values.put(KEY_LIMITW, 60);
	    values.put(KEY_LIMITM, 60);
	    values.put(KEY_MAXPLUS, 10);
	    values.put(KEY_MAXMINUS, 10);
	    values.put(KEY_NEGATIVERESULT, false);
	    values.put(KEY_MAXMULTIPLY, 10);
	    values.put(KEY_MAXDIVIDE, 10);
	    
	    db.insert(TABLE_KIDS, null, values);
	    db.close();
	}
	
	public List<String> menaDeti(){
		List<String> mena = new ArrayList<String>();
		String selectQuery = "SELECT DISTINCT " + KEY_NAME +" FROM " + TABLE_KIDS + " ORDER BY " + KEY_NAME + " ASC";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            String meno;
	            meno = cursor.getString(0);
	            mena.add(meno);
	        } while (cursor.moveToNext());
	    }
		return mena;
	}
	
	public int pocetSlovicok(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(2);
	    }
		return -1;
	}
	
	public int pocetPrikladov(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(3);
	    }
		return -1;
	}
	
	public int limitSlovicka(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(4);
	    }
		return -1;
	}
	
	public int limitPriklady(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(5);
	    }
		return -1;
	}
	
	public int maxPlus(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(6);
	    }
		return -1;
	}
	
	public int maxMinus(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(7);
	    }
		return -1;
	}
	
	public int negativnyVysledok(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(8);
	    }
		return -1;
	}
	
	
	public int maxNasobenie(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(9);
	    }
		return -1;
	}
	
	public int maxDelenie(String meno){
		String selectQuery = "SELECT  * FROM " + TABLE_KIDS + " WHERE "+ KEY_NAME +" ='" + meno + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        return cursor.getInt(10);
	    }
		return -1;
	}
	public int upravPocetSlov(String name, int pocet){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_WORDS, pocet);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravPocetPrikladov(String name, int pocet){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_ARITHMETICS, pocet);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravLimitSlovicka(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_LIMITW, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravLimitMatematika(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_LIMITM, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravMaxScitanie(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_MAXPLUS, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravMaxOdcitanie(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_MAXMINUS, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravZapornyVysledok(String name, boolean limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_NEGATIVERESULT, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravMaxNasobenie(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_MAXMULTIPLY, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public int upravMaxDelenie(String name, int limit){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_MAXDIVIDE, limit);
	    return db.update(TABLE_KIDS, values, KEY_NAME + " = ?",
	            new String[] { name });		
	}
	
	public void vymazDieta(String dieta){
		//pridat mazanie slovicok a prikladov z DB!!!		
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_KIDS, KEY_NAME + " = ?",
	            new String[] { dieta });
	    db.close();
		
	}

}
