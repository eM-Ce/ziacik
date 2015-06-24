package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RodicNastavenia extends Activity {
	EditText limitAJ;
	EditText pocetSlovicok;
	EditText limitM;
	EditText pocetPrikladov;
	EditText maxScitanie;
	EditText maxOdcitanie;
	CheckBox zapornyVysledok;
	EditText maxNasobenie;
	EditText maxDelenie;
	Spinner vyberDietata;
	List<String> deti;
	DBHelperUsers db;
	String dieta;
	ProgressDialog progressDialog;
	DBHelperMath2 dbmath;
	DBHelper dbslovicka;
	DBHelperOthers dbostatne;
	Button resetMath;
	Button pridajDieta;
	Button vymazDieta;
	Button statistiky;
	InputMethodManager imm;
	
private class DBInsertTask extends AsyncTask<Void, Void, Void> {
		
		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(RodicNastavenia.this, "Poèkajte prosím", "Vkladám do databázy...");
	    }

	    protected void onProgressUpdate(Void... progress) {
	        publishProgress(progress[0]);
	    }

	    protected void onPostExecute(Void result) {
	    	progressDialog.dismiss();
	    }

		@Override
		protected Void doInBackground(Void... arg0) {
			dbmath.vymazDieta(dieta);
			dbmath.pridajPriklady(dieta);
			return null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rodic_nastavenia);
		db = new DBHelperUsers(this);
		dbmath = new DBHelperMath2(this);
		dbslovicka = new DBHelper(this);
		dbostatne = new DBHelperOthers(this);
		limitAJ = (EditText) findViewById(R.id.editLimitAJ);
		pocetSlovicok = (EditText) findViewById(R.id.editPocetAJ);
		limitM = (EditText) findViewById(R.id.editLimitM);
		pocetPrikladov = (EditText) findViewById(R.id.editPocetM);
		maxScitanie = (EditText) findViewById(R.id.editScitanie);
		maxOdcitanie = (EditText) findViewById(R.id.editOdcitanie);
		zapornyVysledok = (CheckBox) findViewById(R.id.checkZapoVysledok);
		maxNasobenie = (EditText) findViewById(R.id.editNasobenie);
		maxDelenie = (EditText) findViewById(R.id.editDelenie);
		limitAJ.setFocusableInTouchMode(true);
		pocetSlovicok.setFocusableInTouchMode(true);
		limitM.setFocusableInTouchMode(true);
		pocetPrikladov.setFocusableInTouchMode(true);
		maxScitanie.setFocusableInTouchMode(true);
		maxOdcitanie.setFocusableInTouchMode(true);
		maxNasobenie.setFocusableInTouchMode(true);
		maxDelenie.setFocusableInTouchMode(true);
		vyberDietata = (Spinner) findViewById(R.id.spinnerDeti);
		resetMath = (Button) findViewById(R.id.ResetMathButton);
		pridajDieta = (Button) findViewById(R.id.PriadjDietaButton);
		vymazDieta = (Button) findViewById(R.id.VymazDietaButton);
		statistiky = (Button) findViewById(R.id.StatistikyButton);
		resetMath.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DBInsertTask().execute();
			}
		});
		
		deti = new ArrayList<String>();
		deti.addAll(db.menaDeti());
		Log.d("Settings", "mena deti nacitane");
		if (deti.isEmpty()) {
			Log.d("Settings", "pridavam prve dieta");
			db.pridajDieta("name");
			deti.add("name");
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, deti);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vyberDietata.setAdapter(dataAdapter);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		vyberDietata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				imm.hideSoftInputFromWindow(limitAJ.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(pocetSlovicok.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(limitM.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(pocetPrikladov.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(maxScitanie.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(maxOdcitanie.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(maxNasobenie.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(maxDelenie.getWindowToken(), 0);
				dieta = String.valueOf(vyberDietata.getSelectedItem());
				limitAJ.setText(Integer.toString(db.limitSlovicka(dieta)));
				pocetSlovicok.setText(Integer.toString(db.pocetSlovicok(dieta)));
				limitM.setText(Integer.toString(db.limitPriklady(dieta)));
				pocetPrikladov.setText(Integer.toString(db.pocetPrikladov(dieta)));
				maxScitanie.setText(Integer.toString(db.maxPlus(dieta)));
				maxOdcitanie.setText(Integer.toString(db.maxMinus(dieta)));
				if (db.negativnyVysledok(dieta) == 1) zapornyVysledok.setChecked(true);
				else zapornyVysledok.setChecked(false);
				maxNasobenie.setText(Integer.toString(db.maxNasobenie(dieta)));
				maxDelenie.setText(Integer.toString(db.maxDelenie(dieta)));				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		dieta = String.valueOf(vyberDietata.getSelectedItem());
		limitAJ.setText(Integer.toString(db.limitSlovicka(dieta)));
		pocetSlovicok.setText(Integer.toString(db.pocetSlovicok(dieta)));
		limitM.setText(Integer.toString(db.limitPriklady(dieta)));
		pocetPrikladov.setText(Integer.toString(db.pocetPrikladov(dieta)));
		maxScitanie.setText(Integer.toString(db.maxPlus(dieta)));
		maxOdcitanie.setText(Integer.toString(db.maxMinus(dieta)));
		if (db.negativnyVysledok(dieta) == 1) zapornyVysledok.setChecked(true);
		else zapornyVysledok.setChecked(false);
		maxNasobenie.setText(Integer.toString(db.maxNasobenie(dieta)));
		maxDelenie.setText(Integer.toString(db.maxDelenie(dieta)));
		limitAJ.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!limitAJ.getText().toString().isEmpty()){
					db.upravLimitSlovicka(dieta, Integer.parseInt(limitAJ.getText().toString()));
				}				
			}
		});
		pocetSlovicok.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!pocetSlovicok.getText().toString().isEmpty()){
					db.upravPocetSlov(dieta, Integer.parseInt(pocetSlovicok.getText().toString()));
				}				
			}
		});
		limitM.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!limitM.getText().toString().isEmpty())
				db.upravLimitMatematika(dieta, Integer.parseInt(limitM.getText().toString()));				
			}
		});
		pocetPrikladov.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!pocetPrikladov.getText().toString().isEmpty())
				db.upravPocetPrikladov(dieta, Integer.parseInt(pocetPrikladov.getText().toString()));				
			}
		});
		maxScitanie.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!maxScitanie.getText().toString().isEmpty()){
					if (Integer.parseInt(maxScitanie.getText().toString())>100)
						maxScitanie.setText(Integer.toString(100));
					if (Integer.parseInt(maxNasobenie.getText().toString())<0)
						maxNasobenie.setText(Integer.toString(0));
					db.upravMaxScitanie(dieta, Integer.parseInt(maxScitanie.getText().toString()));
				}
			}
		});
		maxOdcitanie.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!maxOdcitanie.getText().toString().isEmpty()){
					if (Integer.parseInt(maxOdcitanie.getText().toString())>100)
						maxOdcitanie.setText(Integer.toString(100));
					if (Integer.parseInt(maxNasobenie.getText().toString())<0)
						maxNasobenie.setText(Integer.toString(0));
					db.upravMaxOdcitanie(dieta, Integer.parseInt(maxOdcitanie.getText().toString()));				
				}
			}
		});
		zapornyVysledok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		       @Override
		       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		    	   db.upravZapornyVysledok(dieta, isChecked);
		       }
		   }
		);
		maxNasobenie.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!maxNasobenie.getText().toString().isEmpty()){
					if (Integer.parseInt(maxNasobenie.getText().toString())>100)
						maxNasobenie.setText(Integer.toString(100));
					if (Integer.parseInt(maxNasobenie.getText().toString())<0)
						maxNasobenie.setText(Integer.toString(0));
				db.upravMaxNasobenie(dieta, Integer.parseInt(maxNasobenie.getText().toString()));	
				}
			}
		});
		maxDelenie.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void afterTextChanged(Editable arg0) {
				if (!maxDelenie.getText().toString().isEmpty()){
					if (Integer.parseInt(maxDelenie.getText().toString())>100)
						maxDelenie.setText(Integer.toString(100));
					if (Integer.parseInt(maxDelenie.getText().toString())<0)
						maxDelenie.setText(Integer.toString(0));
					db.upravMaxDelenie(dieta, Integer.parseInt(maxDelenie.getText().toString()));
				}				
			}
		});
		pridajDieta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						RodicNastavenia.this);
				adb.setTitle("Pridanie dieaa");
				adb.setMessage("Zadajte prosím meno dieaa:");
				final EditText input = new EditText(RodicNastavenia.this);
				adb.setView(input);
				adb.setNegativeButton("Zruši", null);
				adb.setPositiveButton("Prida", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String meno = input.getText().toString();
						if(meno.isEmpty()){Toast.makeText(getApplicationContext(),
								"Nezadali ste meno.", Toast.LENGTH_SHORT).show();
						}else if (deti.contains(meno)){
							Toast.makeText(getApplicationContext(),
									"Zvo¾te nové meno", Toast.LENGTH_SHORT).show();
						} else {
							db.pridajDieta(meno);
							dieta = meno;
							deti.add(meno);
							vyberDietata.setSelection(deti.size()-1);
							new DBInsertTask().execute();
						}
					}
				});
				adb.show();
			}
		});
		vymazDieta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						RodicNastavenia.this);
				adb.setTitle("Vymazanie dieaa");
				adb.setMessage("Prajete si vymaza diea (" + dieta + ") so všetkými záznamami z databázy?");
				adb.setNegativeButton("Nie", null);
				adb.setPositiveButton("Áno", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.vymazDieta(dieta);
						dbmath.vymazDieta(dieta);
						deti.remove(dieta);
						if (deti.isEmpty()) {
							Log.d("Settings", "pridavam prve dieta");
							db.pridajDieta("name");
							deti.add("name");
						}
						vyberDietata.setSelection(0);
						//TODO mazanie slovicok a ostatnych predmetov	
					}
				});
				adb.show();
			}
		});
		statistiky.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Class<?> mainClass = Class.forName("com.example.ziacik.Statistiky");
					Intent in = new Intent(RodicNastavenia.this, mainClass);
					Bundle b = new Bundle();
					b.putString("meno", dieta);
					in.putExtras(b);
					startActivity(in);
					Log.d("Nastavenia", "spustil som statistiky");
				} catch (Exception E) {
					E.printStackTrace();
				}
			}
		});
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rodic_nastavenia, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
