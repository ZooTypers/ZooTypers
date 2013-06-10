package com.example.zootypers.ui;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.util.InterfaceUtils;

/**
 *
 * UI / Activity for post-game screen.
 * @author cdallas
 *
 */
public class PostGameScreen extends Activity {
	
	Integer score;
	PopupWindow ppw;
	private boolean savedScore;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		Log.i("ZooTypers", "entered post game");

		savedScore = false;
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
		Log.i("ZooTypers", "saving score post game");
		buildSavePopup();
	}

	/**
	 * Called when the user clicks the "Main Menu" button.
	 * @param view The button clicked
	 */
	public final void goToTitlePage(final View view) {
		Log.i("ZooTypers", "going to title page from post game");
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
		finish();
	}

	/**
	 * Called when the user clicks the "New Game" button.
	 * @param view The button clicked
	 */
	public void goToPreGameSelection(final View view) {
		Log.i("ZooTypers", "going to pre game from post game");
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);
		finish();
	}

	/**
	 * Helper method to build a popup screen for the
	 * save score popup
	 */
	@SuppressLint("InlinedApi")
	private void buildSavePopup() {
		if (savedScore) {
			InterfaceUtils.buildAlertDialog(this, R.string.already_saved_title, R.string.already_saved_msg);
			return;
		}
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.postgame_layout);
		View popupView = layoutInflater.inflate(R.layout.save_score_screen, null);
		ppw = new PopupWindow(popupView,
		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		savedScore = true;
	}

	/**
	 * Called when the user wants to exit out of the save name
	 * popup
	 * @param view the button that is clicked
	 */
	public void exitPopup(View view) {
		Log.i("ZooTypers", "exiting popup to save score");
		ppw.dismiss();
		savedScore = false;
	}
	
	/**
	 * Called when the user clicks the "Submit" button when
	 * a name is typed in
	 * @param view the button that is clicked
	 */
	public void submitName(View view) {
		Log.i("ZooTypers", "submitting name to be saved");
		
		// get the input from the user
		final View contentView = ppw.getContentView();
	    EditText savedNameInput = (EditText) contentView.findViewById(R.id.saved_name_input);
	    String savedNameString = savedNameInput.getText().toString();
	    // send the input the the leaderboard model
	    SingleLeaderBoardModel sl = new SingleLeaderBoardModel(getApplicationContext());
    	sl.addEntry(savedNameString, score);
		InterfaceUtils.buildAlertDialog(this, R.string.saved_title, R.string.saved_msg);
		ppw.dismiss();
	}
}
