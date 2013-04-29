package com.example.zootypers;

import java.util.Observable;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SinglePlayerGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO delete once completed
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_player_game);
		
		int image = R.drawable.background_grassland;
		Drawable imageD = getResources().getDrawable(image);
		ViewGroup ll = (ViewGroup) findViewById(R.id.single_player_game_layout);
		ll.setBackground(imageD);
		
		int animal = R.drawable.animal_giraffe;
		Drawable animalD = getResources().getDrawable(animal);
		ImageView animalView = (ImageView)findViewById(R.id.animal_pic);
		animalView.setImageDrawable(animalD);
		
		
		/*
		// get the items in the intent
				Intent optionalIntent = getIntent();
				Bundle extras = optionalIntent.getExtras();
				
				// get the items for StartTaskActivity
				String subject = extras.getString("subject chosen");
				int durationHours = extras.getInt("duration hour");
				int durationMins = extras.getInt("duration min");
				String convertDHours = String.valueOf(durationHours);
				String convertDMins = String.valueOf(durationMins);
				String mainString = "You will be studying " + subject;
				mainString += " for " + convertDHours + " hours ";
				mainString += " and " + convertDMins + " minutes ";
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_player_game, menu);
		return true;
	}
	
	/**
	  * Displays the initial screen of the single player game.
	  * @param animalID int referring to the id of the selected animal image,
	  * e.g. R.drawable.elephant_color.
	  * @param backgroudID int referring to the id of the selected background image.
	  * @param words An array of the words to display. Must have a length of 5.
	  */
	public void initialDisplay(Drawable animalID, Drawable backgroundID, String[] words) {
	  if (words.length != 5) {
	    // error!
	  }
	   
	  // set the background of the game
	  ViewGroup ll = (ViewGroup) findViewById(R.id.single_player_game_layout);
	  ll.setBackground(backgroundID);

	  // set the animal image
	  ImageView animalView = (ImageView)findViewById(R.id.animal_pic);
	  animalView.setImageDrawable(animalID);

	  for (int i = 0; i < 5; i++)
	    displayWord(i, words[i]);
	    
	  displayTimer(60);
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
	  TextView wordBox = (TextView) getByStringId("word" + wordIndex);
	  wordBox.setText(word);
	}
	  
	/**
	 * Updates the timer on the screen.
	 * @param secondsLeft The number of seconds to display.
	 */
	public void displayTimer(int secondsLeft) {
	  // TODO update the timer to display secondsLeft

	  TextView timeString = (TextView) findViewById(R.id.time_left);
	  timeString.setText(secondsLeft + "");
	  // TODO uncomment once an TextView with id timer is made
	  //TextView timerBox = (TextView) findViewById(R.id.timer);
	  //timerBox.setText(secondsLeft);
    }
	  
	/**
	 * Updates the score on the screen.
	 * @param score The score to display.
	 */
	public void displayScore(int score) {
	  // TODO update score to show new score

	  // TODO uncomment once an TextView with id score is made
	  //TextView scoreBox = (TextView) findViewById(R.id.score);
	  //scoreBox.setText(score);
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

	/**
	 * @param id The id of the View to get as a String.
	 * @return The View object with that id
	 */
	private View getByStringId(String id) {
	  return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
    }
	
	// dummy method for testing
    public void fillTexts(View view) {
    	
    	TextView text1 = (TextView) findViewById(R.id.word1);
    	String var = "jamesiscool";
    	text1.setText(Html.fromHtml("<font color=green>" + var + "</font>"));
    	
    	TextView text2 = (TextView) findViewById(R.id.word2);
    	text2.setText("Queztezcal");
    	
    	TextView text3 = (TextView) findViewById(R.id.word3);
    	text3.setText(Html.fromHtml("<font color=red>thistextshouldbered</font>"));
    	
    	TextView text4 = (TextView) findViewById(R.id.word4);
    	text4.setText("hello");
    	
    	TextView text5 = (TextView) findViewById(R.id.word5);
    	text5.setText(Html.fromHtml("<font color=purple>gohuskies!</font>hellooo"));
    }
	 
}
