package com.example.ziacik;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Choose extends ListActivity {

	String[] myActivities; 
	List<String> deti;
	DBHelperUsers db;
	AlertDialog.Builder adb;
	String dieta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MyList", "spustam apku");
		myActivities = getResources().getStringArray(R.array.listofallactivities);
		setListAdapter(new ArrayAdapter<String>(Choose.this,
				android.R.layout.simple_list_item_1, myActivities));
		
		db = new DBHelperUsers(this);
		deti = db.menaDeti();		
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                Choose.this,
                android.R.layout.select_dialog_singlechoice, deti);
		
		adb = new AlertDialog.Builder(
				Choose.this);
		adb.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dieta = arrayAdapter.getItem(which);
				setTitle("Žiaèik - " + dieta);
				
			}
		});
		adb.show();
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (dieta == null || dieta.isEmpty()) {
			adb.show();
		}else {
			String className = myActivities[position];
			try {
				className = Normalizer.normalize(className, Form.NFD)
						.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				className = className.replaceAll("/Uprav", "");
				Class<?> mainClass = Class.forName("com.example.ziacik."	+ className.replaceAll("\\s+", ""));
				Intent in = new Intent(Choose.this, mainClass);
				Bundle b = new Bundle();
				b.putString("meno", dieta);
				in.putExtras(b);
				startActivity(in);
			} catch (Exception E) {
				E.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.choose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Class<?> mainClass = null;
			try {
				mainClass = Class.forName("com.example.ziacik.RodicNastavenia");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Intent in = new Intent(Choose.this, mainClass);
			startActivity(in);
			return true;
		}
		if (id == R.id.action_profile){
			adb.show();
		}
	    return super.onOptionsItemSelected(item);
	}
}
