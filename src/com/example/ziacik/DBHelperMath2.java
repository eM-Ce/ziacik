package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
import android.util.Log;

public class DBHelperMath2 extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DBmatematika2.db";
    private static final String TABLE_MATH = "priklady";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "meno";
    private static final String KEY_FIRST = "operand1";
    private static final String KEY_SECOND = "operand2";
    private static final String KEY_OPERATION = "operacia";
    private static final String KEY_RIGHT = "right";
    private static final String KEY_USETIME = "usetime";
    private static final String KEY_TIME = "time";
    private static final String KEY_LIMIT = "timelimit";

	public DBHelperMath2(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_MATH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +  KEY_NAME + " TEXT,"
				+ KEY_FIRST + " INTEGER," + KEY_SECOND + " INTEGER," 
                + KEY_OPERATION + " TEXT," +  KEY_USETIME + " LONG,"
                + KEY_RIGHT + " FLOAT," + KEY_TIME + " FLOAT," + KEY_LIMIT + " FLOAT" + ")";
        db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATH);
	    onCreate(db);
	}
	
	public void pridajPriklad(Priklad2 priklad){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, priklad.getName());
	    values.put(KEY_FIRST, priklad.getOp1());
	    values.put(KEY_SECOND, priklad.getOp2());
	    values.put(KEY_OPERATION, String.valueOf(priklad.getOperation()));
	    values.put(KEY_USETIME, 0);
	    values.put(KEY_RIGHT, 1);
	    values.put(KEY_TIME, 0);
	    values.put(KEY_LIMIT, 0); 
	    db.insert(TABLE_MATH, null, values);
	    db.close();
	}
	
	public void pridajPriklady(String meno){
		int pocet = 0;
		int pocet2 = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		ContentValues values = new ContentValues();
		for(int i = 1; i <= 100; i++){
			for(int j = 1; j <= 100; j++){
				//Log.d("Vkladanie prikladov", Integer.toString(i) + " " + Integer.toString(j));
				Priklad2 priklad = new Priklad2();
				priklad.setName(meno);
				priklad.setOp1(i);
				priklad.setOp2(j);
				priklad.setOperation('+');
				values.put(KEY_NAME, priklad.getName());
			    values.put(KEY_FIRST, priklad.getOp1());
			    values.put(KEY_SECOND, priklad.getOp2());
			    values.put(KEY_OPERATION, String.valueOf(priklad.getOperation()));
			    values.put(KEY_USETIME, 0);
			    values.put(KEY_RIGHT, 1);
			    values.put(KEY_TIME, 0);
			    values.put(KEY_LIMIT, 0); 
			    db.insert(TABLE_MATH, null, values);
				priklad = new Priklad2();
				priklad.setName(meno);
				priklad.setOp1(i);
				priklad.setOp2(j);
				priklad.setOperation('-');
				values.put(KEY_NAME, priklad.getName());
			    values.put(KEY_FIRST, priklad.getOp1());
			    values.put(KEY_SECOND, priklad.getOp2());
			    values.put(KEY_OPERATION, String.valueOf(priklad.getOperation()));
			    values.put(KEY_USETIME, 0);
			    values.put(KEY_RIGHT, 1);
			    values.put(KEY_TIME, 0);
			    values.put(KEY_LIMIT, 0); 
			    db.insert(TABLE_MATH, null, values);
			    if (i*j < 1000) {
			    	priklad = new Priklad2();
					priklad.setName(meno);
					priklad.setOp1(i);
					priklad.setOp2(j);
					priklad.setOperation('*');
					values.put(KEY_NAME, priklad.getName());
			    	values.put(KEY_FIRST, priklad.getOp1());
			    	values.put(KEY_SECOND, priklad.getOp2());
			    	values.put(KEY_OPERATION, String.valueOf(priklad.getOperation()));
			    	values.put(KEY_USETIME, 0);
			    	values.put(KEY_RIGHT, 1);
			    	values.put(KEY_TIME, 0);
			    	values.put(KEY_LIMIT, 0); 
			    	db.insert(TABLE_MATH, null, values);
			    	pocet2++;
			    	Log.d("Nasobenie mensie", "i: "+ Integer.toString(i) + " j: " + Integer.toString(j) + " pocet " + Integer.toString(pocet2));
			    }
				if (i%j ==0){
					priklad = new Priklad2();
					priklad.setName(meno);
					priklad.setOp1(i);
					priklad.setOp2(j);
					priklad.setOperation('/');
					values.put(KEY_NAME, priklad.getName());
			    	values.put(KEY_FIRST, priklad.getOp1());
			    	values.put(KEY_SECOND, priklad.getOp2());
			    	values.put(KEY_OPERATION, String.valueOf(priklad.getOperation()));
			    	values.put(KEY_USETIME, 0);
			    	values.put(KEY_RIGHT, 1);
			    	values.put(KEY_TIME, 0);
			    	values.put(KEY_LIMIT, 0); 
			    	db.insert(TABLE_MATH, null, values);
			    	pocet++;
			    	Log.d("Sudelitelne", "i: "+ Integer.toString(i) + " j: " + Integer.toString(j) + " pocet " + Integer.toString(pocet));
			    }
			}
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	    db.close();
	}
	
	public List<Priklad2> vsetkyPriklady(String name){
		List<Priklad2> priklady = new ArrayList<Priklad2>();
		String selectQuery = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' LIMIT 20";
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Priklad2 priklad = new Priklad2();
	            priklad.setID(cursor.getInt(0));
	            priklad.setName(cursor.getString(1));
	            priklad.setOp1(cursor.getInt(2));
	            priklad.setOp2(cursor.getInt(3));
	            priklad.setOperation(cursor.getString(4).charAt(0));
	            priklad.setUsetime(cursor.getLong(5));
	            priklad.setRight(cursor.getFloat(6));
	            priklad.setTime(cursor.getInt(7));
	            priklad.setLimit(cursor.getInt(8));
	            priklady.add(priklad);
	        } while (cursor.moveToNext());
	    }
		return priklady;
	}
	
	public List<Priklad2> prikladyPrvyOperand(String name, int value){
		List<Priklad2> priklady = new ArrayList<Priklad2>();
		String selectQuery = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' AND " + KEY_FIRST + " = " + value;
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Priklad2 priklad = new Priklad2();
	            priklad.setID(cursor.getInt(0));
	            priklad.setName(cursor.getString(1));
	            priklad.setOp1(cursor.getInt(2));
	            priklad.setOp2(cursor.getInt(3));
	            priklad.setOperation(cursor.getString(4).charAt(0));
	            priklad.setUsetime(cursor.getLong(5));
	            priklad.setRight(cursor.getFloat(6));
	            priklad.setTime(cursor.getInt(7));
	            priklad.setLimit(cursor.getInt(8));
	            priklady.add(priklad);
	        } while (cursor.moveToNext());
	    }
		return priklady;
	}
	
	public List<Priklad2> prikladyPodlaNastaveni(String name, int maxplus, int maxminus, boolean zaporny, int maxnasobenie, int maxdelenie, int pocet){
		List<Priklad2> priklady = new ArrayList<Priklad2>();
		String selectQuery = "";
		if (zaporny){
			selectQuery = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' AND ((" 
					+ KEY_OPERATION + "='+' AND " + KEY_FIRST + "<=" + maxplus + " AND " + KEY_SECOND + "<=" + maxplus + ") OR (" 
					+ KEY_OPERATION + "='-' AND " + KEY_FIRST + "<=" + maxminus + " AND " + KEY_SECOND + "<=" + maxminus + ") OR ("
					+ KEY_OPERATION + "='*' AND " + KEY_FIRST + "<=" + maxnasobenie + " AND " + KEY_SECOND + "<=" + maxnasobenie + ") OR ("
					+ KEY_OPERATION + "='/' AND " + KEY_FIRST + "<=" + maxdelenie + " AND " + KEY_SECOND + "<=" + maxdelenie + ")) ORDER BY " + KEY_RIGHT + " LIMIT " + pocet;
		} else {
			selectQuery = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' AND ((" 
					+ KEY_OPERATION + "='+' AND " + KEY_FIRST + "<=" + maxplus + " AND " + KEY_SECOND + "<=" + maxplus + ") OR (" 
					+ KEY_OPERATION + "='-' AND " + KEY_FIRST + "<=" + maxminus + " AND " + KEY_SECOND + "<=" + maxminus + " AND " + KEY_FIRST + ">=" + KEY_SECOND + ") OR ("
					+ KEY_OPERATION + "='*' AND " + KEY_FIRST + "<=" + maxnasobenie + " AND " + KEY_SECOND + "<=" + maxnasobenie + ") OR ("
					+ KEY_OPERATION + "='/' AND " + KEY_FIRST + "<=" + maxdelenie + " AND " + KEY_SECOND + "<=" + maxdelenie + ")) ORDER BY " + KEY_RIGHT + " LIMIT " + pocet;
		}
		SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	            Priklad2 priklad = new Priklad2();
	            priklad.setID(cursor.getInt(0));
	            priklad.setName(cursor.getString(1));
	            priklad.setOp1(cursor.getInt(2));
	            priklad.setOp2(cursor.getInt(3));
	            priklad.setOperation(cursor.getString(4).charAt(0));
	            priklad.setUsetime(cursor.getLong(5));
	            priklad.setRight(cursor.getFloat(6));
	            priklad.setTime(cursor.getInt(7));
	            priklad.setLimit(cursor.getInt(8));
	            priklady.add(priklad);
	        } while (cursor.moveToNext());
	    }
	    List<Priklad2> priklady2 = new ArrayList<Priklad2>();
		String selectQuery2 = "";
		if (zaporny){
			selectQuery2 = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' AND ((" 
					+ KEY_OPERATION + "='+' AND " + KEY_FIRST + "<=" + maxplus + " AND " + KEY_SECOND + "<=" + maxplus + ") OR (" 
					+ KEY_OPERATION + "='-' AND " + KEY_FIRST + "<=" + maxminus + " AND " + KEY_SECOND + "<=" + maxminus + ") OR ("
					+ KEY_OPERATION + "='*' AND " + KEY_FIRST + "<=" + maxnasobenie + " AND " + KEY_SECOND + "<=" + maxnasobenie + ") OR ("
					+ KEY_OPERATION + "='/' AND " + KEY_FIRST + "<=" + maxdelenie + " AND " + KEY_SECOND + "<=" + maxdelenie + ")) ORDER BY " + KEY_USETIME + " ASC LIMIT " + pocet;
		} else {
			selectQuery2 = "SELECT * FROM " + TABLE_MATH + " WHERE "+ KEY_NAME +" ='" + name + "' AND ((" 
					+ KEY_OPERATION + "='+' AND " + KEY_FIRST + "<=" + maxplus + " AND " + KEY_SECOND + "<=" + maxplus + ") OR (" 
					+ KEY_OPERATION + "='-' AND " + KEY_FIRST + "<=" + maxminus + " AND " + KEY_SECOND + "<=" + maxminus + " AND " + KEY_FIRST + ">=" + KEY_SECOND + ") OR ("
					+ KEY_OPERATION + "='*' AND " + KEY_FIRST + "<=" + maxnasobenie + " AND " + KEY_SECOND + "<=" + maxnasobenie + ") OR ("
					+ KEY_OPERATION + "='/' AND " + KEY_FIRST + "<=" + maxdelenie + " AND " + KEY_SECOND + "<=" + maxdelenie + ")) ORDER BY " + KEY_USETIME + " ASC LIMIT " + pocet;
		}
		SQLiteDatabase db2 = this.getWritableDatabase();
	    Cursor cursor2 = db2.rawQuery(selectQuery2, null);
	    if (cursor2.moveToFirst()) {
	        do {
	            Priklad2 priklad = new Priklad2();
	            priklad.setID(cursor2.getInt(0));
	            priklad.setName(cursor2.getString(1));
	            priklad.setOp1(cursor2.getInt(2));
	            priklad.setOp2(cursor2.getInt(3));
	            priklad.setOperation(cursor2.getString(4).charAt(0));
	            priklad.setUsetime(cursor2.getLong(5));
	            priklad.setRight(cursor2.getFloat(6));
	            priklad.setTime(cursor2.getInt(7));
	            priklad.setLimit(cursor2.getInt(8));
	            priklady2.add(priklad);
	        } while (cursor2.moveToNext());
	    }
	    List<Priklad2> priklady3 = new ArrayList<Priklad2>();
	    int j = Math.min(priklady.size(), priklady2.size());
	    int i = 0;
	    while (i<j && i<pocet){
	    	priklady3.add(priklady.get(i));
	    	priklady3.add(priklady2.get(i));
	    	i++;
	    }
	    if (i<pocet) {
	    	if (priklady.size() < priklady2.size()){
	    		while (i<pocet && i<priklady2.size()){
	    			priklady3.add(priklady2.get(i));
	    			i++;
	    		}
	    	} else if (priklady.size() > priklady2.size()){
	    		while (i<pocet && i<priklady.size()){
	    			priklady3.add(priklady.get(i));
	    			i++;
	    		}
	    	} 
	    }
		return priklady3;
	}
	
	public int upravPocetOdpovedi(Priklad2 priklad){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_USETIME, priklad.getUsetime());
	    values.put(KEY_RIGHT, priklad.getRight());
	    values.put(KEY_TIME, priklad.getTime());
	    values.put(KEY_LIMIT, priklad.getLimit()); 
	    return db.update(TABLE_MATH, values, KEY_ID + " = ?",
	            new String[] { String.valueOf(priklad.getID()) });		
	}
	
	public void vymazDieta(String dieta){
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_MATH, KEY_NAME + " = ?",
	            new String[] { dieta });
	    db.close();
		
	}
	
}
