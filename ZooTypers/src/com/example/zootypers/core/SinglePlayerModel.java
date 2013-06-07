package com.example.zootypers.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.io.IOUtils;

import android.content.res.AssetManager;
import android.util.Log;

import com.example.zootypers.util.States;

/** 
 * 
 * The Model class for Single Player store a list of words for the UI to display.
 * It keeps track of word and letter the user has typed and updates the view accordingly.
 * 
 * @author winglam, nhlien93, dyxliang
 * 
 */
public class SinglePlayerModel extends PlayerModel {

	// keep track of the user's current score
	private int score;

	// allows files in assets to be accessed.
	private AssetManager am;

	/**
	 * Constructs a new SinglePlayerModel that takes in the ID of an animal and background,
	 * and also what the difficulty level is. The constructor will initialize the words list
	 * and fills in what words the view should display on the screen.
	 *
	 * @param diff, the difficulty level that is selected by the user
	 * @param am, the asset manager of the SinglePlayer that gives the model to 
	 * access to the words stored in the asset folder
	 * @param wordsDis, the number of words being displayed on the screen
	 */
	public SinglePlayerModel(final States.difficulty diff, AssetManager am, int wordsDis) {
		super(wordsDis);
		this.am = am;
		score = 0;
		// generates the words list according to difficulty chosen
		getWordsList(diff);
	}

	/*
	 * Reads different files according to the difficulty passed in,
	 * parsed the words in the chosen file into wordsList, and shuffles
	 * the words in the list.
	 * 
	 * @param diff, the difficulty level that the user has chosen
	 */
	private void getWordsList(final States.difficulty diff) {
		Log.i("SinglePlayer", "reading file for words list");

		String file;
    if (Locale.getDefault().getDisplayLanguage().equals("français")) {
      if (diff == States.difficulty.EASY) {
        file = "4words-latin.txt";
      } else if (diff == States.difficulty.MEDIUM) {
        file = "5words-latin.txt";
      } else {
        file = "6words-latin.txt";
      }
    } else {
      if (diff == States.difficulty.EASY) {
        file = "4words.txt";
      } else if (diff == States.difficulty.MEDIUM) {
        file = "5words.txt";
      } else {
        file = "6words.txt";
      }
    }
    
		// read entire file as string, parsed into array by new line
		try {
			InputStream stream = am.open(file);
			String contents = IOUtils.toString(stream, "UTF-8");
			String[] tempArr = contents.split(System.getProperty("line.separator"));
			wordsList = Arrays.asList(tempArr);
		} catch (IOException e) {
			Log.e("SinglePlayer", "error reading file for words list", e);
		}

		// Shuffle the elements in the array
		Collections.shuffle(wordsList);
	}

	/**
	 * The typedLetter method handles what words and letter the user has
	 * typed so far and notify the view to highlight typed letter or fetch 
	 * a new word from the wordsList for the view to display accordingly.
	 * 
	 * @param letter, the letter that the user typed on the Android soft-keyboard
	 */
	public final void typedLetter(final char letter) {
		// currently not locked on to a word
		if (currWordIndex == -1) {
			for (int i = 0; i < wordsDisplayed.length; i++) {
				// if any of the first character in wordsDisplayed matched letter
				if (wordsList.get(wordsDisplayed[i]).charAt(0) == letter) {
					currWordIndex = i;
					currLetterIndex = 1;
					setChanged();
					Log.i("SinglePlayer", "typed the letter: " + letter);
					notifyObservers(States.update.HIGHLIGHT);
					return;
				}
			}
			// locked on to a word being typed (letter == the index of current letter index in the word)
		} else if (wordsList.get(wordsDisplayed[currWordIndex]).charAt(currLetterIndex) == letter) {

			// store length of current word
			int wordLen = wordsList.get(wordsDisplayed[currWordIndex]).trim().length();

			Log.i("SinglePlayer", "typed the letter: " + letter);
			// word is completed after final letter is typed
			if ((currLetterIndex + 1) >= wordLen) {
				score += wordLen;
				Log.i("SinglePlayer", "completed the word: " + 
						wordsList.get(wordsDisplayed[currWordIndex]) 
						+ "\nscore increased to: " + score);
				updateWordsDisplayed();
				currLetterIndex = -1;
				currWordIndex = -1;
			} else {
				currLetterIndex += 1;
				setChanged();
				notifyObservers(States.update.HIGHLIGHT);
			}
			return;
		}
		// wrong letter typed
		Log.i("SinglePlayer", "typed the wrong letter: " + letter);
		setChanged();
		notifyObservers(States.update.WRONG_LETTER);
	}

	/**
	 * @return current score of the player
	 */
	public final int getScore() {
		return score;
	}
}
