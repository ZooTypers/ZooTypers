package com.example.zootypers.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.io.IOUtils;

import android.content.res.AssetManager;

import com.parse.ParseObject;

public class DatabaseUtils {

	// used once to initialize database of words
	public static void setupWordsListLatin(AssetManager am, boolean useLatin) {
		String[] fourWords; 
		String[] fiveWords;
		String[] sixWords;
		if (useLatin) {
			fourWords = getWordsListLatin(States.difficulty.EASY, am);
			fiveWords = getWordsListLatin(States.difficulty.MEDIUM, am);
			sixWords = getWordsListLatin(States.difficulty.HARD, am);
		} else {
			fourWords = getWordsList(States.difficulty.EASY, am);
			fiveWords = getWordsList(States.difficulty.MEDIUM, am);
			sixWords = getWordsList(States.difficulty.HARD, am);

		}
		ArrayList<String> combinedWords = new ArrayList<String>();
		for (int i = 0; i < fourWords.length; i++) {
			combinedWords.add(fourWords[i]);
		}

		for (int i = 0; i < fiveWords.length; i++) {
			combinedWords.add(fiveWords[i]);
		}

		for (int i = 0; i < sixWords.length; i++) {
			combinedWords.add(sixWords[i]);
		}

		Collections.shuffle(combinedWords);
		for (int i = 0; i < combinedWords.size(); i++) {
			ParseObject wordsObject = new ParseObject("WordsListLatin");
			wordsObject.put("word", combinedWords.get(i).trim());
			wordsObject.saveInBackground();
		}
	}

	// used by setupWordsList to get all the words from a file
	private static String[] getWordsListLatin(final States.difficulty diff, AssetManager am) {
		String file;
		if (diff == States.difficulty.EASY) {
			file = "4words-latin.txt";
		} else if (diff == States.difficulty.MEDIUM) {
			file = "5words-latin.txt";
		} else {
			file = "6words-latin.txt";
		}

		// read entire file as string, parsed into array by new line
		try {
			InputStream stream = am.open(file);
			String contents = IOUtils.toString(stream, "UTF-8");
			String[] wordsList = contents.split(System.getProperty("line.separator"));

			// Shuffle the elements in the array
			Collections.shuffle(Arrays.asList(wordsList));
			return wordsList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// used by setupWordsList to get all the words from a file
	private static String[] getWordsList(final States.difficulty diff, AssetManager am) {
		String file;
		if (diff == States.difficulty.EASY) {
			file = "4words.txt";
		} else if (diff == States.difficulty.MEDIUM) {
			file = "5words.txt";
		} else {
			file = "6words.txt";
		}

		// read entire file as string, parsed into array by new line
		try {
			InputStream stream = am.open(file);
			String contents = IOUtils.toString(stream, "UTF-8");
			String[] wordsList = contents.split(System.getProperty("line.separator"));

			// Shuffle the elements in the array
			Collections.shuffle(Arrays.asList(wordsList));
			return wordsList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
