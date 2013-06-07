package com.example.zootypers.core;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * A data class that help to store the name and score of a user in the leaderboard.
 * 
 * @author kobryan & dyxliang
 */
public class ScoreEntry implements Parcelable {
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

	public ScoreEntry(Parcel in) {
		readFromParcel(in);
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(playerName);
		dest.writeInt(playerScore);
	}

	private void readFromParcel(Parcel in) {
		playerName = in.readString();
		playerScore = in.readInt();
	}

	@Override
	public String toString() {
		return playerName + " " + playerScore;
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR =
	new Parcelable.Creator() {
		public ScoreEntry createFromParcel(Parcel in) {
			return new ScoreEntry(in);
		}

		public ScoreEntry[] newArray(int size) {
			return new ScoreEntry[size];
		}
	};
}