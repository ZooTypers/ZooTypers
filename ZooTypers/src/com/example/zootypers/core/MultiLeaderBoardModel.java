package com.example.zootypers.core;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.zootypers.util.InternetConnectionException;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


/**
 * The multiplayer leaderboard model class that keep track of every player's high score 
 * with name and score, and ranked by to the highest score.
 * 
 * @author nhlien93
 *
 */
public class MultiLeaderBoardModel {
	private static final int DEFAULT_ENTRIES = 10;

	//Number of Entries that we allow in the database
	private int numOfEntries;

	//The user's entry in the database
	private ParseObject entry;
	
	//All the scores in the database
	private List<ParseObject> allScores;

	/**
	 * Constructs the leaderboard with the default number of entries.
	 * @throws InternetConnectionException 
	 */
	public MultiLeaderBoardModel() throws InternetConnectionException{
		this(DEFAULT_ENTRIES);
	}

	/**
	 * @effect : Constructs the leaderboard with the default number of entries.
	 * @requires numOfEntries is a positive number
	 * @param numOfEntries, the max number of entries that are saved in the database 
	 * @throws InternetConnectionException 
	 */
	public MultiLeaderBoardModel(int numOfEntries) throws InternetConnectionException{
		this.numOfEntries = numOfEntries;
		getAllScores();
		//gets the current leaderboard from database and stores it into a list
	}
	
	/*
	 * Gets all the scores from the online database in Descending score order. If
	 * scores are equal than rank alphabetically.
	 */
	private void getAllScores() throws InternetConnectionException {
		Log.i("Multiplayer", "multiplayer getting all scores from parse");
		try {
			ParseQuery query = new ParseQuery("MultiLeaderBoard");
			query = query.whereNotEqualTo("name", "DONOTDELETE");
			query = query.orderByDescending("score");
			query = query.addAscendingOrder("name");
			query.setLimit(query.count());
			allScores = query.find();
		} catch (ParseException e) {
			Log.e("Multiplayer", "error getting all scores from parse for multiplayer", e);
			throw new InternetConnectionException();
		} catch (NullPointerException e) {
			Log.e("Multiplayer", "unable to connect to internet", e);
			throw new InternetConnectionException();
		}
	}

	/*
	 * Gets the player's particular parse object so that changes to the score
	 * can be made if a score is added.
	 */
	public void setPlayer(String name) throws InternetConnectionException {
		Log.i("Multiplayer", "multiplayer getting this player's score from parse");
		try {
			ParseQuery query = new ParseQuery("MultiLeaderBoard");
			query.whereEqualTo("name", name);
			query.count();
			entry = query.getFirst();
		} catch (ParseException e) {
			Log.i("Multiplayer", "this player's has no scores score from parse");
			// making a new entry for this player
			entry = new ParseObject("MultiLeaderBoard");
			entry.put("name", name);
			entry.put("score", 0);
		} catch (NullPointerException e) {
			Log.e("Multiplayer", "unable to connect to internet", e);
			throw new InternetConnectionException();
		}
	}

	/**
	 * Add a new entry(score) to the database
	 * @param score, user's score to potentially be added
	 */
	public void addEntry(int score){
		Log.i("Multiplayer", "multiplayer leaderboard adding the user's entry");
		int highScore = Math.max(score, entry.getInt("score"));
		entry.put("score", highScore);
		entry.saveInBackground();
	}

	/**
	 * Return a List of score entries that is in the order of top to low scores
	 * Entry has field ( string and int ) that stores name and scores
	 * The list will return a total of numOfEntries entries.
	 * @return a list of size numOfEntries with the top score entries
	 */
	public ScoreEntry[] getTopScores(){
		int size = Math.min(numOfEntries, allScores.size());
		ScoreEntry[] scores = new ScoreEntry[size];
		for (int i = 0; i < size; i++) {
			scores[i] = new ScoreEntry(allScores.get(i).getString("name"), allScores.get(i).getInt("score"));
		}
		return scores;
	}

	/**
	 * Checks to see if the user is a part of the top entries.
	 * @return true of user is ranked in the top entries and false otherwise.
	 */
	public boolean isInTopEntries() {
		return getRank() < numOfEntries;
	}
	
	/**
	 * Return a List of score entries that is in the order of high to low scores
	 * around your own score. The size of the list will be determined by the parameter
	 * passed in. There will be numOfRelative scores above and below the user's score
	 * in the list unless range goes out of bounds then scores only go to that
	 * @param numOfRelatives the number of scores above and below user's score to return in list
	 * @return a list of size numOfEntries with the top score entries
	 */
	public ScoreEntry[] getRelativeScores(int numOfRelatives) {
		if (numOfRelatives < 0) {
			numOfRelatives *= -1;
		}
		List<ScoreEntry> scoreEntries = new ArrayList<ScoreEntry>();
		int rank = getRank();
		if (rank != 0) {
			int startIndex = rank - numOfRelatives - 1;
			if (startIndex < 0) {
				startIndex = 0;
			}
			int endIndex = rank + numOfRelatives;
			if (endIndex < DEFAULT_ENTRIES) {
				endIndex = DEFAULT_ENTRIES;
			}
			if (endIndex > allScores.size()) {
				endIndex = allScores.size();
			}
			
			for (int i = startIndex; i < endIndex; i++) {
				scoreEntries.add(new ScoreEntry(allScores.get(i).getString("name"), allScores.get(i).getInt("score")));
			}
			
		}
		return scoreEntries.toArray(new ScoreEntry[scoreEntries.size()]);
	}

	/**
	 * Clears the users scores from the database
	 */
	public void clearLeaderboard(){
		Log.i("Multiplayer", "multiplayer leaderboard deleting the user's entry");
		entry.deleteInBackground();
	}

	/**
	 * @ return rank with 1 being highest. If rank = 0 then user has no multi player score on database
	 */
	public int getRank(){
		return Collections.binarySearch(allScores, entry, new Comparator<ParseObject>() {

			@Override
			public int compare(ParseObject arg0, ParseObject arg1) {
				int diff = arg1.getInt("score") - arg0.getInt("score");
				if (diff == 0) {
					return arg0.getString("name").compareTo(arg1.getString("name"));
				}
				return arg1.getInt("score") - arg0.getInt("score");
			}
		}) + 1;	
	}
}
