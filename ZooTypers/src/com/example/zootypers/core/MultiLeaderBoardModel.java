package com.example.zootypers.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
		Log.i("Multiplayer", "getting all scores from parse");
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
		Log.i("Multiplayer", "getting this player's score from parse");
		try {
			ParseQuery query = new ParseQuery("MultiLeaderBoard");
			query.whereEqualTo("name", name);
			query.count();
			entry = query.getFirst();
		} catch (ParseException e) {
			e.fillInStackTrace();
			Log.i("Multiplayer", "this player has no score from parse");
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
		Log.i("Multiplayer", "adding the user's entry to leaderboard");
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
			scores[i] = new ScoreEntry(allScores.get(i).getString("name"), 
			allScores.get(i).getInt("score"));
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
	 * around your own score. The size of the list will be defalut_entries with the
	 * goal being that the player's score is the center rank. Only time there is less
	 * than default_entries is when there are less than default entries in the database
	 * 
	 * @return a list of size numOfEntries with the top score entries
	 */
	public ScoreEntry[] getRelativeScores() {
		List<ScoreEntry> scoreEntries = new ArrayList<ScoreEntry>();
		int highestRank = getHighestRelScoreRank();
		if (highestRank != 0) {
			int startIndex = highestRank - 1;
			for (int i = startIndex; i < allScores.size() && i < startIndex + DEFAULT_ENTRIES; i++) {
				scoreEntries.add(new ScoreEntry(allScores.get(i).getString("name"), allScores.get(i).getInt("score")));
			}
		}
		return scoreEntries.toArray(new ScoreEntry[scoreEntries.size()]);
	}

	/**
	 * @return highest possible relative rank with 1 being highest. 
	 * If rank = 0 then user has no multiplayer score on database
	 */
	public int getHighestRelScoreRank() {
		int rank = getRank();
		int relative = numOfEntries/2;
		int highestRank = 0;
		if (rank != 0) {
			if (allScores.size() - rank < relative) {
				highestRank = allScores.size() - numOfEntries + 1;
			} else {
				highestRank = rank - relative + 1;
			}
			if (highestRank < 1) {
				highestRank = 1;
			}
		}
		return highestRank;
	}
	/**
	 * Clears the users scores from the database
	 */
	public void clearLeaderboard(){
		Log.i("Multiplayer", "deleting the user's entry from leaderboard");
		entry.deleteInBackground();
	}

	/**
	 * @return rank with 1 being highest. If rank = 0 
	 * then user has no multi player score on database
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
