package com.example.ziacik;

public class Priklad {
	int _id;
    String _name;
    int _operand1;
    int _operand2;
    float _plus;
    float _minus;
    float _krat;
    float _deleno;
    float _plus_time;
    float _minus_time;
    float _krat_time;
    float _deleno_time;
    float _plus_limit;
    float _minus_limit;
    float _krat_limit;
    float _deleno_limit;
    
    public Priklad() {
	}
    
    public Priklad(int id, String name, int operand1, int operand2,
    		float plus, float minus, float krat, float deleno,
    		float plustime, float minustime, float krattime, float delenotime,
    		float pluslimit, float minuslimit, float kratlimit, float delenolimit){
        this._id = id;
        this._name = name;
        this._operand1 = operand1;
        this._operand2 = operand2;
        this._plus = plus;
        this._minus = minus;
        this._krat = krat;
        this._deleno = deleno;
        this._plus_time = plustime;
        this._minus_time = minustime;
        this._krat_time = krattime;
        this._deleno_time = delenotime;
        this._plus_limit = pluslimit;
        this._minus_limit = minuslimit;
        this._krat_limit = kratlimit;
        this._deleno_limit = delenolimit;
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

    public float getPlusTime(){
        return this._plus_time;
    }
     
    public void setPlusTime(float time){
        this._plus_time = time;
    }
    public float getMinusTime(){
        return this._minus_time;
    }
     
    public void setMinusTime(float time){
        this._minus_time = time;
    }
    public float getKratTime(){
        return this._krat_time;
    }
     
    public void setKratTime(float time){
        this._krat_time = time;
    }
    public float getDelenoTime(){
        return this._deleno_time;
    }
     
    public void setDelenoTime(float time){
        this._deleno_time = time;
    }

    public float getPlus(){
        return this._plus;
    }
     
    public void setPlus(float right){
        this._plus = right;
    }
    public float getMinus(){
        return this._minus;
    }
     
    public void setMinus(float right){
        this._minus = right;
    }
    public float getKrat(){
        return this._krat;
    }
     
    public void setKrat(float right){
        this._krat = right;
    }
    public float getDeleno(){
        return this._deleno;
    }
     
    public void setDeleno(float right){
        this._deleno = right;
    }

    public float getPlusLimit(){
        return this._plus_limit;
    }
     
    public void setPlusLimit(float limit){
        this._plus_limit = limit;
    }
    public float getMinusLimit(){
        return this._minus_limit;
    }
     
    public void setMinusLimit(float limit){
        this._minus_limit = limit;
    }
    public float getKratLimit(){
        return this._krat_limit;
    }
     
    public void setKratLimit(float limit){
        this._krat_limit = limit;
    }
    public float getDelenoLimit(){
        return this._deleno_limit;
    }
     
    public void setDelenoLimit(float limit){
        this._deleno_limit = limit;
    }
}
