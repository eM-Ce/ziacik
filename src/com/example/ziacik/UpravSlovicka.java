package com.example.ziacik;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpravSlovicka extends Activity {
	
	EditText slovicka;
	TextView instrukcia;
	Spinner subory;
	Button otvor;
	Button enter;
	String subor;
	String slova;
	int pocetsuborov;
	SharedPreferences prefs;
	ArrayList<String> suborymena;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uprav_slovicka);
		instrukcia = (TextView) findViewById(R.id.instrukcia);
		otvor = (Button) findViewById(R.id.Uprav);
		enter = (Button) findViewById(R.id.PridatSlovicka);
		slovicka = (EditText) findViewById(R.id.Anglicky);
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		subory = (Spinner) findViewById(R.id.Subory);
		suborymena = new ArrayList<>();
		pocetsuborov = prefs.getInt("pocetsuborov", 0);
		Log.d("nazvy", Integer.toString(prefs.getInt("pocetsuborov", 0)));
		for (int i = 1; i <= pocetsuborov; i++) {
			Log.d("nazvy", prefs.getString("subor"+i, ""));
			suborymena.add(prefs.getString("subor"+i, ""));
		}
		Collections.sort(suborymena);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, suborymena);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subory.setAdapter(dataAdapter);
		otvor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.Uprav:
					Log.d("nazvy", "idem nastavit viditelnost");
					enter.setVisibility(1);
					slovicka.setVisibility(1);
					instrukcia.setVisibility(1);
					subory.setVisibility(4);
					otvor.setVisibility(4);
					Log.d("nazvy", "idem otvorit subor");
					try{
						 subor = String.valueOf(subory.getSelectedItem());
				         FileInputStream fin = openFileInput(subor);
				         int c;
				         String temp="";
				         while( (c = fin.read()) != -1){
				            temp = temp + Character.toString((char)c);
				         }
				         slovicka.setText(temp);
				      }catch(Exception e){

				      }
					break;
			}
		}});
	}
	
	public void save(View view){
		slova = slovicka.getText().toString();
			try {
				FileOutputStream fOut = openFileOutput(subor,MODE_PRIVATE);
				fOut.write(slova.getBytes());
				fOut.close();
				Toast.makeText(getBaseContext(),"Slovíèka boli uložené do súboru: " + subor,
				Toast.LENGTH_SHORT).show();
				enter.setVisibility(4);
				slovicka.setVisibility(4);
				instrukcia.setVisibility(4);
				subory.setVisibility(1);
				otvor.setVisibility(1);
	    	} catch (Exception e) {
	    	e.printStackTrace();
	    	}
	}
	
}
