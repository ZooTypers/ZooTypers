package com.example.zootypers;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.Parse;

@SuppressLint("NewApi")
public class MultiPlayer extends Activity implements Observer {
	private MultiPlayerModel model;
    protected final int NUM_WORDS = 5;  
    protected int bg;
   

    // for the game timer
    protected GameTimer gameTimer;
    protected final long INTERVAL = 1000; // 1 second
    public final static long START_TIME = 60000; // 1 minute
    public static boolean paused = false;
    private long currentTime;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get animal & background selected by user
		setContentView(R.layout.activity_pregame_selection_multi);
		Drawable animal = ((ImageButton) findViewById
				(getIntent().getIntExtra("anm", 0))).getDrawable();
		bg = getIntent().getIntExtra("bg", 0);
		Drawable background = ((ImageButton) findViewById(bg)).getDrawable();

		Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", "SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
		//setupWordsList();
		// this can be fetched from the database once login is properly setup
		// should not need these in actual model
		String uname = "david";

		// start model
		model = new MultiPlayerModel(States.difficulty.MEDIUM, this.getAssets(), NUM_WORDS, uname);
		model.addObserver(this);

		// change screen view
		setContentView(R.layout.activity_multi_player);
		initialDisplay(animal, background);

		// create and start timer
		gameTimer = new GameTimer(START_TIME, INTERVAL);
		gameTimer.start();
	}
	
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_player, menu);
        return true;
    }
  
    @Override
    /**
     * When the user types a letter, this listens for it.
     */
    public final boolean onKeyDown(final int key, final KeyEvent event){  	
    	char charTyped = event.getDisplayLabel();
        charTyped = Character.toLowerCase(charTyped);
        model.typedLetter(charTyped);
        return true;
    }
    
    /**
    * @param wordIndex The index of the word to display; 0 <= wordIndex < 5.
    * @param word The word to display.
    */
    public final void displayWord(final int wordIndex, final String word) {
        if ((wordIndex < 0) || (wordIndex >= NUM_WORDS)) {
            // error!
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
     * Displays the initial screen of the single player game.
     * @param animalID Drawable referring to the id of the selected animal image,
     * e.g. R.drawable.elephant_color.
     * @param backgroudID Drawable referring to the id of the selected background image.
     * @param words An array of the words to display. Must have a length of 5.
     */
    public void initialDisplay(Drawable animalID, Drawable backgroundID) {
        // display animal
        ImageView animalImage = (ImageView) findViewById(R.id.animal_image);
        animalImage.setImageDrawable(animalID);

        // display background
        ViewGroup layout = (ViewGroup) findViewById(R.id.game_layout);
        layout.setBackground(backgroundID);

        model.populateDisplayedList();

        // TODO figure out how to change milliseconds to seconds. it skips numbers
        displayTime(START_TIME / INTERVAL);

        displayScore(0);
    }

	/**
	 * Displays the oppenent's animal on the screen.
	 * @param score The score to display.
	 */
	@SuppressLint("NewApi")
	public final void displayOpponentAnimal(final Drawable animal) {
		TextView oppAnimal = (TextView) findViewById(R.id.opp_animal_image);
		oppAnimal.setBackground(animal);
	}

	/**
	 * Updates the oppenent's score on the screen.
	 * @param score The score to display.
	 */
	public final void displayOpponentScore(final int score) {
		TextView currentScore = (TextView) findViewById(R.id.opp_score);
		currentScore.setText(Integer.toString(score));
	}


	// TODO overrride update method
	/**
	 * Observer for model.
	 * @param arg0 Thing being observes.
	 * @param arg1 State.
	 */
	@Override
	public void update(final Observable arg0, final Object arg1) {
		MultiPlayerModel mpM;
		if (arg0 instanceof MultiPlayerModel) {
			mpM = (MultiPlayerModel) arg0;

			if (arg1 instanceof States.update) {
				States.update change = (States.update) arg1;
				TextView tv = (TextView)findViewById(R.id.typedError_prompt);
				if (change == States.update.FINISHED_WORD) {
					displayScore(mpM.getScore());
					displayWord(mpM.getCurrWordIndex(), mpM.getCurrWord());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.HIGHLIGHT) {
					highlightWord(mpM.getCurrWordIndex(), mpM.getCurrWord(), 
								  mpM.getCurrLetterIndex());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.OPPONENT_SCORE) {
					displayScore(mpM.getOpponentScore());
					tv.setVisibility(TextView.INVISIBLE);
				} else if (change == States.update.WRONG_LETTER) {
					//final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
					//final RelativeLayout rl = (RelativeLayout) findViewById(R.id.single_game_layout);
					//tg.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP);
					tv.setVisibility(TextView.VISIBLE);

				}

			}
		}
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
	
	/**
	 * Called when the timer runs out; goes to the post game screen.
	 */
	public final void goToPostGame() {
		Intent intent = new Intent(this, PostGameScreenMulti.class);
		// pass score
		intent.putExtra("score", ((TextView) findViewById(R.id.score)).getText().toString());
		// TODO get whether you won from the model
		intent.putExtra("won", true);
		intent.putExtra("bg", bg);
		startActivity(intent);
	}
	
    /**
    * @param id The id of the View to get as a String.
    * @return The View object with that id
    */
    public final View getByStringId(final String id) {
        return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
    }

    
    /**
     * Timer for game.
     * @author ZooTypers
     */
     public class GameTimer extends CountDownTimer {
         /**
         * @param startTime Amount of time player starts with.
         * @param interval Amount of time between ticks.
         */
         public GameTimer(final long startTime, final long interval) {
             super(startTime, interval);
         }

         @Override
         public final void onFinish() {
             // TODO add game over message before going to post game
             goToPostGame();
         }

         @Override
         public final void onTick(final long millisUntilFinished) {
             currentTime = millisUntilFinished;
             displayTime(TimeUnit.MILLISECONDS.toSeconds(currentTime));
         }
     }
}
