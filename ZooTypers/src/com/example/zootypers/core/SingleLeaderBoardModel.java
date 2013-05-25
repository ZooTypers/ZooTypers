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


/**
 * The single leaderboard model class that keep track of entries on the leaderboard
 * with score, and is ranked according to the highest score.
 * 
 * @author nhlien93
 *
 */
public class SingleLeaderBoardModel {
	private static final int DEFAULT_ENTRIES = 10;
	private static final String FILE_NAME = "single_player_leaderboard.txt";

	//Number of Entries that we allow in the database
	private int topEntries;
	// allows files in assets to be accessed
	private Context context;

	//private Map<Integer, Integer> score;
	private List<Integer> scores;

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
		scores = new ArrayList<Integer>();
		parseFile();
	}

	/*
	 * Parses the file and keeps a list of the current entries in the leaderboard
	 */
	private void parseFile() {
		String[] tempArr = null;
		try {
			InputStream stream =  context.openFileInput(FILE_NAME);
			String contents = IOUtils.toString(stream, "UTF-8");
			tempArr = contents.split(" ");
		} catch (FileNotFoundException e) {
			tempArr = new String[0];
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < tempArr.length; i++) {
			// to make the rankings start from 1 not 0
			scores.add(Integer.parseInt(tempArr[i]));
		}
	}

	/**
	 * Add a new entry(score) to the database
	 * @param score, user's score to potentially be added
	 */
	public void addEntry(int newScore){
		//only adds the entry if the score is within the range of the current top scores
		int size = scores.size();
		if (size == 0 || size < topEntries) {
			scores.add(newScore);
		} else if (scores.get(size - 1) < newScore) {
			scores.set(size - 1, newScore);
		}
		Collections.sort(scores, new Comparator<Integer>() {

			@Override
			public int compare(Integer intOne, Integer intTwo) {
				return intTwo - intOne;
			}
			
		});
		save();
	}
	
	/*
	 * After updating the list, save the list back into the file by writing
	 */
	private void save() {
		StringBuffer write = new StringBuffer();
		for (int j = 0; j < scores.size() - 1; j++) {
			write.append(scores.get(j) + " ");
		}
		write.append(scores.get(scores.size() - 1));
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Return an array of Entry that is in the order of top to low scores
	 * Entry has field ( string and int ) that stores name and scores
	 * Also in timestamp order(Early achieve has higher rank)
	 * @return An unmodifiable list of the scores in the correct rank order
	 */
	public List<Integer> getTopScores(){	
		return Collections.unmodifiableList(scores);
	}

	/**
	 * Clear every thing in the database
	 */
	public void clearLeaderboard(){
		context.deleteFile(FILE_NAME);
		scores.clear();
	}
}
