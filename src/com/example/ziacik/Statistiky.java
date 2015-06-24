package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

public class Statistiky extends Activity {
	
	DBHelper db;
	DBHelperMath2 dbmath;
	List<Slovo> slova;
	List<Priklad2> priklady;
	String dieta;
	List<String> vyber;
	AlertDialog.Builder adb;
	String kategoria;
	String choice;
	GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistiky);
		setContentView(new MyView(this));
		db = new DBHelper(this);
		dbmath = new DBHelperMath2(this);
		Bundle b = getIntent().getExtras();
		dieta = b.getString("meno");
		choice = "";
		kategoria = "";
		setTitle("Štatistiky - " + dieta);
		vyber = new ArrayList<>();
		vyber.add("Angliètina");
		vyber.add("Matematika");
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Statistiky.this,
                android.R.layout.select_dialog_singlechoice, vyber);
		
		adb = new AlertDialog.Builder(Statistiky.this);
		adb.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				choice = arrayAdapter.getItem(which);
				setTitle("Štatistiky - " + choice + " - " + dieta);
				AlertDialog.Builder adb2 = new AlertDialog.Builder(Statistiky.this);
				if (choice.equals("Angliètina")){
					adb2.setTitle("Výber kategórie");
					List<String> kategorie = db.kategorie(dieta);
					final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
			                Statistiky.this,
			                android.R.layout.select_dialog_singlechoice, kategorie);
					adb2.setAdapter(arrayAdapter2, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							kategoria = arrayAdapter2.getItem(which);
							setContentView(new MyView(Statistiky.this));
						}
					});
					adb2.show();
				}
				if (choice.equals("Matematika")){
					adb2.setTitle("Výber prvého operandu");
					List<String> kategorie = new ArrayList<>();
					for (int i = 1; i < 101; i++) {
						kategorie.add(Integer.toString(i));
					}
					final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
			                Statistiky.this,
			                android.R.layout.select_dialog_singlechoice, kategorie);
					adb2.setAdapter(arrayAdapter2, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							kategoria = arrayAdapter2.getItem(which);
							setContentView(new MyView(Statistiky.this));
						}
					});
					adb2.show();
				}
				
			}
		});
		adb.show();
    }

    public class MyView extends View {
        public MyView(Context context) {
             super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
           // TODO Auto-generated method stub
           super.onDraw(canvas);
           int x = getWidth();
           int y = getHeight();
           int radius;
           radius = 20;
           Paint paint = new Paint();
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           canvas.drawPaint(paint);
           // Use Color.parseColor to define HTML colors
           paint.setColor(Color.parseColor("#000000"));
           canvas.drawLine(x/2, 0, x/2, y, paint);
           canvas.drawLine(0, y/2, x, y/2, paint);
           paint.setTextSize(50);
           canvas.drawText("zle\nrýchlo", 10, 50, paint);
           canvas.drawText("dobre\nrýchlo", x-300, 50, paint);
           canvas.drawText("zle\npomaly", 10, y - 40, paint);
           canvas.drawText("dobre\npomaly", x - 330, y - 40, paint);
           paint.setColor(Color.parseColor("#CD5C5C"));
           if (choice.equals("Angliètina")){
        	   slova = db.slovaZoSuboru(kategoria, dieta);
        	   for (int i = 0; i < slova.size(); i++) {
        		   Slovo slovo = slova.get(i);
        		   float posx = slovo.getRight()*x/2;
        		   float posy;
        		   if (slovo.getLimit()==0)posy = y/2;
        		   else posy = (slovo.getSolveTime()/slovo.getLimit())*y;
        		   //Log.d("Slovo", "Right "+ slovo.getRight() + " SolveTime "+ slovo.getSolveTime() + " LimitTime "+ slovo.getLimit());
        		   //Log.d("Kruh", Float.toString(posx)+" "+Float.toString(posy));
        		   canvas.drawCircle(posx, posy, radius, paint);
        	   }
           }
           if (choice.equals("Matematika")){
        	   priklady = dbmath.prikladyPrvyOperand(dieta, Integer.parseInt(kategoria));
        	   for (int i = 0; i < priklady.size(); i++) {
        		   Priklad2 priklad = priklady.get(i);
        		   float posx = priklad.getRight()*x/2;
        		   float posy;
        		   if (priklad.getLimit()==0)posy = y/2;
        		   else posy = (priklad.getTime()/priklad.getLimit())*y;
        		   //Log.d("Priklad", "Right "+ priklad.getRight() + " SolveTime "+ priklad.getTime() + " LimitTime "+ priklad.getLimit());
        		   //Log.d("Kruh", Float.toString(posx)+" "+Float.toString(posy));
        		   canvas.drawCircle(posx, posy, radius, paint);
        	   }
           }
       }
        
        private boolean inside(float x, float y, float posx, float posy){
        	if (Math.abs(x-posx)<30 && Math.abs(y-posy)<30){
        		Log.d("Inside", "sedí");
        		return true;
        	}
        	Log.d("Inside", "nesedí " + Math.abs(x-posx) + " " + Math.abs(y-posy));

        	Log.d("Inside", "nesedí x " + x + " " + y);

        	Log.d("Inside", "nesedí posx " + posx + " " + posy);
        	return false;
        }
        
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            int width = getWidth();
            int height = getHeight();
            

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                	if (choice.equals("Angliètina")){
                		AlertDialog.Builder adb2 = new AlertDialog.Builder(Statistiky.this);
        					adb2.setTitle("Vybrané slovíèka");
        					List<String> vyber = new ArrayList<String>();
        					final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
        			                Statistiky.this,
        			                android.R.layout.select_dialog_singlechoice, vyber);
        					adb2.setAdapter(arrayAdapter2, null);
        					
                  	   for (int i = 0; i < slova.size(); i++) {
                  		   Slovo slovo = slova.get(i);
                  		   float posx = slovo.getRight()*width/2;
                  		   float posy;
                  		   if (slovo.getLimit()==0)posy = height/2;
                  		   else posy = (slovo.getSolveTime()/slovo.getLimit())*height;
                  		   if (inside(x, y, posx, posy)) {
                  			   vyber.add("EN: " + slovo.getEn()+"\nSK: " + slovo.getSk() + "\n");
                  		   }
                  	   }
                  	   if (vyber.size() > 0)adb2.show();
                     }
                     if (choice.equals("Matematika")){
                    	AlertDialog.Builder adb2 = new AlertDialog.Builder(Statistiky.this);
     					adb2.setTitle("Vybrané príklady");
     					List<String> vyber = new ArrayList<String>();
     					final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
     			                Statistiky.this,
     			                android.R.layout.select_dialog_singlechoice, vyber);
     					adb2.setAdapter(arrayAdapter2, null);
                  	   for (int i = 0; i < priklady.size(); i++) {
                  		   Priklad2 priklad = priklady.get(i);
                  		   float posx = priklad.getRight()*width/2;
                  		   float posy;
                  		   if (priklad.getLimit()==0)posy = height/2;
                  		   else posy = (priklad.getTime()/priklad.getLimit())*height;
                  		   if (inside(x, y, posx, posy)) {
                			   vyber.add( priklad.getOp1()+" " + priklad.getOperation() + " " + priklad.getOp2());
                		   }
                  	   }
                  	   if (vyber.size() > 0)adb2.show();
                     }                 
                     invalidate();
                     break;
                   
                case MotionEvent.ACTION_MOVE:
                   
                       
                           
                            
                        invalidate();
                   
                    break;
                case MotionEvent.ACTION_UP:
                   
                        
                        invalidate();
                    
                        
                    break;
            }
            return true;
        }
        
        
        
    }
    
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistiky, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			adb.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
