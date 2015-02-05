package com.example.ziacik;

public class Slovo {
	int _id;
    String _english;
    String _slovak;
    int _time;
    int _right;
     
    
    public Slovo(){     
    }
    
    public Slovo(int id, String en, String sk, int time, int right){
        this._id = id;
        this._english = en;
        this._slovak = sk;
        this._time = time;
        this._right = right;
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
    
    public int getTime(){
        return this._time;
    }
     
    public void setTime(int time){
        this._time = time;
    }
    
    public int getRight(){
        return this._right;
    }
     
    public void setRight(int right){
        this._right = right;
    }
    
    public void spravne(){
        this._right++;
    }
    
    public void nespravne(){
        this._right--;
    }
    
}
