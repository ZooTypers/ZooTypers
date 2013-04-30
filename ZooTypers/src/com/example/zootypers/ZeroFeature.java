package com.example.zootypers;

import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ZeroFeature extends Activity {

	private WordDB worddb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zero_feature);
		
		worddb = new WordDB(this);
		worddb.resetDatabase();
		addWords();
		fetchData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zero_feature, menu);
		return true;
	}
	
	public void goToStart(View view) {
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}
	
	private void addWords() {
		worddb.createWordData("hello", 1);
		worddb.createWordData("Jamesiscool", 10);
		worddb.createWordData("Xanadu", 4);
		worddb.createWordData("Quetzalcoatl", 8);
	}
	
	// fetch some set of the data in the database
	private void fetchData() {
		Map<WordData, Integer> results = worddb.getAllWords();
		Set<WordData> resultSet = results.keySet();
		WordData[] resultArray = resultSet.toArray(new WordData[0]);
		
		WordData firstEntry = resultArray[0];
		// set the texts in the TextViews
		String firstString = convertToString(firstEntry, results.get(firstEntry));
		TextView firstResult = (TextView) findViewById(R.id.database_string1);
		firstResult.setText(firstString);
		
		WordData secondEntry = resultArray[1];
		String secondString = convertToString(secondEntry, results.get(secondEntry));
		TextView secondResult = (TextView) findViewById(R.id.database_string2);
		secondResult.setText(secondString);
		
		WordData thirdEntry = resultArray[2];
		String thirdString = convertToString(thirdEntry, results.get(thirdEntry));
		TextView thirdResult = (TextView) findViewById(R.id.database_string3);
		thirdResult.setText(thirdString);
		
		WordData fourthEntry = resultArray[3];
		String fourthString = convertToString(fourthEntry, results.get(fourthEntry));
		TextView fourthResult = (TextView) findViewById(R.id.database_string4);
		fourthResult.setText(fourthString);
		
	}
	
	private String convertToString(WordData wd, Integer diff) {
		return wd.toString() + " with difficulty " + diff;
	}
}
