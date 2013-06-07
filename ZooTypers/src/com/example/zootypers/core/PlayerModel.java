package com.example.zootypers.core;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import com.example.zootypers.util.States;

/** 
 * 
 * The abstract class for a Player that stores a list of words for the UI to display.
 * It keeps track of word and letter the user has typed and updates the view accordingly.
 * 
 * @author winglam, nhlien93, dyxliang
 * 
 */
public abstract class PlayerModel extends Observable {

	// number of words displayed on the view
	protected final int numWordsDisplayed;

	// stores an array of words 
	protected List<String> wordsList;

	// array of indices that refers to strings inside wordsList
	protected int[] wordsDisplayed;

	// index of a string inside wordsDisplayed (should NEVER be used on wordsList!)
	protected int currWordIndex;

	// index of letter that has been parsed from the currWordIndex
	protected int currLetterIndex;

	// index of the next word to pull from wordsList, (should ONLY be used with wordsList)
	protected int nextWordIndex;

	// set of first character of each word currently being displayed
	protected Set<Character> currFirstLetters;


	/**
	 * Constructs a new model that takes in the number of words being displayed.
	 * 
	 * @param wordsDis, the number of words being displayed on the screen
	 */
	public PlayerModel(int wordsDis) {
		numWordsDisplayed = wordsDis;
		currFirstLetters = new HashSet<Character>();
		//initialize all the fields to default starting values
		wordsDisplayed = new int[numWordsDisplayed];
		nextWordIndex = 0;
		currLetterIndex = -1;
		currWordIndex = -1;
	}

	/**
	 * The populateDisplayedList method gets called once by MultiPlayer after
	 * it added itself as an observer of this class. The method populates the
	 * displayed word list with numWordsDisplayed amount of words. 
	 */
	public void populateDisplayedList() {
		// adds a total of numWordsDisplayed words to the original display list
		for (int i = 0; i < numWordsDisplayed; i++) {
			// checks to see if any of the words start with the same letter as the 
			// current word that might be added, if it does than increment to the next
			// potential word
			while (currFirstLetters.contains(wordsList.get(nextWordIndex).charAt(0))) {
				nextWordIndex++;
			}
			currFirstLetters.add(wordsList.get(nextWordIndex).charAt(0));
			wordsDisplayed[i] = nextWordIndex;
			currWordIndex = i;
			setChanged();
			notifyObservers(States.update.FINISHED_WORD);
		}
		nextWordIndex++;
		currWordIndex = -1;
	}

	/*
	 *  Replace the current word on display with a new word from list making
	 *  sure that the new word will not start with the same letter as any of
	 *  the other words being displayed.
	 *  post: nextWordIndex will always be set to a valid index of wordsList
	 */
	protected void updateWordsDisplayed() {
		currFirstLetters.remove(wordsList.get(wordsDisplayed[currWordIndex]).charAt(0));
		// checks if the remaining words being displayed has the same first letter as the
		// first letter of the potential next word, increment if it does and check again.
		while (currFirstLetters.contains(wordsList.get(nextWordIndex).charAt(0))) {
			nextWordIndex++;
			if (nextWordIndex >= wordsList.size()) {
				nextWordIndex = 0;
			}
		}
		// by this point the next word should not have the same first letter
		currFirstLetters.add(wordsList.get(nextWordIndex).charAt(0));
		wordsDisplayed[currWordIndex] = nextWordIndex;
		nextWordIndex++;
		if (nextWordIndex >= wordsList.size()) {
			nextWordIndex = 0;
		}

		setChanged();
		notifyObservers(States.update.FINISHED_WORD);
	}

	/**
	 * @return the string representation of the current word the player is locked to,
	 * null if player is not locked to a word
	 */
	public final String getCurrWord() {
		if (currWordIndex == -1) {
			return null;
		}
		return wordsList.get(wordsDisplayed[currWordIndex]);
	}

	/**
	 * The typedLetter method handles what words and letter the user has
	 * typed so far and notify the view to highlight typed letter or fetch 
	 * a new word from the wordsList for the view to display accordingly.
	 * 
	 * @param letter, the letter that the user typed on the Android soft-keyboard
	 */
	public abstract void typedLetter(final char letter);

	/**
	 * @return current score of the user
	 */
	public abstract int getScore();

	/**
	 * @return the index of the word the player is currently locked to within the words displayed
	 */
	public final int getCurrWordIndex() {
		return currWordIndex;
	}

	/**
	 * @return the index of the letter the player is expected to type in the locked word
	 */
	public final int getCurrLetterIndex() {
		return currLetterIndex;
	}

	/**
	 * @return the array of all the index of the words currently being displayed.
	 */
	public final int[] getWordsDisplayed() {
		return wordsDisplayed;
	}
}
