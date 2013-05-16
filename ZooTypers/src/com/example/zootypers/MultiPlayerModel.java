package com.example.zootypers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

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

	private static final long QUEUE_TIMEOUT = 50000; // timer set for 50 sec to wait before giving up in queue
	private static final int LIST_SIZE = 100;
	private static final int SCORE_TIMEOUT = 10000; // timer set to 10 sec to wait for getting opponents score
	
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
	private String animalName;
	
	// maximum number of words in wordLists on Parse database
	private static final int NUMOFWORDS = 709;

	/**
	 * Constructs a new SinglePlayerModel that takes in the ID of an animal and background,
	 * and also what the difficulty level is. The constructor will initialize the words list
	 * and fills in what words the view should display on the screen.
	 * 
	 * @param animalID, the string ID of a animal that is selected by the user
	 * @param backgroudID, the string ID of a background that is selected by the user
	 * @param diff, the difficulty level that is selected by the user
	 */
	public MultiPlayerModel(int wordsDis, String uname, String animalName) {
		this.animalName = animalName;
		this.name = uname;
		beginMatchMaking();
		wordsList = getWordsList();
		this.numWordsDisplayed = wordsDis;
		currFirstLetters = new HashSet<Character>();
		//initialize all the fields to default starting values
		wordsDisplayed = new int[numWordsDisplayed];
		score = 0;
		nextWordIndex = 0;
		currLetterIndex = -1;
		currWordIndex = -1;
	}
	
    private void beginMatchMaking() {
        while (!addToQueue()) {
        	// loop until added, will need timeout
        }
        
        if (findOpponent()) {
        	// found an opponent, begin game
        	//getOpponentData();
        	
        } else {
        	// keep checking my status till I am matched
        	while (!checkStatus()) {
        		// loop until I am matched, till need timeout
        	}
        }
    }
    
	public boolean addToQueue() {
		
		player = new ParseObject("Players");
		player.put("name", name);
		player.put("opponent", "");
		player.put("animal", animalName);
		player.put("score", 0);
		player.put("startingIndex", -1);
		player.put("finished", false);
		try {
			player.save();
		} catch (ParseException e) {
			// did not save properly
			return false;
		}
		return true;
	}
	
	private boolean findOpponent() {
		try {
			ParseQuery query = new ParseQuery("Players");
			query.whereEqualTo("opponent", "");
			query.whereNotEqualTo("name", name);
			opponent = query.getFirst();
			if (opponent != null) {
				// changing opponent
				final int randy = (int) (Math.random() * (NUMOFWORDS));
				opponent.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						opponent.put("opponent", name);
						opponent.put("startingIndex", randy);
						opponent.saveInBackground();			
					}
				});
				
				// changing myself 
				player.saveInBackground(new SaveCallback() {
					@Override
					public void done(ParseException e) {
						player.put("opponent", opponent.getString("name"));
						player.put("startingIndex", randy);
						player.saveInBackground();			
					}
				});
				
				return true;
			} 
		} catch (ParseException e1) {
			// TODO say connection failed if can't get opponent info
//			e1.printStackTrace();
		}
		return false;
	}
	
    private boolean checkStatus() {
    	List<ParseObject> wordObjects = null;
    	long starttime = System.currentTimeMillis();
    	long endtime = starttime + QUEUE_TIMEOUT;
    	while(System.currentTimeMillis() < endtime) {
    		try {
    			ParseQuery query = new ParseQuery("Players");
    			query.whereEqualTo("name", name);
    			query.whereNotEqualTo("opponent", "");
    			wordObjects= query.find();
    			if (wordObjects.size() != 0) {
    				ParseQuery queryOpponent = new ParseQuery("Players");
    				queryOpponent.whereEqualTo("name", player.getString("opponent"));
    				opponent = queryOpponent.getFirst();
    				return true;
    			}
    			Thread.sleep(5000);
    		} catch (ParseException e1) {
    			return false;
    		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return false;
    }
    
	/**
	 * The populateDisplayedList method gets called once by MultiPlayer after
	 * it added itself as an observer of this class. The method populates the
	 * displayed word list with numWordsDisplayed amount of words. 
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

  public String getOpponentAnimal() {
    // TODO Auto-generated method stub
    return "giraffe";
  }

	// populates wordsList by contacting the database for LIST_SIZE amount of words
	private List<String> getWordsList() {
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
	


	// returns false if there is a duplicate, true otherwise
//	private boolean noDuplicates() {
//		try {
//			ParseQuery query = new ParseQuery("Players");
//			query.whereEqualTo("name", name);
//			@SuppressWarnings("unused")
//			ParseObject retObj = query.getFirst();
//			return false;
//		} catch (ParseException e1) {
//			return true;
//		}
//
//	}

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
	
	private void updateScoreOnline() {
		player.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				player.put("score", score);
				player.saveInBackground();			
			}
		});
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

	public final void setUserFinish() {
		player.put("finished", true);
	}
	
	// return true if my opponent has finished their game
	public final boolean isOpponentFinished() {
    	long starttime = System.currentTimeMillis();
    	long endtime = starttime + SCORE_TIMEOUT;
    	while(System.currentTimeMillis() < endtime) {
    		
    		if (opponent.getBoolean("finished")) {
    			return true;
    		}
    		try {
				opponent.refresh();
				Thread.sleep(5000);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return false;
	}
	
	public void deleteUser() {
		try {
			player.delete();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
