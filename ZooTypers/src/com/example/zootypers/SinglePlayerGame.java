package com.example.zootypers;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class SinglePlayerGame extends Activity {

	private GameTimer gameTimer;
	private final long START_TIME = 60000; // 1 minute
	private final long INTERVAL = 1000; // 1 second
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    
		// Get animal & background selected by user
    setContentView(R.layout.activity_pregame_selection);
		Drawable animal = ((ImageButton)findViewById(getIntent().getIntExtra("anm", 0))).getDrawable();
    Drawable background = ((ImageButton)findViewById(getIntent().getIntExtra("bg", 0))).getDrawable();
		    
		setContentView(R.layout.activity_single_player_game);
		
		// TODO get words from model
		String[] words = {"word1", "word2", "word3", "word4", "word5"};
		initialDisplay(animal, background, words);
		
		gameTimer = new GameTimer(START_TIME, INTERVAL);
		gameTimer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_player_game, menu);
		return true;
	}

	/**
	   * Displays the initial screen of the single player game.
	   * @param animalID Drawable referring to the id of the selected animal image,
	   * e.g. R.drawable.elephant_color.
	   * @param backgroudID Drawable referring to the id of the selected background image.
	   * @param words An array of the words to display. Must have a length of 5.
	   */
	public void initialDisplay(Drawable animalID, Drawable backgroundID, String[] words) {
	  if (words.length != 5) {
	    // TODO error!
	  }
	  
	  // display animal
	  ImageView animalImage = (ImageView) findViewById(R.id.animal_image);
	  animalImage.setImageDrawable(animalID);
	  
	  // display background
	  ViewGroup layout = (ViewGroup) findViewById(R.id.single_game_layout);
	  layout.setBackground(backgroundID);
	  
	  for (int i = 0; i < Math.max(words.length, 5); i++) {
	    displayWord(i, words[i]);
	  } 
	  
    // TODO figure out how to change milliseconds to seconds. it skips numbers
    displayTime(START_TIME / 1000);
    
	  displayScore(0);
	}
	
	/**
	 * @param wordIndex The index of the word to display; 0 <= wordIndex < 5.
	 * @param word The word to display.
	 */
	public void displayWord(int wordIndex, String word) {
	  if (wordIndex < 0 || wordIndex >= 5) {
	    // error!
	  }
	  // TODO : figure out what this means
	  TextView wordBox = (TextView) getByStringId("word" + wordIndex);
	  wordBox.setText(word);
  }

	/**
	 * Updates the timer on the screen.
	 * @param secondsLeft The number of seconds to display.
	 */
	public void displayTime(long secondsLeft) {
	  TextView timerBox = (TextView) findViewById(R.id.time_text);
	  timerBox.setText(Long.toString(secondsLeft));
  }
	  
	/**
	 * Updates the score on the screen.
	 * @param score The score to display.
	 */
	public void displayScore(int score) {
	  TextView currentScore = (TextView) findViewById(R.id.score);
	  currentScore.setText(Integer.toString(score));
	}
	  
	/**
	 * Highlights the letterIndex letter of the wordIndex word. letterIndex must
	 * not be beyond the scope of the word.
	 * @param wordIndex The index of the word to highlight; 0 <= wordIndex < 5.
	 * @param letterIndex The index of the letter in the word to highlight.
	 */
	public void highlightWord(int wordIndex, int letterIndex) {
	  // TODO highlight the letterIndex letter of the wordIndex word
	}

	public void update(Observable arg0, Object arg1) {
	  SinglePlayerModel spM;
		if (arg0 instanceof SinglePlayerModel) {
			spM = (SinglePlayerModel) arg0;
		  
			if (arg1 instanceof States.update) {
				States.update change = (States.update) arg1;

				if (change == States.update.FINISHED_WORD) {
				  displayScore(spM.getScore());
				  displayWord(spM.getCurrWordIndex(), spM.getCurrWord());
				} else if (change == States.update.HIGHLIGHT) {
				  highlightWord(spM.getCurrWordIndex(), spM.getCurrLetterIndex());
				} else if (change == States.update.WRONG_LETTER) {
					// TODO print an error message?
				}
				  
			}
		}
	}
	
	// dummy method for testing
	public void fillTexts(View view) {
		// testing that variables work
		String testString = "jamesiscool";
		TextView firstWord = (TextView) findViewById(R.id.word0);
		firstWord.setText(Html.fromHtml("<font color=green>" + testString + "</font>"));
		
		TextView secondWord = (TextView) findViewById(R.id.word1);
		secondWord.setText("Quetezecal");
		
		TextView thirdWord = (TextView) findViewById(R.id.word2);
		thirdWord.setText(Html.fromHtml("<font color=red>thisshouldbered</font>"));
		
		TextView fourthWord = (TextView) findViewById(R.id.word3);
		fourthWord.setText("hello");
		
		TextView fifthWord = (TextView) findViewById(R.id.word4);
		fifthWord.setText(Html.fromHtml("<font color=purple>gohuskies!</font>asdfadfsd"));
	}
	  
	/**
	 * Called when the timer runs out; goes to the post game screen.
	 */
	public void goToPostGame() {
		Intent intent = new Intent(this, PostGameScreen.class);
		// pass score
		intent.putExtra("score", ((TextView)findViewById(R.id.score)).getText().toString());
		startActivity(intent);    
	}
	
	/**
	 * @param id The id of the View to get as a String.
	 * @return The View object with that id
	 */
	private View getByStringId(String id) {
	  return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
  }
	
	public class GameTimer extends CountDownTimer {
		
		public GameTimer(long startTime, long interval) {
			super(startTime, interval);
		}
		
		@Override
		public void onFinish() {
			// TODO add game over message before going to post game
			// currentTime.setText("GAME OVER!!");
			goToPostGame();   
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
		  displayTime(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
		}
	}
}