package com.example.zootypers.ui;

import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiPlayerModel;
import com.example.zootypers.util.EmptyQueueException;
import com.example.zootypers.util.InternalErrorException;
import com.example.zootypers.util.InternetConnectionException;
import com.example.zootypers.util.States;
import com.parse.Parse;

/**
 * Activity / UI for MultiPlayer screen.
 * @author cdallas
 *
 */
@SuppressLint("NewApi")
public class MultiPlayer extends Player {

	// the username of the user currently trying to play a game
	private String username;
	
	// the game timer that will give a time limit
	private GameTimer gameTimer;
	
	// used for the communicating with model
	private MultiPlayerModel model;
	
//	private PopupWindow ppw;

	/*
	 * flips the animal being displayed horizontally so that the animal
	 * is facing the other direction.
	 * 
	 * @param id The id of an animal ImageButton on the pregame screen.
	 * @return The resource id of the drawable image facing the opposite way
	 * (i.e. the opponent's version of the animal).
	 */
	private int reverseDrawable(int id) {
		if (id == R.id.giraffe_button) {
			return R.drawable.animal_giraffe_opp;
		} else if (id == R.id.kangaroo_button) {
			return R.drawable.animal_kangaroo_opp;
		} else if (id == R.id.lion_button) {
			return R.drawable.animal_lion_opp;
		} else if (id == R.id.monkey_button) {
			return R.drawable.animal_monkey_opp;
		} else if (id == R.id.panda_button) {
			return R.drawable.animal_panda_opp;
		} else if (id == R.id.penguin_button) {
			return R.drawable.animal_penguin_opp;
		} else if (id == R.id.turtle_button) {
			return R.drawable.animal_turtle_opp;
		} else {
			return R.drawable.animal_elephant_opp;
		}
	}

//	/**
//	 * Shows the loading popup window.
//	 */
//	private void showLoadScreen() {
//		LayoutInflater layoutInflater = 
//				(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//		View popupView = layoutInflater.inflate(R.layout.login_popup, null);
//		ppw = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
//		// TODO problem here
//		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.pregame_layout);
//		// set the position and size of popup
//		ppw.showAtLocation(parentLayout, Gravity.CENTER, 0, 0);
//	}
//
//	/**
//	 * Exits the loading popup window.
//	 */
//	private void dismissLoadScreen() {
//		ppw.dismiss();
//	}


