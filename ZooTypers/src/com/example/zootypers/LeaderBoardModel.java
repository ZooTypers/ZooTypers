package com.example.zootypers;


/**
 * A leaderboard model class that keep track of entries on the leaderboard
 * with name and score, and ranked according to the highest score.
 * 
 * @author kobryan & dyxliang
 *
 */
public class LeaderBoardModel {
	
	//Number of Entries that is already in the database
	private int entryNumber;
	//Number of Entries that we are allowed in the database
	private int topEntries;
	
	/**
	 * @effect : initialize field to default values
	 */
	public LeaderBoardModel(){
		entryNumber = 0;
		topEntries = 50;
	}
	
	/**
	 * @effect : initialize fields to the params and default values
	 * @param top 
	 */
	public LeaderBoardModel(int topEntries){
		this.topEntries = topEntries;
		entryNumber = 0;
	}
	
	/**
	 * Add a new entry(name, score) to the data base 
	 * @return boolean, true when finish
	 * @param name, player name
	 * @param score, palyer score
	 */
	public boolean addEntry(String name, int score){
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
	}
	
	/**
	 * Clear every thing in the database
	 */
	public void clearLeaderboard(){
		entryNumber = 0;
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
