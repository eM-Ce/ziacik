package com.example.ziacik;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PridajSlovicka extends Activity {

	EditText anglicky;
	EditText slovensky;
	EditText menoKategorie;
	Button enter;
	String subor;
	Spinner kategorie;
	Button vybrat;
	List<String> nazvyKategorii;
	DBHelper db;
	boolean nova;
	boolean upravujem;
	int upravovane;
	String kat;
	ListView listview;
	SimpleAdapter sa;
	ArrayList<HashMap<String,String>> list;
	List<Slovo> slova;
	TextView instrukcia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pridaj_slovicka);
		
		db = new DBHelper(this);
		kategorie = (Spinner) findViewById(R.id.Kategorie);
		
		vybrat = (Button) findViewById(R.id.VybratKategoriu);
		
		nazvyKategorii = new ArrayList<String>();
		nazvyKategorii.add("Nová kategória");
		nazvyKategorii.addAll(db.kategorie());
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, nazvyKategorii);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		kategorie.setAdapter(dataAdapter);
				
		instrukcia = (TextView) findViewById(R.id.instrukcia);
		instrukcia.setVisibility(4);
		enter = (Button) findViewById(R.id.PridatSlovicka);
		enter.setVisibility(4);
		anglicky = (EditText) findViewById(R.id.Anglicky);
		anglicky.setVisibility(4);
		slovensky = (EditText) findViewById(R.id.SlovenskyText);
		slovensky.setVisibility(4);
		menoKategorie = (EditText) findViewById(R.id.MenoKategorie);
		menoKategorie.setVisibility(4);
		listview = (ListView) findViewById(R.id.Slovicka);
		listview.setVisibility(4);
		nova = false;
		upravujem = false;
		list = new ArrayList<HashMap<String,String>>();
		sa = new SimpleAdapter(this, list,
				android.R.layout.two_line_list_item ,
				new String[] { "sk","en" },
				new int[] {android.R.id.text1, android.R.id.text2});
		listview.setAdapter(sa);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				Log.d("ToDoListActivity", "ListView longclick");
				String itemName = list.get(position).toString();
				AlertDialog.Builder adb = new AlertDialog.Builder(
						PridajSlovicka.this);
				adb.setTitle("Delete?");
				adb.setMessage("Are you sure you want to delete " + itemName);
				final int positionToRemove = position;
				adb.setNegativeButton("Cancel", null);
				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						db.vymazSlovo(slova.get(positionToRemove));
						slova.remove(positionToRemove);
						list.remove(positionToRemove);
						sa.notifyDataSetChanged();
					}
				});
				adb.show();
				return true;
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				anglicky.setText(slova.get(position).getEn());
				slovensky.setText(slova.get(position).getSk());
				upravujem = true;
				upravovane = position;
			}
			
		});
	}
	
	public void save(View view){
		if (upravujem){
			if (anglicky.getText().toString().isEmpty() || slovensky.getText().toString().isEmpty()){
				Toast.makeText(getBaseContext(),"Slovo vymažete dlhším podržaním",
						Toast.LENGTH_SHORT).show();
				upravujem = false;
			}else {
				HashMap<String,String> item = new HashMap<String,String>();
				slova.get(upravovane).setEn(anglicky.getText().toString());
				slova.get(upravovane).setSk(slovensky.getText().toString());
				item.put( "sk", slova.get(upravovane).getSk());
				item.put( "en", slova.get(upravovane).getEn());
				list.set(upravovane, item);
				sa.notifyDataSetChanged();
				db.upravSlovo(slova.get(upravovane));
				upravujem = false;
			}
		}else if (nova){
			kat = menoKategorie.getText().toString();
			if (kat.isEmpty()){
				Toast.makeText(getBaseContext(),"Zadajte prosím kategóriu.",
						Toast.LENGTH_SHORT).show();
			}else if (nazvyKategorii.contains(kat)){
				Toast.makeText(getBaseContext(),"Kategória " + kat + " už existuje.",
						Toast.LENGTH_SHORT).show();
			} else {
				menoKategorie.setVisibility(4);
				nova = false;
			slova = new ArrayList<Slovo>();
			anglicky.setVisibility(1);
			slovensky.setVisibility(1);
			instrukcia.setVisibility(1);
			}
		} else if (anglicky.getText().toString().isEmpty() || slovensky.getText().toString().isEmpty()){
			Toast.makeText(getBaseContext(),"Nezadali ste slovo alebo niektorý jeho preklad.",
					Toast.LENGTH_SHORT).show();
			upravujem = false;
		}else {
			anglicky.setVisibility(1);
			slovensky.setVisibility(1);
			instrukcia.setVisibility(1);
			HashMap<String,String> item = new HashMap<String,String>();
			Slovo slovo = new Slovo(anglicky.getText().toString(), slovensky.getText().toString());
			item.put( "sk", slovo.getSk());
		    item.put( "en", slovo.getEn());
		    slova.add(slovo);
		    list.add(item);
		    db.pridajSlovo(slovo, kat);
		    Toast.makeText(getBaseContext(),"Slovíèko bolo pridane do kategórie: " + kat,
					Toast.LENGTH_SHORT).show();
		    anglicky.setText("");
		    slovensky.setText("");
		    sa.notifyDataSetChanged();
		}
	}
	
	public void vybrat(View view){
		kategorie.setVisibility(4);
		vybrat.setVisibility(4);
		enter.setVisibility(1);
		listview.setVisibility(1);
		kat = String.valueOf(kategorie.getSelectedItem());
		if (kat.equals("Nová kategória")){
			menoKategorie.setVisibility(1);
			nova = true;
		} else {
			anglicky.setVisibility(1);
			slovensky.setVisibility(1);
			instrukcia.setVisibility(1);
			nova = false;
			slova = db.slovaZoSuboru(kat);
			
			HashMap<String,String> item;
			for(int i=0;i<slova.size();i++){
			    item = new HashMap<String,String>();
			    item.put( "sk", slova.get(i).getSk());
			    item.put( "en", slova.get(i).getEn());
			    list.add( item );
			}
			sa.notifyDataSetChanged();
			

		}
		
	}

}
