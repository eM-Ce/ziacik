package com.example.ziacik;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Anglictina extends Activity {
	
	TextView word;
	TextView time;
	EditText preklad;
	Spinner vyberkategorie;
	Button start;
	Button enter;
	Button pauza;
	String spravne;
	String prelozene;
	String kategoria;
	List<String> kategorie;
	List<String> zlezodpovedane;
	List<Slovo> slova;
	int pozicia;
	DBHelper db;
	DBHelperUsers dbusers;
	CountDownTimer timer;
	long timeleft;
	boolean pauznute;
	int countpauses;
	int pocet;
	int pocetlimit;
	int limit;
	int spravnezodpovedane;
	int nespravne;
	InputMethodManager imm;
	String dieta;
	ImageView tick;
	ImageView wrong;
	int zobrazene;
	
	
	private CountDownTimer initializeTimer(long millis){
		return new CountDownTimer(millis, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				zobrazene++;
				if (zobrazene > 5) {
					tick.setVisibility(4);
					wrong.setVisibility(4);
				}
				time.setText("Ostáva: " + String.valueOf(millisUntilFinished/1000) + "s Slovo: " + pocet + "/" + pocetlimit);
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
		setContentView(R.layout.activity_english);
		pauznute = false;
		countpauses = 0;
		Bundle b = getIntent().getExtras();
		dieta = b.getString("meno");
		db = new DBHelper(this);
		dbusers = new DBHelperUsers(this);
		word = (TextView) findViewById(R.id.Word);
		time = (TextView) findViewById(R.id.TimerAndCount);
		start = (Button) findViewById(R.id.Uprav);
		enter = (Button) findViewById(R.id.Enter);
		pauza = (Button) findViewById(R.id.Pause);
		preklad = (EditText) findViewById(R.id.Vysledok);
		vyberkategorie = (Spinner) findViewById(R.id.Subory);
		tick = (ImageView) findViewById(R.id.tick);
		wrong = (ImageView) findViewById(R.id.wrong);
		limit = dbusers.limitSlovicka(dieta) * 1000;
		pocetlimit = dbusers.pocetSlovicok(dieta);
		timer = initializeTimer(limit);
		kategorie = new ArrayList<String>();
		zlezodpovedane = new ArrayList<String>();
		kategorie.add("Všetky slovíèka");
		kategorie.addAll(db.kategorie(dieta));
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, kategorie);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		vyberkategorie.setAdapter(dataAdapter);
		preklad.setOnEditorActionListener(new OnEditorActionListener() {

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
					word.setVisibility(1);
					time.setVisibility(1);
					enter.setVisibility(1);
					preklad.setVisibility(1);
					pauza.setVisibility(1);
					vyberkategorie.setVisibility(4);
					start.setVisibility(4);
					zlezodpovedane = new ArrayList<String>();
					try{
						 kategoria = String.valueOf(vyberkategorie.getSelectedItem());
						 if (kategoria.equals("Všetky slovíèka")){
							 slova = db.vsetkySlova(dieta, pocetlimit);
						 }else {
							 slova = db.slovaZoSuboru(kategoria, dieta, pocetlimit);
						 }
						 pozicia = 0;
				         word.setText(slova.get(pozicia).getEn() + " (" + slova.get(pozicia).getHelp() + ")");
				         spravne = slova.get(pozicia).getSk();
				         pocet = 1;
				         if (pocetlimit > slova.size()) pocetlimit = slova.size();
				         spravnezodpovedane = 0;
				         nespravne = 0;
				         imm.showSoftInput(preklad, InputMethodManager.SHOW_IMPLICIT);
				         
				         timer = initializeTimer(limit);
				         timer.start();
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
					if (!preklad.getText().toString().isEmpty() || timeleft == 0){
					timer.cancel();
					pocet++;
					prelozene = preklad.getText().toString();
					preklad.setText("");
					if(prelozene.compareTo(spravne) == 0){
						slova.get(pozicia).spravne(limit - timeleft, limit);
						slova.get(pozicia).setTime((int) (System.currentTimeMillis()/1000));
						db.upravPocetOdpovedi(slova.get(pozicia));
						spravnezodpovedane++;
						Toast.makeText(getBaseContext(),"Správne",
							       Toast.LENGTH_SHORT).show();
						zobrazene = 0;
						wrong.setVisibility(4);
						tick.setVisibility(1);
						MediaPlayer mediaPlayer = MediaPlayer.create(Anglictina.this, R.raw.cartoon108);
						mediaPlayer.start();
					}else {
						slova.get(pozicia).nespravne(limit - timeleft, limit);
						slova.get(pozicia).setTime((int) (System.currentTimeMillis()/1000));
						nespravne++;
						db.upravPocetOdpovedi(slova.get(pozicia));
						Toast.makeText(getBaseContext(),"Nesprávne",
							       Toast.LENGTH_SHORT).show();
						zlezodpovedane.add("EN: " + slova.get(pozicia).getEn() 
								+ " (" + slova.get(pozicia).getHelp() + ")" 
								+ " SK: " + slova.get(pozicia).getSk()
								+ " Odpoveï: " + prelozene);
						zobrazene = 0;
						tick.setVisibility(4);
						wrong.setVisibility(1);
						MediaPlayer mediaPlayer = MediaPlayer.create(Anglictina.this, R.raw.cartoon048);
						mediaPlayer.start();
						 
					}
					pozicia++;
					if (pozicia < pocetlimit){
						word.setText(slova.get(pozicia).getEn() + " (" + slova.get(pozicia).getHelp() + ")");
						spravne = slova.get(pozicia).getSk();
						timer = initializeTimer(limit);
						timer.start();
			        } else{
			        	word.setVisibility(4);
			        	time.setVisibility(4);
						enter.setVisibility(4);
						preklad.setVisibility(4);
						pauza.setVisibility(4);
						wrong.setVisibility(4);
						tick.setVisibility(4);
						start.setVisibility(1);
						vyberkategorie.setVisibility(1);
						imm.hideSoftInputFromWindow(preklad.getWindowToken(), 0);
						AlertDialog.Builder adb = new AlertDialog.Builder(
								Anglictina.this);
						adb.setTitle("Súhrn");
						String message = "Správne: " + spravnezodpovedane + "\nNesprávne: " + nespravne + "\nZle zodpovedané slová:\n";
						for (int i = 0; i < zlezodpovedane.size(); i++) {
							message = message + zlezodpovedane.get(i) + "\n";
						}
						adb.setMessage(message);
						adb.setNeutralButton("OK", null);
						adb.show();
			        }
					}
				default:
					break;
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
				        imm.showSoftInput(preklad, InputMethodManager.SHOW_IMPLICIT);
						pauza.setText("Pauza");
						word.setVisibility(1);
						enter.setVisibility(1);
						preklad.setVisibility(1);
						timer = initializeTimer(timeleft);
						if (countpauses == 3) pauza.setVisibility(4);
						timer.start();
						
					}else {
						timer.cancel();
						imm.hideSoftInputFromWindow(preklad.getWindowToken(), 0);
						pauza.setText("Pokraèova");
						countpauses++;
						pauznute = true;
						word.setVisibility(4);
						enter.setVisibility(4);
						preklad.setVisibility(4);
						wrong.setVisibility(4);
						tick.setVisibility(4);
					}
				default: break;
				}
			}
		});
	}

}
