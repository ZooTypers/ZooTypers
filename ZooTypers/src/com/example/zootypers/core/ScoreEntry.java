package com.example.zootypers.core;

/**
 * A data class that help to store the name and score of a user in the leaderboard.
 * 
 * @author kobryan & dyxliang
 */
public class ScoreEntry {
	private String playerName;
	private int playerScore;
	
	/**
	 * Constructs player's given name and score received.
	 * 
	 * @param playerName
	 * @param playerScore
	 */
	public ScoreEntry(String playerName, int playerScore){
		this.playerName = playerName;
		this.playerScore = playerScore;
	}
	
	/**
	 * @return playerName
	 */
	public String getName(){
		return playerName;
	}
	
	/**
	 * @return playerScore
	 */
	public int getScore(){
		return playerScore;
	}
}