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

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT," + KEY_EN + " TEXT," 
                + KEY_SK + " TEXT," + KEY_TIME + " INTEGER," + KEY_RIGHT + " INTEGER" + ")";
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
		values.put(KEY_CATEGORY, kategoria);
	    values.put(KEY_EN, slovo.getEn());
	    values.put(KEY_SK, slovo.getSk());
	    values.put(KEY_TIME, 0);
	    values.put(KEY_RIGHT, 0);
	    
	    db.insert(TABLE_WORDS, null, values);
	    db.close();
	}
	
	/*public Slovo ziskajSlovo(int id){
		return null;
	}*/
	
	public List<Slovo> vsetkySlova(){
		List<Slovo> vsetkySlova = new ArrayList<Slovo>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(Integer.parseInt(cursor.getString(0)));
	            slovo.setEn(cursor.getString(2));
	            slovo.setSk(cursor.getString(3));
	            slovo.setTime(Integer.parseInt(cursor.getString(4)));
	            slovo.setRight(Integer.parseInt(cursor.getString(5)));
	            vsetkySlova.add(slovo);
	        } while (cursor.moveToNext());
	    }
		return vsetkySlova;
	}
	
	public List<String> kategorie(){
		List<String> kategorie = new ArrayList<String>();
		String selectQuery = "SELECT DISTINCT " + KEY_CATEGORY +" FROM " + TABLE_WORDS + " ORDER BY " + KEY_CATEGORY + " ASC";
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
	
	public List<Slovo> slovaZoSuboru(String kategoria){
		List<Slovo> slova = new ArrayList<Slovo>();
		String selectQuery = "SELECT  * FROM " + TABLE_WORDS + " WHERE "+ KEY_CATEGORY +" ='" + kategoria + "' ORDER BY "+ KEY_RIGHT + " ASC," + KEY_TIME + " ASC";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Slovo slovo = new Slovo();
	            slovo.setID(Integer.parseInt(cursor.getString(0)));
	            slovo.setEn(cursor.getString(2));
	            slovo.setSk(cursor.getString(3));
	            slovo.setTime(Integer.parseInt(cursor.getString(4)));
	            slovo.setRight(Integer.parseInt(cursor.getString(5)));
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
	    return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(slovo.getID()) });		
	}
	
	public int upravPocetOdpovedi(Slovo slovo){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_RIGHT, slovo.getRight());
	    values.put(KEY_TIME, slovo.getTime());
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
