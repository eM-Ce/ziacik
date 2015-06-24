package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBslovicka.db";
    private static final String TABLE_WORDS = "slovicka";
    private static final String KEY_ID = "id";
    private static final String KEY_EN = "english";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SK = "slovak";
    private static final String KEY_TIME = "timestamp";
    private static final String KEY_RIGHT = "right";
    private static final String KEY_NAME = "name";
    private static final String KEY_HELP = "help";
    private static final String KEY_LIMIT = "timelimit";
    private static final String KEY_SOLVETIME = "solvetime";
    

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_WORDS + "( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT, " + KEY_CATEGORY + " TEXT, " + KEY_EN + " TEXT, " 
                + KEY_SK + " TEXT, " + KEY_HELP + " TEXT, " + KEY_TIME + " LONG, " + KEY_LIMIT + " FLOAT, " + KEY_SOLVETIME + " FLOAT, " + KEY_RIGHT + " FLOAT" + ")";
        db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
	    onCreate(db);
	}
	
	public void pridajSlovo(Slovo slovo, String kategoria){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
	    values.put(KEY_NAME, slovo.getName());
		values.put(KEY_CATEGORY, kategoria);
	    values.put(KEY_EN, slovo.getEn());
	    values.put(KEY_SK, slovo.getSk());
	    values.put(KEY_HELP, slovo.getHelp());
	    values.put(KEY_TIME, 0);
	    values.put(KEY_LIMIT, 0);
	    values.put(KEY_SOLVETIME, 0);
	    values.put(KEY_RIGHT, 1);
	    
	    db.insert(TABLE_WORDS, null, values);
	    db.close();
	}
	
	/*public Slovo ziskajSlovo(int id){
		return null;
	}*/
	
	public List<Slovo> vsetkySlova(String name, int pocetlimit){
		List<Slovo> vsetkySlova = new ArrayList<Slovo>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE " + KEY_NAME + "='" + name + "' ORDER BY "+ KEY_RIGHT + " ASC," + KEY_TIME + " ASC";;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(cursor.getInt(0));
	            slovo.setName(cursor.getString(1));
	            slovo.setEn(cursor.getString(3));
	            slovo.setSk(cursor.getString(4));
	            slovo.setHelp(cursor.getString(5));
	            slovo.setTime(cursor.getLong(6));
	            slovo.setLimit(cursor.getFloat(7));
	            slovo.setSolveTime(cursor.getFloat(8));
	            slovo.setRight(cursor.getFloat(9));
	            vsetkySlova.add(slovo);
	        } while (cursor.moveToNext());
	    }
		return vsetkySlova;
	}
	
	public List<String> kategorie(String name){
		List<String> kategorie = new ArrayList<String>();
		String selectQuery = "SELECT DISTINCT " + KEY_CATEGORY +" FROM " + TABLE_WORDS + " WHERE " + KEY_NAME + "='" + name + "' ORDER BY " + KEY_CATEGORY + " ASC";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            String kategoria;
	            kategoria = cursor.getString(0);
	            kategorie.add(kategoria);
	        } while (cursor.moveToNext());
	    }
		return kategorie;
	}
	
	public List<Slovo> slovaZoSuboru(String kategoria, String name, int pocetlimit){
		List<Slovo> slova = new ArrayList<Slovo>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE "+ KEY_CATEGORY +" ='" + kategoria + "' AND " + KEY_NAME + "='" + name + "' ORDER BY "+ KEY_RIGHT + " ASC LIMIT " + pocetlimit;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(cursor.getInt(0));
	            slovo.setName(cursor.getString(1));
	            slovo.setEn(cursor.getString(3));
	            slovo.setSk(cursor.getString(4));
	            slovo.setHelp(cursor.getString(5));
	            slovo.setTime(cursor.getLong(6));
	            slovo.setLimit(cursor.getFloat(7));
	            slovo.setSolveTime(cursor.getFloat(8));
	            slovo.setRight(cursor.getFloat(9));
	            slova.add(slovo);
	        } while (cursor.moveToNext());
	    }
	    List<Slovo> slova2 = new ArrayList<Slovo>();
		String selectQuery2 = "SELECT  * FROM " + TABLE_WORDS + " WHERE "+ KEY_CATEGORY +" ='" + kategoria + "' AND " + KEY_NAME + "='" + name + "' ORDER BY "+ KEY_TIME + " ASC LIMIT " + pocetlimit;
		SQLiteDatabase db2 = this.getWritableDatabase();
	    Cursor cursor2 = db2.rawQuery(selectQuery2, null);
	    if (cursor2.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(cursor2.getInt(0));
	            slovo.setName(cursor2.getString(1));
	            slovo.setEn(cursor2.getString(3));
	            slovo.setSk(cursor2.getString(4));
	            slovo.setHelp(cursor2.getString(5));
	            slovo.setTime(cursor2.getLong(6));
	            slovo.setLimit(cursor2.getFloat(7));
	            slovo.setSolveTime(cursor2.getFloat(8));
	            slovo.setRight(cursor2.getFloat(9));
	            slova2.add(slovo);
	        } while (cursor2.moveToNext());
	    }
	    List<Slovo> slova3 = new ArrayList<>();
	    int j = Math.min(slova.size(), slova2.size());
	    int i = 0;
	    while (i<j && i<pocetlimit){
	    	slova3.add(slova.get(i));
	    	slova3.add(slova2.get(i));
	    	i++;
	    }
	    if (i<pocetlimit) {
	    	if (slova.size() < slova2.size()){
	    		while (i<pocetlimit && i<slova2.size()){
	    			slova3.add(slova2.get(i));
	    			i++;
	    		}
	    	} else if (slova.size() > slova2.size()){
	    		while (i<pocetlimit && i<slova.size()){
	    			slova3.add(slova.get(i));
	    			i++;
	    		}
	    	} 
	    }
		return slova3;
	}
	
	public List<Slovo> slovaZoSuboru(String kategoria, String name){
		List<Slovo> slova = new ArrayList<Slovo>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE "+ KEY_CATEGORY +" ='" + kategoria + "' AND " + KEY_NAME + "='" + name + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(cursor.getInt(0));
	            slovo.setName(cursor.getString(1));
	            slovo.setEn(cursor.getString(3));
	            slovo.setSk(cursor.getString(4));
	            slovo.setHelp(cursor.getString(5));
	            slovo.setTime(cursor.getLong(6));
	            slovo.setLimit(cursor.getFloat(7));
	            slovo.setSolveTime(cursor.getFloat(8));
	            slovo.setRight(cursor.getFloat(9));
	            slova.add(slovo);
	        } while (cursor.moveToNext());
	    }
		return slova;
	}
	
	public int upravSlovo(Slovo slovo){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_EN, slovo.getEn());
	    values.put(KEY_SK, slovo.getSk());
	    values.put(KEY_HELP, slovo.getHelp());
	    return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(slovo.getID()) });		
	}
	
	public int upravPocetOdpovedi(Slovo slovo){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TIME, slovo.getTime());
	    values.put(KEY_RIGHT, slovo.getRight());
	    values.put(KEY_SOLVETIME, slovo.getSolveTime());
	    values.put(KEY_LIMIT, slovo.getLimit());
	    return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(slovo.getID()) });		
	}
	
	public void vymazSlovo(Slovo slovo){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_WORDS, KEY_ID + " = ?",
	            new String[] { String.valueOf(slovo.getID()) });
	    db.close();
		
	}

}
