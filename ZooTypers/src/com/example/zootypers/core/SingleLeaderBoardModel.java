package com.example.zootypers.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.content.Context;
import android.util.Log;


/**
 * The single leaderboard model class that keep track of entries on the leaderboard
 * with score, and is ranked according to the highest score.
 * 
 * @author nhlien93
 *
 */
public class SingleLeaderBoardModel {
	// default number of entries on the leaderboard
	private static final int DEFAULT_ENTRIES = 10;
	// name if the file that stores all the scores
	private static final String FILE_NAME = "single_player_leaderboard.txt";
	// the delimiter that separates the score entries in the file
	private static final String DELIM = "\t";
	// Number of Entries that we allow in the database
	private int topEntries;
	// allows files in assets to be accessed
	private Context context;

	// The list of all the score entries from the file
	private List<ScoreEntry> scoreEntries;

	/**
	 * @effect : initialize field to default values
	 * @param am, the asset manager of the SinglePlayer that gives the model to 
	 * access to the words stored in the asset folder
	 */
	public SingleLeaderBoardModel(Context context){
		this(context, DEFAULT_ENTRIES);
	}

	/**
	 * @effect : initialize fields to the params and default values
	 * @requires topEntries is a positive number
	 * @param topEntries the max number of entries that are saved in the database
	 * @param am, the asset manager of the SinglePlayer that gives the model to 
	 * access to the words stored in the asset folder
	 */
	public SingleLeaderBoardModel(Context context, int topEntries){
		this.context = context;
		this.topEntries = topEntries;
		scoreEntries = new ArrayList<ScoreEntry>();
		parseFile();
	}

	/*
	 * Parses the file and keeps a list of the current entries in the leaderboard
	 */
	private void parseFile() {
		Log.i("SinglePlayer", "reading file for scores");
		String[] tempArr = null;
		try {
			InputStream stream =  context.openFileInput(FILE_NAME);
			String contents = IOUtils.toString(stream, "UTF-8");
			//splitting up each score entry
			tempArr = contents.split("\n");
		} catch (FileNotFoundException e) {
			e.fillInStackTrace();
			tempArr = new String[0];
			Log.i("SinglePlayer", "no scores in system");
		} catch (IOException e) {
			Log.e("SinglePlayer", "error locating file for scores", e);
		}

		for (int i = 0; i < tempArr.length; i++) {
			// splitting the entry into two strings that represent the name and score
			String[] tempSE = tempArr[i].toString().split(DELIM);
			// making the actual score entries;
			scoreEntries.add(new ScoreEntry (tempSE[0], Integer.parseInt(tempSE[1])));
		}
	}

	/**
	 * Add a new entry(score) to the database
	 * @param score, user's score to potentially be added
	 */
	public void addEntry(String name, int newScore){
		Log.i("SinglePlayer", "adding the user's entry");
		//only adds the entry if the score is within the range of the current top scores
		int size = scoreEntries.size();
		if ((size == 0) || (size < topEntries)) {
			scoreEntries.add(new ScoreEntry(name, newScore));
		} else if (scoreEntries.get(size - 1).getScore() < newScore) {
			scoreEntries.set(size - 1, new ScoreEntry(name, newScore));
		}
		Collections.sort(scoreEntries, new Comparator<ScoreEntry>() {

			@Override
			public int compare(ScoreEntry one, ScoreEntry two) {
				return two.getScore() - one.getScore();
			}
			
		});
		save();
	}
	
	/*
	 * After updating the list, save the list back into the file by writing
	 */
	private void save() {
		Log.i("SinglePlayer", "saving scores");
		StringBuffer write = new StringBuffer();
		for (int j = 0; j < (scoreEntries.size() - 1); j++) {
			ScoreEntry currentSE = scoreEntries.get(j);
			write.append(currentSE.getName() + DELIM + currentSE.getScore() + "\n");
		}
		ScoreEntry lastSE = scoreEntries.get(scoreEntries.size() - 1);
		write.append(lastSE.getName() + DELIM + lastSE.getScore());
		
		try {
			FileOutputStream fOut = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut); 
			// Write the string to the file
			osw.write(write.toString());
			/* ensure that everything is
			 * really written out and close */
			osw.flush();
			osw.close();
		} catch (IOException e) {
			Log.e("SinglePlayer", "error writing to file for single player scores", e);
		}
	}

	/**
	 * Return an array of Entry that is in the order of top to low scores
	 * Entry has field ( string and int ) that stores name and scores
	 * Also in timestamp order(Early achieve has higher rank)
	 * @return An unmodifiable list of the scores in the correct rank order
	 */
	public ScoreEntry[] getTopScores(){
		//ScoreEntry[] array = scoreEntries.toArray(new ScoreEntry[scoreEntries.size()]);		
		return scoreEntries.toArray(new ScoreEntry[scoreEntries.size()]);
	}

	/**
	 * Clear every thing in the database
	 */
	public void clearLeaderboard(){
		Log.i("SinglePlayer", "removing all scores");
		context.deleteFile(FILE_NAME);
		scoreEntries.clear();
	}
}
