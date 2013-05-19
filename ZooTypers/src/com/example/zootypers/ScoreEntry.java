package com.example.zootypers;

/**
 * A data class that stores one instance of a name and score of a user
 * in the leaderboard.
 * 
 * @author kobryan & dyxliang
 */
public class ScoreEntry {
	private String playerName;
	private int playerScore;
	
	/**
	 * Constructs with given name and score.
	 * 
	 * @param playerName The player's name.
	 * @param playerScore The player's score.
	 */
	public ScoreEntry(String playerName, int playerScore){
		this.playerName = playerName;
		this.playerScore = playerScore;
	}
	
	/**
	 * @return The player's name.
	 */
	public String getName(){
		return playerName;
	}
	/**
	 * @return The player's score.
	 */
	public int getScore(){
		return playerScore;
	}
}