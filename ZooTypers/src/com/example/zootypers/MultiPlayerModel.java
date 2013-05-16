package com.example.zootypers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import android.content.res.AssetManager;

import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.RefreshCallback;
import com.parse.SaveCallback;

/** 
 * 
 * The Model class for Single Player store a list of words for the UI to display.
 * It keeps track of word and letter the user has typed and updates the view accordingly.
 * 
 * @author winglam, nhlien93, dyxliang
 * 
 */

public class MultiPlayerModel extends Observable {

	private static final long TIMEOUT = 500000; // timer set for 50 sec TODO change this back. dont forget.
	private static final int LIST_SIZE = 100;
	// number of words displayed on the view
	private final int numWordsDisplayed;

	private String name;

	private ParseObject player;
	
	private ParseObject opponent;
	
	// stores an array of words 
	private List<String> wordsList;

	// array of indices that refers to strings inside wordsList
	private int[] wordsDisplayed;

	private Set<Character> currFirstLetters;
	
	// index of the next word to pull from wordsList, (should ONLY be used with wordsList)
	private int nextWordIndex;

	// index of a string inside wordsDisplayed (should NEVER be used on wordsList!)
	private int currWordIndex;

	// index of letter that has been parsed from the currWordIndex
	private int currLetterIndex;


	// keep track of the user's current score
	private int score;

	/**
	 * Constructs a new SinglePlayerModel that takes in the ID of an animal and background,
	 * and also what the difficulty level is. The constructor will initialize the words list
	 * and fills in what words the view should display on the screen.
	 * 
	 * @param animalID, the string ID of a animal that is selected by the user
	 * @param backgroudID, the string ID of a background that is selected by the user
	 * @param diff, the difficulty level that is selected by the user
	 */
	public MultiPlayerModel(final States.difficulty diff, AssetManager am, int wordsDis, String name) {
		this.name = name;
		beginMatchMaking();
		wordsList = getNextWords();
		this.numWordsDisplayed = wordsDis;
		currFirstLetters = new HashSet<Character>();
		//initialize all the fields to default starting values
		wordsDisplayed = new int[numWordsDisplayed];
		score = 0;
		nextWordIndex = 0;
		currLetterIndex = -1;
		currWordIndex = -1;
	}

	/**
	 * The populateDisplayedList method gets called once by SinglePlayer after
	 * it added itself as an observer of this class.
	 */
	public void populateDisplayedList() {
		// putting first five words into wordsDisplayed
		currFirstLetters = new HashSet<Character>();
		for (int i = 0; i < numWordsDisplayed; i++) {
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

	private List<String> getNextWords() {
		List<ParseObject> wordObjects = null;
		try {
			ParseQuery query = new ParseQuery("WordList");
			query.setSkip(player.getInt("startingIndex"));
			query.setLimit(LIST_SIZE); // limit to at most 20 results
			 wordObjects= query.find();
			if (wordObjects.size() < LIST_SIZE) {
				ParseQuery query2 = new ParseQuery("WordList");
				query2.setLimit(LIST_SIZE - wordObjects.size());
				wordObjects.addAll(query2.find());
			}
		} catch (ParseException e1) {
			// TODO connection error
		}

		List<String> words = new ArrayList<String>();
		for (ParseObject o : wordObjects) {
			words.add(o.getString("word"));
		}
		
		return words;
	}
	
	private void updateScoreOnline() {
		player.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				player.put("score", score);
				player.saveInBackground();			
			}
		});
	}
	
    private void beginMatchMaking() {
        while (!addToQueue()) {
        	// loop until added, will need timeout
        }
        
        if (findOpponent()) {
        	// found an opponent, begin game
        	getOpponentData();
        	
        } else {
        	// keep checking my status till I am matched
        	while (!checkStatus()) {
        		// loop until I am matched, till need timeout
        	}
        	
        }
    }
	
	
	
	
	// checks whether name can be matched to an opponent
	// returns true if matched, false otherwise
	private boolean findOpponent() {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", name);
		try {
			String retMsg = ParseCloud.callFunction("checkOthers", params);
			player.refresh();
			return retMsg.equals("found opponent");
		} catch (ParseException e) {
			return false;
		}
	}
	
    private boolean checkStatus() {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("user", name);
		try {
			long starttime = System.currentTimeMillis();
			long endtime = starttime + TIMEOUT;
			while(System.currentTimeMillis() < endtime) {
				String retMsg = ParseCloud.callFunction("checkMeOut", params);
				if (retMsg.equals("found opponent")) {
					player.refresh();
					getOpponentData();
					return true;
				}
				wait(5000);
			}
		} catch (Exception e1) {
			// exception from waiting, should try again
			return false;
		}
		return false;
    }

	public boolean addToQueue() {
		if (noDuplicates()) { 
			try {
				player = new ParseObject("Players");
				player.put("name", name);
				player.put("opponent", "");
				player.put("score", 0);
				player.put("startingIndex", -1);
				player.saveInBackground();
				player.refresh();
			} catch (ParseException e) {
				// TODO: write that connection failed
			}
			return true;
		}
		return false; //TODO figure out what we want with this.
	}	

	private boolean noDuplicates() {
		int size = -1;
		try {
			ParseQuery query = new ParseQuery("Players");
			query.whereEqualTo("name", name);
			size = query.find().size();
		} catch (ParseException e1) {
			return false;
		}
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void getOpponentData() {
		try {
			ParseQuery query = new ParseQuery("Players");
			query.whereEqualTo("name", player.getString("opponent"));
			opponent = query.getFirst();
		} catch (ParseException e1) {
			// TODO say connection failed if can't get opponent info
			e1.printStackTrace();
		}
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
					notifyObservers(States.update.HIGHLIGHT);
					return;
				}
			}
			// locked on to a word being typed (letter == the index of current letter index in the word)
		} else if (wordsList.get(wordsDisplayed[currWordIndex]).charAt(currLetterIndex) == letter) {

			// store length of current word
			int wordLen = wordsList.get(wordsDisplayed[currWordIndex]).trim().length();

			// word is completed after final letter is typed
			if ((currLetterIndex + 1) >= wordLen) {
				score += wordLen;
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
		setChanged();
		notifyObservers(States.update.WRONG_LETTER);
	}

	/*
	 *  Replace the current word on display with a new word from list making
	 *  sure that the new word will not start with the same letter as any of
	 *  the other words being displayed.
	 *  post: nextWordIndex will always be set to a valid index of wordsList
	 */
	private void updateWordsDisplayed() {
		currFirstLetters.remove(wordsList.get(wordsDisplayed[currWordIndex]).charAt(0));
		while (currFirstLetters.contains(wordsList.get(nextWordIndex).charAt(0))) {
			nextWordIndex++;
			if (nextWordIndex >= wordsList.size()) {
				nextWordIndex = 0;
			}
		}
		currFirstLetters.add(wordsList.get(nextWordIndex).charAt(0));
		wordsDisplayed[currWordIndex] = nextWordIndex;
		nextWordIndex++;
		if (nextWordIndex >= wordsList.size()) {
			nextWordIndex = 0;
		}
		
		opponent.refreshInBackground(new RefreshCallback() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					setChanged();
					notifyObservers(States.update.OPPONENT_SCORE);
				}
			}
		});
		
		setChanged();
		notifyObservers(States.update.FINISHED_WORD);
		updateScoreOnline();
	}

	/**
	 * @return current score of the player
	 */
	public final int getScore() {
		return score;
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
	
	public final int getOpponentScore() {
		return opponent.getInt("score");
	}
}
