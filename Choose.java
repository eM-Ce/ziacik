package com.example.ziacik;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Choose extends ListActivity {

	String[] myActivities; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("MyList", "spustam apku");
		myActivities = getResources().getStringArray(R.array.listofallactivities);
		setListAdapter(new ArrayAdapter<String>(Choose.this,
				android.R.layout.simple_list_item_1, myActivities));
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String className = myActivities[position];
		try {
			className = Normalizer.normalize(className, Form.NFD)
				    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
			className = className.replaceAll("/Uprav", "");
			Class<?> mainClass = Class.forName("com.example.ziacik."	+ className.replaceAll("\\s+", ""));
			Intent in = new Intent(Choose.this, mainClass);
			startActivity(in);
		} catch (Exception E) {
			E.printStackTrace();
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose, menu);
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
