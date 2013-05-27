package com.example.zootypers.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.SingleLeaderBoardModel;

/**
 *
 * UI / Activity for post-game screen.
 * @author cdallas
 *
 */
public class PostGameScreen extends Activity {
	
	Integer score;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// get & display background
		setContentView(R.layout.activity_pregame_selection);
		Drawable background = ((ImageButton) 
		findViewById(getIntent().getIntExtra("bg", 0))).getDrawable();

		setContentView(R.layout.activity_postgame_screen);
		findViewById(R.id.postgame_layout).setBackground(background);

		// get and display score
		score = getIntent().getIntExtra("score", 0);
		TextView finalScore = (TextView) findViewById(R.id.final_score);
		finalScore.setText(score.toString());
	}

	@Override
	public void onBackPressed() {
		// do nothing
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.postgame_screen, menu);
		return true;
	}

	/**
	 * Saves the current score.
	 * @param view The button clicked
	 */
	public void saveScore(final View view) {
    	SingleLeaderBoardModel sl = new SingleLeaderBoardModel(getApplicationContext());
    	sl.addEntry("name" ,score);
		final String title = "Saved Score";
		final String message = "Your score has been successfully saved!";
		buildAlertDialog(title, message);
	}

	/**
	 * Called when the user clicks the "Main Menu" button.
	 * @param view The button clicked
	 */
	public final void goToTitlePage(final View view) {
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "New Game" button.
	 * @param view The button clicked
	 */
	public void goToPreGameSelection(final View view) {
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);
	}

    
    // TODO remove repetition from title page / options
	/**
	 * builds an AlertDialog popup with the given title and message
	 * @param title String representing title of the AlertDialog popup
	 * @param message String representing the message of the AlertDialog
	 * popup
	 */
	private void buildAlertDialog(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, close the dialog box
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show the message
		alertDialog.show();
	}
}
