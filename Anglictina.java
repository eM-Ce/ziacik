package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Anglictina extends Activity {
	
	TextView word;
	EditText preklad;
	Spinner vyberkategorie;
	Button start;
	Button enter;
	String spravne;
	String prelozene;
	String kategoria;
	List<String> kategorie;
	List<Slovo> slova;
	int pozicia;
	DBHelper db;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_english);
		db = new DBHelper(this);
		word = (TextView) findViewById(R.id.Word);
		start = (Button) findViewById(R.id.Uprav);
		enter = (Button) findViewById(R.id.Enter);
		preklad = (EditText) findViewById(R.id.Vysledok);
		vyberkategorie = (Spinner) findViewById(R.id.Subory);
		kategorie = new ArrayList<String>();
		kategorie.add("Všetky slovíèka");
		kategorie.addAll(db.kategorie());
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kategorie);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vyberkategorie.setAdapter(dataAdapter);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.Uprav:
					word.setVisibility(1);
					enter.setVisibility(1);
					preklad.setVisibility(1);
					vyberkategorie.setVisibility(4);
					start.setVisibility(4);
					try{
						 kategoria = String.valueOf(vyberkategorie.getSelectedItem());
						 if (kategoria.equals("Všetky slovíèka")){
							 slova = db.vsetkySlova();
						 }else {
							 slova = db.slovaZoSuboru(kategoria);
						 }
						 pozicia = 0;
				         word.setText(slova.get(pozicia).getEn());
				         spravne = slova.get(pozicia).getSk();
				      }catch(Exception e){

				      }
					break;
			}
		}});
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.Enter:
					
					prelozene = preklad.getText().toString();
					preklad.setText("");
					if(prelozene.compareTo(spravne) == 0){
						slova.get(pozicia).spravne();
						slova.get(pozicia).setTime((int) (System.currentTimeMillis()/1000));
						db.upravPocetOdpovedi(slova.get(pozicia));
						Toast.makeText(getBaseContext(),"Správne",
							       Toast.LENGTH_SHORT).show();
					}else {
						slova.get(pozicia).nespravne();
						slova.get(pozicia).setTime((int) (System.currentTimeMillis()/1000));
						db.upravPocetOdpovedi(slova.get(pozicia));
						Toast.makeText(getBaseContext(),"Nesprávne",
							       Toast.LENGTH_SHORT).show();
					}
					pozicia++;
					if (pozicia < slova.size()){
					word.setText(slova.get(pozicia).getEn());
			        spravne = slova.get(pozicia).getSk();
			        } else{
			        	Toast.makeText(getBaseContext(),"Koniec",
			        		       Toast.LENGTH_SHORT).show();
			        	word.setVisibility(4);
						enter.setVisibility(4);
						preklad.setVisibility(4);
						start.setVisibility(1);
						vyberkategorie.setVisibility(1);
			        }
				default:
					break;
				}
			}
		});
		
	}

}
