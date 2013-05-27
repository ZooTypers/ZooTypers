package com.example.zootypers.core;


/**
 * A leaderboard model class that keep track of entries on the leaderboard
 * with name and score, and ranked according to the highest score.
 * 
 * @author nhlien93
 *
 */
public class LeaderBoardModel {
	private static final int DEFAULT_ENTRIES = 10;
	
	//Number of Entries that is already in the database
	private int entryNumber;
	//Number of Entries that we allow in the database
	private int topEntries;
	
	/**
	 * @effect : initialize field to default values
	 */
	public LeaderBoardModel(){
		topEntries = DEFAULT_ENTRIES;
		entryNumber = 0;
	}
	
	/**
	 * @effect : initialize fields to the params and default values
	 * @requires topEntries is a positive number
	 * @param topEntries the max number of entries that are saved in the database 
	 */
	public LeaderBoardModel(int topEntries){
		this.topEntries = topEntries;
		entryNumber = 0;
		//gets the current leaderboard from database and stores it into a list
	}
	
	/**
	 * Add a new entry(name, score) to the data base 
	 * @return boolean, true when finish
	 * @param name, player name
	 * @param score, palyer score
	 */
	public boolean addEntry(String name, int score){
		//only adds the entry if the score is within the range of the current top scores
		entryNumber++;
		return true;
		//TODO: finish up the addEntry method
	}
	
	/**
	 * Return an array of Entry that is in the order of top to low scores
	 * Entry has field ( string and int ) that stores name and scores
	 * Also in timestamp order(Early achieve has higher rank)
	 * @return Entry[]
	 */
	public ScoreEntry[] getTopScores(){
		return null;
		//TODO: finish up the getting the top scores
	}
	
	/**
	 * Delete the lowest score in the Database when entryNumber reached 50+
	 * @return true, if delete successfully
	 */
	public boolean deleteLowestScore(){
		entryNumber--;
		return true;
		//TODO: finish up deleting the lowest score when you have more than 50 entries
		//this will probably be a private method
	}
	
	/**
	 * Clear every thing in the database
	 */
	public void clearLeaderboard(){
		entryNumber = 0;
		//this clears the whole file into nothing
	}
	
	/**
	 * @ return top;
	 */
	public int getTopEntries(){
		return topEntries;
	}
	/**
	 * @return entry
	 */
	public int getEntryNumber(){
		return entryNumber;
	}
}
