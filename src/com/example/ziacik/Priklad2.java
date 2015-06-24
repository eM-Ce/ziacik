package com.example.ziacik;

public class Priklad2 {
	int _id;
    String _name;
    int _operand1;
    int _operand2;
    char _operation;
    long _usetime;
    float _right;
    float _time;
    float _limit;
    
    public Priklad2() {
	}
    
    public Priklad2(int id, String name, int operand1, int operand2, char operation,
    		long usetime, float right, float time, float limit){
        this._id = id;
        this._name = name;
        this._operand1 = operand1;
        this._operand2 = operand2;
        this._operation = operation;
        this._right = right;
        this._usetime = usetime;
        this._time = time;
        this._limit = limit;
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
     
    public int getOp1(){
        return this._operand1;
    }
         
    public void setOp1(int operand){
        this._operand1 = operand;
    }
    
    public int getOp2(){
        return this._operand2;
    }
         
    public void setOp2(int operand){
        this._operand2 = operand;
    }

    public float getRight(){
        return this._right;
    }
     
    public void setRight(float right){
        this._right = right;
    }
    public long getUsetime(){
        return this._usetime;
    }
     
    public void setUsetime(long time){
        this._usetime = time;
    }
    public float getTime(){
        return this._time;
    }
     
    public void setTime(float time){
        this._time = time;
    }
    public float getLimit(){
        return this._limit;
    }
     
    public void setLimit(float limit){
        this._limit = limit;
    }

    public char getOperation(){
    	return this._operation;
    }
    
    public void setOperation(char operation){
    	this._operation = operation;
    }

    public void spravne(long cas, int limit){
        this._right = (this._right/2)+1;
        this._time = (this._time/2) + cas;
        this._limit = (this._limit/2 + limit);
        this._usetime = System.currentTimeMillis()/1000;
    }
    
    public void nespravne(long cas, int limit){
        this._right = this._right/2;
        this._time = (this._time/2) + cas;
        this._limit = (this._limit/2 + limit);
        this._usetime = System.currentTimeMillis()/1000;
    }
}
