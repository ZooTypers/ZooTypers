package com.example.zootypers;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;

public class SinglePlayer extends Activity {

	SinglePlayerModel Model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Model = new SinglePlayerModel("animal_elephant.png", "background_grassland.png", States.difficulty.EASY);
		setContentView(R.layout.activity_single_player);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_player, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int key, KeyEvent event){
		Model.typedLetter(event.getDisplayLabel());
		return true;
	}

}
