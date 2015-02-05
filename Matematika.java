package com.example.ziacik;

import java.util.Random;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Matematika extends Activity {

	TextView op1;
	TextView op2;
	TextView operation;
	Button start;
	Button enter;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		sEdit = prefs.edit();
		urovenCelkova = prefs.getInt("urovenCelkova", 0);
		urovenScitanie = prefs.getInt("urovenScitanie", 0);
		urovenOdcitanie = prefs.getInt("urovenOdcitanie", 0);
		urovenNasobenie = prefs.getInt("urovenNasobenie", 0);
		urovenDelenie = prefs.getInt("urovenDelenie", 0);
		Log.d("Celkova uroven", Integer.toString(urovenCelkova));
		op1 = (TextView) findViewById(R.id.Operand1);
		op2 = (TextView) findViewById(R.id.Operand2);
		operation = (TextView) findViewById(R.id.Word);
		start = (Button) findViewById(R.id.Uprav);
		enter = (Button) findViewById(R.id.Enter);
		vysledok = (EditText) findViewById(R.id.Vysledok);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.Uprav:
					op1.setVisibility(1);
					op2.setVisibility(1);
					operation.setVisibility(1);
					enter.setVisibility(1);
					vysledok.setVisibility(1);
					start.setVisibility(4);
					generuj();
					break;
			}
		}});
		enter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.Enter:
					int spravnyvysledok = 0;
					switch (operacia) {
					case 0:
						spravnyvysledok = operand1 + operand2;
						break;
					case 1:
						spravnyvysledok = operand1 - operand2;
						break;
					case 2:
						spravnyvysledok = operand1 * operand2;
						break;
					case 3:
						spravnyvysledok = operand1 / operand2;
						break;
					}
					int vyslPouziv = Integer.parseInt(vysledok.getText().toString());
					vysledok.setText("");
					if (vyslPouziv==spravnyvysledok){
						Toast.makeText(getApplicationContext(),
								"Správne", Toast.LENGTH_SHORT).show();
						urovenCelkova++;
						switch (operacia) {
						case 0:
							urovenScitanie++;
							break;
						case 1:
							urovenOdcitanie++;
							break;
						case 2:
							urovenNasobenie++;
							break;
						case 3:
							urovenDelenie++;
							break;
						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Nesprávne", Toast.LENGTH_SHORT).show();
						urovenCelkova--;
						switch (operacia) {
						case 0:
							urovenScitanie--;
							break;
						case 1:
							urovenOdcitanie--;
							break;
						case 2:
							urovenNasobenie--;
							break;
						case 3:
							urovenDelenie--;
							break;
						}
					}
					generuj();
					break;

				default:
					break;
				}
			}
		});

	}
	
	public void generuj(){
		Random r = new Random();
		if (urovenCelkova < 40) operacia = r.nextInt(1);
		else if (urovenCelkova < 80) operacia = r.nextInt(2);
		else if (urovenCelkova < 160) operacia = r.nextInt(3);
		else operacia = r.nextInt(4);
		switch (operacia) {
		case 0:
			operation.setText(" + ");
			if (urovenScitanie < 90 && urovenScitanie > 0){
				max1 = urovenScitanie+10;
				max2 = urovenScitanie+10;
			} else if (urovenScitanie > 0){
				max1 = 100;
				max2 = 100;
			} else {
				max1 = 10;
				max2 = 10;
			}
			operand1 = r.nextInt(max1)+1;
			operand2 = r.nextInt(max2)+1;
			break;
		case 1:
			if (urovenOdcitanie < 90 && urovenOdcitanie > 0){
				max1 = urovenOdcitanie+10;
				max2 = urovenOdcitanie+10;
			} else if (urovenOdcitanie > 0) {
				max1 = 100;
				max2 = 100;
			} else {
				max1 = 10;
				max2 = 10;
			}
			operation.setText(" - ");
			operand1 = r.nextInt(max1)+1;
			if (urovenOdcitanie < 40) max2 = operand1;
			operand2 = r.nextInt(max2)+1;
			break;
		case 2:
			if (urovenNasobenie < 75 && urovenNasobenie > 0){
				max1 = (urovenNasobenie / 5) + 5;
				max2 = (urovenNasobenie / 5) + 5;
			} else if (urovenNasobenie > 0){
				max1 = 20;
				max2 = 20;
			} else {
				max1 = 5;
				max2 = 5;
			}
			operation.setText(" x ");
			operand1 = r.nextInt(max1)+1;
			operand2 = r.nextInt(max2)+1;
			break;
		case 3:
			if (urovenDelenie < 75 && urovenDelenie > 0){
				max1 = (urovenDelenie / 10) + 3;
				max2 = (urovenDelenie / 5) + 5;
			} else if (urovenDelenie > 0){
				max1 = 10;
				max2 = 20;
			} else {
				max1 = 3;
				max2 = 5;
			}
			operation.setText(" / ");
			operand1 = r.nextInt(10)+1;
			operand2 = r.nextInt(20)+1;
			operand1 = operand1 * operand2;
			break;
		}
		op1.setText(operand1.toString());
		op2.setText(operand2.toString());
				
	}
	
	@Override
	public void onBackPressed() {
		sEdit.putInt("urovenCelkova", urovenCelkova);
		sEdit.putInt("urovenScitanie", urovenScitanie);
		sEdit.putInt("urovenOdcitanie", urovenOdcitanie);
		sEdit.putInt("urovenNasobenie", urovenNasobenie);
		sEdit.putInt("urovenDelenie", urovenDelenie);
		sEdit.commit();
		super.onBackPressed();
	}

}
