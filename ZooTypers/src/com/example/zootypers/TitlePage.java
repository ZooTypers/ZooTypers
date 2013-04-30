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
  
  /**
   * Called when the user clicks the "Single Player" button.
   * @param view The button clicked
   */
	public void goToPreGameSelection(View view) {
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);		
	}
  
  /**
   * Called when the user clicks the "Multiplayer" button.
   * @param view The button clicked
   */
  public void goToPreGameSelectionMulti(View view) {
    Intent intent = new Intent(this, PreGameSelectionMulti.class);
    startActivity(intent);    
  }
  
  /**
   * Called when the user clicks the "Leaderboard" button.
   * @param view The button clicked
   */
  public void goToLeaderboard(View view) {
    // TODO send to login / leaderboard
    Intent intent = new Intent(this, Leaderboard.class);
    startActivity(intent);    
  }
  
  /**
   * Called when the user clicks the "Options" button.
   * @param view The button clicked
   */
  public void goToOptions(View view) {
    Intent intent = new Intent(this, Options.class);
    startActivity(intent);    
  }
	

  public void goToSingle(View view) {
    Intent intent = new Intent(this, SinglePlayerGame.class);
    startActivity(intent);
  }
}
