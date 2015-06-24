package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
//import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;




public class Matematika extends Activity {
	

	TextView op1;
	TextView op2;
	TextView operation;
	TextView time;
	Button start;
	Button enter;
	Button pauza;
	EditText vysledok;
	int operacia;
	int max1;
	int max2;
	int urovenCelkova;
	int urovenScitanie;
	int urovenOdcitanie;
	int urovenNasobenie;
	int urovenDelenie;
	Integer operand1;
	Integer operand2;
	SharedPreferences prefs;
	SharedPreferences.Editor sEdit;
	CountDownTimer timer;
	long timeleft;
	boolean pauznute;
	int countpauses;
	int pocet;
	int limit;
	int spravne;
	int nespravne;
	int pocetlimit;
	DBHelperMath2 db;
	DBHelperUsers dbusers;
	List<Priklad2> priklady;
	List<String> zlezodpovedane;
	InputMethodManager imm;
	ProgressDialog progressDialog;
	String dieta;
	ImageView tick;
	ImageView wrong;
	int zobrazene;
	
/*private class DBInsertTask extends AsyncTask<Void, Void, Void> {
		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(Matematika.this, "Poèkajte prosím", "Vkladám do databázy...");
	    }

	    protected void onProgressUpdate(Void... progress) {
	        publishProgress(progress[0]);
	    }

	    protected void onPostExecute(Void result) {
	    	progressDialog.dismiss();
	    }

		@Override
		protected Void doInBackground(Void... arg0) {
			db.pridajPriklady();
			return null;
		}
	}*/
	
	
	private CountDownTimer initializeTimer(long millis){
		return new CountDownTimer(millis, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				zobrazene++;
				if (zobrazene > 5) {
					tick.setVisibility(4);
					wrong.setVisibility(4);
				}
				time.setText("Ostáva: " + String.valueOf(millisUntilFinished/1000) + "s Príklad: " + (pocet+1) + "/" + pocetlimit);
				timeleft = millisUntilFinished;
			}
			
			@Override
			public void onFinish() {
				timeleft = 0;
				tick.setVisibility(4);
				wrong.setVisibility(4);
				enter.callOnClick();
			}
		};
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle b = getIntent().getExtras();
		dieta = b.getString("meno");
		
		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		sEdit = prefs.edit();
		urovenCelkova = prefs.getInt("urovenCelkova", 0);
		urovenScitanie = prefs.getInt("urovenScitanie", 0);
		urovenOdcitanie = prefs.getInt("urovenOdcitanie", 0);
		urovenNasobenie = prefs.getInt("urovenNasobenie", 0);
		urovenDelenie = prefs.getInt("urovenDelenie", 0);
		Log.d("Celkova uroven", Integer.toString(urovenCelkova));
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		db = new DBHelperMath2(this);
		dbusers = new DBHelperUsers(this);
		//new DBInsertTask().execute();
		op1 = (TextView) findViewById(R.id.Operand1);
		op2 = (TextView) findViewById(R.id.Operand2);
		operation = (TextView) findViewById(R.id.Word);
		time = (TextView) findViewById(R.id.TimerAndCount);
		start = (Button) findViewById(R.id.Uprav);
		enter = (Button) findViewById(R.id.Enter);
		vysledok = (EditText) findViewById(R.id.Vysledok);
		tick = (ImageView) findViewById(R.id.tick);
		wrong = (ImageView) findViewById(R.id.wrong);
		limit = dbusers.limitPriklady(dieta) * 1000;
		pocetlimit = dbusers.pocetPrikladov(dieta);
		timer = initializeTimer(limit);
		pauza = (Button) findViewById(R.id.Pause);
		zlezodpovedane = new ArrayList<>();
		vysledok.setOnEditorActionListener(new OnEditorActionListener() {

			  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			    if (actionId == EditorInfo.IME_ACTION_DONE) {
			    	enter.performClick();
			      return true;
			    } else {
			      return false;
			    }
			  }
			});
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.Uprav:
					op1.setVisibility(1);
					op2.setVisibility(1);
					time.setVisibility(1);
					operation.setVisibility(1);
					enter.setVisibility(1);
					vysledok.setVisibility(1);
					pauza.setVisibility(1);
					start.setVisibility(4);
					pocet = 0;
					spravne = 0;
					nespravne = 0;
					zlezodpovedane = new ArrayList<String>();
					int pocetprikladov = dbusers.pocetPrikladov(dieta);
					int maxplus = dbusers.maxPlus(dieta);
					int maxminus = dbusers.maxMinus(dieta);
					int maxnasobenie = dbusers.maxNasobenie(dieta);
					int maxdelenie = dbusers.maxDelenie(dieta);
					boolean zaporny = false;
					if (dbusers.negativnyVysledok(dieta) == 1) zaporny = true;
					priklady = db.prikladyPodlaNastaveni(dieta, maxplus, maxminus, zaporny, maxnasobenie, maxdelenie, pocetprikladov);
					Log.d("Pocet prikladov", Integer.toString(priklady.size()));
					op1.setText(Integer.toString(priklady.get(pocet).getOp1()));
					Log.d("Natavujem texty", "Op1 nastaveny");
					op2.setText(Integer.toString(priklady.get(pocet).getOp2()));
					operation.setText(String.valueOf(priklady.get(pocet).getOperation()));
			        imm.showSoftInput(vysledok, InputMethodManager.SHOW_IMPLICIT);
			        timer = initializeTimer(limit);
			        timer.start();
					break;
			}
		}});
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.Enter:
					if (!vysledok.getText().toString().isEmpty()){
					timer.cancel();
					
					int spravnyvysledok = 0;
					switch (priklady.get(pocet).getOperation()) {
					case '+':
						spravnyvysledok = priklady.get(pocet).getOp1() + priklady.get(pocet).getOp2();
						break;
					case '-':
						spravnyvysledok = priklady.get(pocet).getOp1() - priklady.get(pocet).getOp2();
						break;
					case '*':
						spravnyvysledok = priklady.get(pocet).getOp1() * priklady.get(pocet).getOp2();
						break;
					case '/':
						spravnyvysledok = priklady.get(pocet).getOp1() / priklady.get(pocet).getOp2() ;
						break;
					}
					int vyslPouziv = Integer.parseInt(vysledok.getText().toString());
					vysledok.setText("");
					if (vyslPouziv==spravnyvysledok){
						spravne++;
						Toast.makeText(getApplicationContext(),
								"Správne", Toast.LENGTH_SHORT).show();
						priklady.get(pocet).spravne(limit - timeleft, limit);
						db.upravPocetOdpovedi(priklady.get(pocet));
						zobrazene = 0;
						wrong.setVisibility(4);
						tick.setVisibility(1);
						MediaPlayer mediaPlayer = MediaPlayer.create(Matematika.this, R.raw.cartoon108);
						mediaPlayer.start();
					} else {
							nespravne++;
						Toast.makeText(getApplicationContext(),
								"Nesprávne", Toast.LENGTH_SHORT).show();
						zlezodpovedane.add(priklady.get(pocet).getOp1() + " " 
								+ priklady.get(pocet).getOperation()+ " " 
								+ priklady.get(pocet).getOp2()
								+ " = " + spravnyvysledok);
						priklady.get(pocet).nespravne(limit - timeleft, limit);
						db.upravPocetOdpovedi(priklady.get(pocet));
						zobrazene = 0;
						tick.setVisibility(4);
						wrong.setVisibility(1);
						MediaPlayer mediaPlayer = MediaPlayer.create(Matematika.this, R.raw.cartoon048);
						mediaPlayer.start();
						}
					pocet++;
					if (pocet < pocetlimit && pocet < priklady.size()) {
						op1.setText(Integer.toString(priklady.get(pocet).getOp1()));
						op2.setText(Integer.toString(priklady.get(pocet).getOp2()));
						operation.setText(String.valueOf(priklady.get(pocet).getOperation()));
				        timer = initializeTimer(limit);
				        timer.start();
					} else {
						op1.setVisibility(4);
						op2.setVisibility(4);
						time.setVisibility(4);
						operation.setVisibility(4);
						enter.setVisibility(4);
						vysledok.setVisibility(4);
						pauza.setVisibility(4);
						wrong.setVisibility(4);
						tick.setVisibility(4);
						start.setVisibility(1);
						AlertDialog.Builder adb = new AlertDialog.Builder(
								Matematika.this);
						adb.setTitle("Súhrn");
						String message = "Správne: " + spravne + "\nNesprávne: " + nespravne + "\nZle zodpovedané príklady:\n";
						for (int i = 0; i < zlezodpovedane.size(); i++) {
							message = message + zlezodpovedane.get(i) + "\n";
						}
						adb.setMessage(message);
						adb.setNeutralButton("OK", null);
						adb.show();
					}
				
					}
				}
			}
		});
		pauza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.Pause:
					if(pauznute){
						pauznute = false;
						imm.showSoftInput(vysledok, InputMethodManager.SHOW_IMPLICIT);
						op1.setVisibility(1);
						op2.setVisibility(1);
						operation.setVisibility(1);
						enter.setVisibility(1);
						vysledok.setVisibility(1);
						pauza.setText("Pauza");
						timer = initializeTimer(timeleft);
						if (countpauses == 3) pauza.setVisibility(4);
						timer.start();
						
					}else {
						timer.cancel();
						pauza.setText("Pokraèova");
						countpauses++;
						pauznute = true;
						imm.hideSoftInputFromWindow(vysledok.getWindowToken(), 0);						
						op1.setVisibility(4);
						op2.setVisibility(4);
						operation.setVisibility(4);
						enter.setVisibility(4);
						wrong.setVisibility(4);
						tick.setVisibility(4);
						vysledok.setVisibility(4);
					}
				default: break;
				}
			}
		});

	}
	
	/*
	@Override
	public void onBackPressed() {
		sEdit.putInt("urovenCelkova", urovenCelkova);
		sEdit.putInt("urovenScitanie", urovenScitanie);
		sEdit.putInt("urovenOdcitanie", urovenOdcitanie);
		sEdit.putInt("urovenNasobenie", urovenNasobenie);
		sEdit.putInt("urovenDelenie", urovenDelenie);
		sEdit.commit();
		super.onBackPressed();
	}*/

}
