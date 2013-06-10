package com.example.zootypers.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiPlayerModel;
import com.example.zootypers.core.PlayerModel;
import com.example.zootypers.util.States;

/**
 * Activity / UI for Player screen
 * @author cdallas, littlpunk, kobyran
 */
@SuppressWarnings("unused")
public abstract class Player extends Activity implements Observer {

	// the total amount of time given to the user to type. (61 seconds to tick 60 times)
	public final static long START_TIME = 61000;

	// the interval for each time the clock ticks. (interval will be 1 second)
	protected final static long INTERVAL = 1000;

	// the number of words being displayed on the screen
	protected final int NUM_WORDS = 5;

	// the background ID the user chose in the pre-game selection
	protected int bg;

	// the current time on the game.
	protected long currentTime;

	// check for whether to vibrate or not
	private boolean useVibrate;
	
	// the vibrator that is used to vibrate the phone
	private Vibrator vibrator;

	protected MediaPlayer mediaPlayer;
	
	/**
	 * Called when the timer runs out; starts the post game screen
	 * activity with the correct data to pass.
	 */
	public abstract void goToPostGame();

	/**
	 * Called where there is a error.
	 * Quits the game and goes to the corresponding error page.
	 */
	public abstract void error(States.error err);

	/**
	 * @param id The id of the View to get as a String.
	 * @return The View object with that id
	 */
	public final View getByStringId(final String id) {
		return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
	}

	/**
	 * 
	 * Sets up the vibration so that if vibration in options is set to on then it
	 * vibrates whenever user types in the incorrect letter.
	 * 
	 */
	protected void setVibrate() {
		try {
			FileInputStream is = openFileInput("vibrate.txt");
			Log.i("Player", "use vibrate");
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			useVibrate = true;
		} catch (FileNotFoundException e){
			Log.i("Player", "no vibrate");
			useVibrate = false;
		}
	}

	/**
	 * 
	 * Set up the background music for the single & multi-player mode, so users can toggle
	 * on and off in the options menu.
	 * 
	 * @param mediaPlayer the MediaPlayer that will play background music
	 * @returns whether or not music should be playing.
	 */
	protected boolean setBGMusic(MediaPlayer mediaPlayer) {
		boolean playMusic = false;
		try {
			FileInputStream is = openFileInput("bgm.txt");
			playMusic = true;
			Log.i("ZooTypers", "play background music");
		} catch (FileNotFoundException e){
			Log.i("ZooTypers", "no background music");
		} 
		//play music
		if(playMusic){
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}

		return playMusic;
	}

	/**
	 * Observer for model.
	 * @param arg0 Thing being observes.
	 * @param arg1 State.
	 */
	@Override
	public void update(final Observable arg0, final Object arg1) {
		PlayerModel pM;
		if (arg0 instanceof PlayerModel) {
			pM = (PlayerModel) arg0;

			if (arg1 instanceof States.update) {
				States.update change = (States.update) arg1;
				TextView tv = (TextView) findViewById(R.id.typedError_prompt);
				if (change == States.update.FINISHED_WORD) {
					displayScore(pM.getScore());
					displayWord(pM.getCurrWordIndex(), pM.getCurrWord());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.HIGHLIGHT) {
					highlightWord(pM.getCurrWordIndex(), pM.getCurrWord(), 
							pM.getCurrLetterIndex());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.WRONG_LETTER) {
					tv.setVisibility(TextView.VISIBLE);
					//Check if vibrate
					//Vibrate
					if(useVibrate){
						vibrator.vibrate(150);
					}
				} 
			}
		} 
		if (arg0 instanceof MultiPlayerModel && arg1 instanceof States.update) {
			MultiPlayerModel mpM = (MultiPlayerModel) arg0;
			States.update change = (States.update) arg1;
			TextView tv = (TextView) findViewById(R.id.typedError_prompt);
			if (change == States.update.OPPONENT_SCORE) {
				displayOpponentScore(mpM.getOpponentScore());
				tv.setVisibility(TextView.INVISIBLE);
			}
		}
	}
	
	@Override
	public boolean onKeyDown(final int key, final KeyEvent event){
		if (key == KeyEvent.KEYCODE_VOLUME_DOWN || key == KeyEvent.KEYCODE_VOLUME_UP) {
			AudioManager am = (AudioManager) this.getSystemService(AUDIO_SERVICE);
			if (key == KeyEvent.KEYCODE_VOLUME_DOWN )
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			else 
				am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		}
		return true;
	}

	/**
	 * Updates the oppenent's score on the screen.
	 * @param score The score to display.
	 */
	public final void displayOpponentScore(final int score) {
		TextView currentScore = (TextView) findViewById(R.id.opp_score);
		currentScore.setText(Integer.toString(score));
	}

	/**
	 * @param wordIndex The index of the word to display; 0 <= wordIndex < 5.
	 * @param word The word to display.
	 */
	public final void displayWord(final int wordIndex, final String word) {
		if ((wordIndex < 0) || (wordIndex >= NUM_WORDS)) {
			error(States.error.INTERNAL);
			return;
		}
		TextView wordBox = (TextView) getByStringId("word" + wordIndex);
		wordBox.setText(word);
	}

	/**
	 * Updates the timer on the screen.
	 * @param secondsLeft The number of seconds to display.
	 */
	public final void displayTime(final long secondsLeft) {
		TextView timerBox = (TextView) findViewById(R.id.time_text);
		timerBox.setText(Long.toString(secondsLeft));
	}

	/**
	 * Updates the score on the screen.
	 * @param score The score to display.
	 */
	public final void displayScore(final int score) {
		TextView currentScore = (TextView) findViewById(R.id.score);
		currentScore.setText(Integer.toString(score));
	}

	/**
	 * Highlights the letterIndex letter of the wordIndex word. letterIndex must
	 * not be beyond the scope of the word.
	 * @param wordIndex The index of the word to highlight; 0 <= wordIndex < 5.
	 * @param letterIndex The index of the letter in the word to highlight.
	 */
	public void highlightWord(final int wordIndex, final String word, final int letterIndex) {
		TextView wordBox = (TextView) getByStringId("word" + wordIndex);
		String highlighted  = word.substring(0, letterIndex);
		String rest = word.substring(letterIndex);
		wordBox.setText(Html.fromHtml("<font color=#00FF00>" + highlighted + "</font>" + rest));
	}

	/**
	 * Reopens keyboard when it is closed
	 * @param view The button clicked.
	 * @author oaknguyen
	 */
	public final void keyboardButton(final View view) {
		Log.i("ZooTypers", "user has clicked on keyboard button");
		InputMethodManager inputMgr = (InputMethodManager) 
				getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMgr.toggleSoftInput(0, 0);
	}  

	/**
	 * Initialize the player one in both single and multi-player mode.
	 */
	@SuppressLint("NewApi")
	public void initialDisplay(Drawable animalID, Drawable backgroundID){
		// display animal
		ImageView animalImage = (ImageView) findViewById(R.id.animal_image);
		animalImage.setImageDrawable(animalID);

		// display background
		ViewGroup layout = (ViewGroup) findViewById(R.id.game_layout);
		layout.setBackground(backgroundID);

		displayTime(START_TIME / INTERVAL);

		displayScore(0);
		mediaPlayer = MediaPlayer.create(this, R.raw.sound2);
	}
}
