package com.example.zootypers.ui;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiPlayerModel;
import com.example.zootypers.core.PlayerModel;
import com.example.zootypers.util.States;

public abstract class Player extends Activity implements Observer {

	protected final int NUM_WORDS = 5;  
	protected int bg;
	
	protected final long INTERVAL = 1000; // 1 second
	public final static long START_TIME = 61000; // we give them 61 seconds to tick 60 times 
	protected long currentTime;
	
	public abstract void goToPostGame();
	public abstract void error(States.error err);
	
    /**
    * @param id The id of the View to get as a String.
    * @return The View object with that id
    */
    public final View getByStringId(final String id) {
        return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
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
				TextView tv = (TextView)findViewById(R.id.typedError_prompt);
				if (change == States.update.FINISHED_WORD) {
					displayScore(pM.getScore());
					displayWord(pM.getCurrWordIndex(), pM.getCurrWord());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.HIGHLIGHT) {
					highlightWord(pM.getCurrWordIndex(), pM.getCurrWord(), 
							pM.getCurrLetterIndex());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.WRONG_LETTER) {
					//final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
					//final RelativeLayout rl = (RelativeLayout) findViewById(R.id.single_game_layout);
					//tg.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP);
					tv.setVisibility(TextView.VISIBLE);
				} 
			}
		} 
		if (arg0 instanceof MultiPlayerModel&& arg1 instanceof States.update) {
			MultiPlayerModel mpM = (MultiPlayerModel) arg0;
			States.update change = (States.update) arg1;
			TextView tv = (TextView)findViewById(R.id.typedError_prompt);
			if (change == States.update.OPPONENT_SCORE) {
				displayOpponentScore(mpM.getOpponentScore());
				tv.setVisibility(TextView.INVISIBLE);
			}
		}
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
    * Reopens keyboard when it is closed
    * @param view The button clicked.
    * @author oaknguyen
    */
    public final void keyboardButton(final View view) {
        InputMethodManager inputMgr = (InputMethodManager) 
        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMgr.toggleSoftInput(0, 0);
    }	
}
