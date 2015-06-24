package com.example.ziacik;

import android.util.Log;

public class Slovo {
	int _id;
    String _english;
    String _slovak;
    String _help;
    long _time;
    float _right;
    float _solvetime;
    float _limit;
    String _name;
     
    
    public Slovo(){     
    }
    
    public Slovo(int id, String name, String en, String sk, String help, long time, float right, float solvetime, float limit){
        this._id = id;
        this._name = name;
        this._english = en;
        this._slovak = sk;
        this._help = help;
        this._time = time;
        this._right = right;
        this._solvetime = solvetime;
        this._limit = limit;
    }
     
    public Slovo(String en, String sk){
        this._english = en;
        this._slovak = sk;
    }
    
    public int getID(){
        return this._id;
    }
     
    public void setID(int id){
        this._id = id;
    }
    
    public String getName(){
        return this._name;
    }
    
    public void setName(String name){
        this._name = name;
    }
     
    public String getEn(){
        return this._english;
    }
         
    public void setEn(String en){
        this._english = en;
    }
    
    public String getSk(){
        return this._slovak;
    }
    
    public void setSk(String sk){
        this._slovak = sk;
    }
    
    public String getHelp(){
        return this._help;
    }
         
    public void setHelp(String help){
        this._help = help;
    }
    
    public long getTime(){
        return this._time;
    }
     
    public void setTime(long time){
        this._time = time;
    }
    
    public float getSolveTime(){
        return this._solvetime;
    }
     
    public void setSolveTime(float time){
        this._solvetime = time;
    }
    
    public float getRight(){
        return this._right;
    }
     
    public void setRight(float right){
        this._right = right;
    }
    
    public float getLimit(){
        return this._limit;
    }
     
    public void setLimit(float limit){
        this._limit = limit;
    }
    
    public void spravne(long cas, int limit){
    	Log.d("Slovo", "Right "+ this._right);
        this._right = ((this._right/2)+1);
    	Log.d("Slovo", "SolveTime "+ this._solvetime);
        this._solvetime = (this._solvetime/2) + cas;
    	Log.d("Slovo", "Limit "+ this._limit);
        this._limit = (this._limit/2 + limit);
        this._time = System.currentTimeMillis()/1000;
        Log.d("Slovo", "Usetime "+ this._time);
    }
    
    public void nespravne(long cas, int limit){
        this._right = this._right/2;
        this._solvetime = (this._solvetime/2) + cas;
        this._limit = (this._limit/2 + limit);
        this._time = System.currentTimeMillis()/1000;
    }
    
}
