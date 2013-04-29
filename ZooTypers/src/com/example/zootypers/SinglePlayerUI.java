package com.example.zootypers;

import java.util.Observable;
import java.util.Observer;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class SinglePlayerUI extends Activity implements Observer {
  
  /**
   * @param id The id of the View to get as a String.
   * @return The View object with that id
   */
  private View getByStringId(String id) {
   return findViewById(getResources().getIdentifier(id, "id", getPackageName()));
  }
  
  /**
   * Displays the initial screen of the single player game.
   * @param animalID int referring to the id of the selected animal image,
   * e.g. R.drawable.elephant_color.
   * @param backgroudID int referring to the id of the selected background image.
   * @param words An array of the words to display. Must have a length of 5.
   */
  public void initialDisplay(Drawable animalID, Drawable backgroudID, String[] words) {
    if (words.length != 5) {
      // error!
    }
    
    // TODO uncomment once an ImageView with id animal is made
    //ImageView animalBox = (ImageView) findViewById(R.id.animal);
    //animalBox.setImageDrawable(animalID);
    
    
    // TODO uncomment once an ImageView with id background is made
    //ImageView backgroundBox = (ImageView) findViewById(R.id.background);
    //backgroundBox.setImageDrawable(backgroudID);
    
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

  @Override
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

}
