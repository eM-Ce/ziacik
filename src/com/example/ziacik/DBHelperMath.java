package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperMath extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBmatematika.db";
    private static final String TABLE_MATH = "priklady";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "meno";
    private static final String KEY_FIRST = "operand1";
    private static final String KEY_SECOND = "operand2";
    private static final String KEY_PLUS = "plus";
    private static final String KEY_MINUS = "minus";
    private static final String KEY_MULTIPLY = "krat";
    private static final String KEY_DIVIDE = "deleno";
    private static final String KEY_PLUS_LIMIT = "pluslimit";
    private static final String KEY_MINUS_LIMIT = "minuslimit";
    private static final String KEY_MULTIPLY_LIMIT = "kratlimit";
    private static final String KEY_DIVIDE_LIMIT = "delenolimit";
    private static final String KEY_PLUS_TIME = "plustime";
    private static final String KEY_MINUS_TIME = "minustime";
    private static final String KEY_MULTIPLY_TIME = "krattime";
    private static final String KEY_DIVIDE_TIME = "delenotime";

	public DBHelperMath(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_MATH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +  KEY_NAME + " TEXT," + KEY_FIRST + " INTEGER," + KEY_SECOND + " INTEGER," 
                + KEY_PLUS + " FLOAT," +  KEY_PLUS_LIMIT + " FLOAT," + KEY_PLUS_TIME + " FLOAT,"
                + KEY_MINUS + " FLOAT," + KEY_MINUS_LIMIT + " FLOAT," + KEY_MINUS_TIME + " FLOAT," 
                + KEY_MULTIPLY + " FLOAT," + KEY_MULTIPLY_LIMIT + " FLOAT,"  + KEY_MULTIPLY_TIME + " FLOAT," 
                + KEY_DIVIDE + " FLOAT," + KEY_DIVIDE_LIMIT + " FLOAT," + KEY_DIVIDE_TIME + " FLOAT" + ")";
        db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATH);
	    onCreate(db);
	}
	
	public void pridajPriklad(Priklad priklad){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, priklad.getName());
	    values.put(KEY_FIRST, priklad.getOp1());
	    values.put(KEY_SECOND, priklad.getOp2());
	    values.put(KEY_PLUS, 0);
	    values.put(KEY_MINUS, 0);
	    values.put(KEY_MULTIPLY, 0);
	    values.put(KEY_DIVIDE, 0);
	    values.put(KEY_PLUS_LIMIT, 0);
	    values.put(KEY_MINUS_LIMIT, 0);
	    values.put(KEY_MULTIPLY_LIMIT, 0);
	    values.put(KEY_DIVIDE_LIMIT, 0);
	    values.put(KEY_PLUS_TIME, 0);
	    values.put(KEY_MINUS_TIME, 0);
	    values.put(KEY_MULTIPLY_TIME, 0);
	    values.put(KEY_DIVIDE_TIME, 0); 
	    
	    db.insert(TABLE_MATH, null, values);
	    db.close();
	}
	
	public List<Priklad> vsetkyPriklady(String name){
		List<Priklad> priklady = new ArrayList<Priklad>();
		String selectQuery = "SELECT  * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "'";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Priklad priklad = new Priklad();
	            priklad.setID(cursor.getInt(0));
	            priklad.setName(cursor.getString(1));
	            priklad.setOp1(cursor.getInt(2));
	            priklad.setOp2(cursor.getInt(3));
	            priklad.setPlus(cursor.getInt(4));
	            priklad.setPlusLimit(cursor.getInt(5));
	            priklad.setPlusTime(cursor.getInt(6));
	            priklad.setMinus(cursor.getInt(7));
	            priklad.setMinusLimit(cursor.getInt(8));
	            priklad.setMinusTime(cursor.getInt(9));
	            priklad.setKrat(cursor.getInt(10));
	            priklad.setKratLimit(cursor.getInt(11));
	            priklad.setKratTime(cursor.getInt(12));
	            priklad.setDeleno(cursor.getInt(13));
	            priklad.setDelenoLimit(cursor.getInt(14));
	            priklad.setDelenoTime(cursor.getInt(15));
	            priklady.add(priklad);
	        } while (cursor.moveToNext());
	    }
		return priklady;
	}
	
	public int upravPocetOdpovedi(Priklad priklad){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_PLUS, priklad.getPlus());
	    values.put(KEY_PLUS_LIMIT, priklad.getPlusLimit());
	    values.put(KEY_PLUS_TIME, priklad.getPlusTime());
	    values.put(KEY_MINUS, priklad.getMinus());
	    values.put(KEY_MINUS_LIMIT, priklad.getMinusLimit());
	    values.put(KEY_MINUS_TIME, priklad.getMinusTime());
	    values.put(KEY_MULTIPLY, priklad.getKrat());
	    values.put(KEY_MULTIPLY_LIMIT, priklad.getKratLimit());
	    values.put(KEY_MULTIPLY_TIME, priklad.getKratTime());
	    values.put(KEY_DIVIDE, priklad.getDeleno());
	    values.put(KEY_DIVIDE_LIMIT, priklad.getDelenoLimit());
	    values.put(KEY_DIVIDE_TIME, priklad.getDelenoTime()); 
	    return db.update(TABLE_MATH, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(priklad.getID()) });		
	}
	
}
