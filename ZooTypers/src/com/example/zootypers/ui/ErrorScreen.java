package com.example.zootypers.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;

/**
 * Generic error screen UI / activity.
 */
public class ErrorScreen extends Activity {

	private int error;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Get the kind of error, defaulting to an interrupt error
		error = getIntent().getIntExtra("error", 2130903041);
    	setContentView(error);
    
    	// Get and store the username
		username = getIntent().getStringExtra("username");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(error, menu);
		return true;
	}

	/**
	 * Called when the user clicks the "Main Menu" button.
	 * @param view The button clicked
	 */
	public final void goToTitlePage(final View view) {
		Log.i("ZooTypers", "leaving error screen to title page");
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "New Game" button.
	 * @param view The button clicked
	 */
	public void goToPreGameSelection(final View view) {
		Log.i("ZooTypers", "leaving error screen to multiplayer pre game page");
		Intent intent = new Intent(this, PreGameSelectionMulti.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
}