	/*
	 *  Called when the activity is starting. uses the information that was picked
	 *  in the pre selection screen and sets a background, animal, and opponent's
	 *  also creates a multi player model and does the initial display.
	 *  once done created there should already be an opponent ready to play.
	 *  
	 *  @params savedInstanceState, the state of the intent before exiting
	 *  this activity before
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Get animal & background selected by user
		setContentView(R.layout.activity_pregame_selection_multi);
		int anmID = getIntent().getIntExtra("anm", 0);
		Drawable animal = ((ImageButton) findViewById(anmID)).getDrawable();
		bg = getIntent().getIntExtra("bg", 0);
		Drawable background = ((ImageButton) findViewById(bg)).getDrawable();

		// Initialize the database
		Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
							   "SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 

		// Get the user name
		username = getIntent().getStringExtra("username");

		//showLoadScreen();

		// Start model, passing number of words, user name, and selected animal
		model = new MultiPlayerModel(NUM_WORDS, username, anmID);
		model.addObserver(this);
		try {
			model.beginMatchMaking();
			model.setWordsList();
			// Get the opponent's animal from the model
			int oppAnimal = reverseDrawable(model.getOpponentAnimal());
			// Display the multiplayer screen
			setContentView(R.layout.activity_multi_player);
			initialDisplay(animal, background, oppAnimal);
		} catch (InternetConnectionException e) {
			error(States.error.CONNECTION);
			return;
		} catch (EmptyQueueException e) {
			error(States.error.NOOPPONENT);
			return;
		} catch (InternalErrorException e) {
			error(States.error.INTERNAL);
			return;
		}

		//dismissLoadScreen();

		// Create and start timer
		gameTimer = new GameTimer(START_TIME, INTERVAL);
		gameTimer.start();
	}


	/**
	 * Initialize the contents of the Activity's standard options menu. 
	 * 
	 * @params menu, that uses added to the action bar if it is present.
	 */
	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.multi_player, menu);
		return true;
	}

	/**
	 * Displays the initial screen of the single player game.
	 * @param animal Drawable referring to the id of the selected animal image,
	 * e.g. R.drawable.elephant_color.
	 * @param backgroudID Drawable referring to the id of the selected background image.
	 * @param words An array of the words to display. Must have a length of 5.
	 */
	public void initialDisplay(Drawable animal, Drawable background, int oppAnimal) {
		// display animal
		ImageView animalImage = (ImageView) findViewById(R.id.animal_image);
		animalImage.setImageDrawable(animal);

		// display opponent's animal
		ImageView oppAnimalImage = (ImageView) findViewById(R.id.opp_animal_image);
		oppAnimalImage.setBackgroundResource(oppAnimal);

		// display background
		ViewGroup layout = (ViewGroup) findViewById(R.id.game_layout);
		layout.setBackground(background);

		model.populateDisplayedList();

		displayTime(START_TIME / INTERVAL);

		displayScore(0);
	}

	/**
	 * Quits the game and goes to the title page.
	 * @param view The button clicked.
	 */
	public final void goToTitlePage(final View view) {
		// Clean up the database
		model.deleteUser();

		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}

	/**
	 * Called where there is a error.
	 * Quits the game and goes to the corresponding error page.
	 */
	public final void error(States.error err) {
		// Clean up the database
		model.deleteUser();

		Intent intent = new Intent(this, ErrorScreen.class);
		// Pass username
		intent.putExtra("username", username);
		
		if (err.equals(States.error.NOOPPONENT))
			intent.putExtra("error", R.layout.activity_no_opponent_error);
		else if (err.equals(States.error.INTERNAL))
			intent.putExtra("error", R.layout.activity_interrupt_error);
		else 
			intent.putExtra("error", R.layout.activity_connection_error);
		
		startActivity(intent);
	}
	
	/**
	 * Called when the timer runs out; goes to the post game screen.
	 */
	public final void goToPostGame() {
		// Show game over message before going to post game
		findViewById(R.id.game_over).setVisibility(0);

		// sets themselves as done with the game
		try {
			model.setUserFinish();
		} catch (InternetConnectionException e) {
			error(States.error.CONNECTION);
			return;
		}

		Intent intent = new Intent(this, PostGameScreenMulti.class);

		// Pass scores and if you won to post game screen
		int myScore = model.getScore();
		int oppScore = model.getOpponentScore();
		intent.putExtra("score", myScore);
		intent.putExtra("oppScore", oppScore);
		if (myScore > oppScore) {
			intent.putExtra("result", 1);			
		} else if (myScore == oppScore) {
			intent.putExtra("result", 0);			
		} else {			
			intent.putExtra("result", -1);			
		}

		// Pass if opponent completed the game
		try {
			if (!model.isOpponentFinished()) {
				error(States.error.CONNECTION);
			}
			//intent.putExtra("discon", !model.isOpponentFinished());
		} catch (InternetConnectionException e) {
			error(States.error.CONNECTION);
			return;
		} catch (InternalErrorException e) {
			error(States.error.INTERNAL);
			return;
		}

		// Pass background to post game screen
		intent.putExtra("bg", bg);

		// Pass username
		intent.putExtra("username", username);

		model.deleteUser();
		startActivity(intent);	
	}
	
	@Override
	/**
	 * Called when the user types a letter; passes the letter to the model.
	 */
	public boolean onKeyDown(final int key, final KeyEvent event){ 	  
		char charTyped = event.getDisplayLabel();
		charTyped = Character.toLowerCase(charTyped);
		model.typedLetter(charTyped);
		return true;
	}
	
	/**
	 * Timer for the game.
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
			goToPostGame();
		}

		@Override
		public final void onTick(final long millisUntilFinished) {
			model.refreshInBackground();
			// edge case because 0 does not tick, in order to show 0 we have to -1000
			currentTime = millisUntilFinished - 1000;
			displayTime(TimeUnit.MILLISECONDS.toSeconds(currentTime));
		}
	}
}
