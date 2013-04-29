package com.example.zootypers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TitlePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_title_page, menu);
		return true;
	}
	
	// Called when the user clicks button for zero feature
	public void goToZF(View view) {
		Intent intent = new Intent(this, ZeroFeature.class);
		startActivity(intent);
	}
	
	public void goToSingleGame(View view) {
		Intent intent = new Intent(this, SinglePlayerGame.class);
		startActivity(intent);
	}
	
	public void goToPreGameSelection(View view) {
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);
		
	}
}
