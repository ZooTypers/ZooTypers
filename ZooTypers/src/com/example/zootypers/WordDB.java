package com.example.zootypers;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WordDB {

	// Database fields
	private WordDBHelper dbHelper;
	private String[] allColumns = { WordDBHelper.KEY_ID,
			WordDBHelper.KEY_WORDDATA, WordDBHelper.KEY_DIFFICULTY };
	
	private String ALL_ROWS = "SELECT * FROM " + dbHelper.DATABASE_TABLE;
	// creates a new WordDB with the given context
	public WordDB(Context context) {
		dbHelper = new WordDBHelper(context);
	}
	
	// creates a new entry in the table and returns it
	public WordData createWordData(String word, int diff) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(WordDBHelper.KEY_WORDDATA, word);
		values.put(WordDBHelper.KEY_DIFFICULTY, diff);
		long insertId = database.insert(WordDBHelper.DATABASE_TABLE,  null,
				values);
		// used to go through the database
		Cursor cursor = database.query(WordDBHelper.DATABASE_TABLE,
				allColumns, WordDBHelper.KEY_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		WordData newWordData = cursorToWordData(cursor);
		cursor.close();
		database.close();
		return newWordData;
	}
	
	// deletes a certain wordData from the table
	public void deleteWordData(WordData worddata) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		long id = worddata.getId();
		database.delete(WordDBHelper.DATABASE_TABLE, WordDBHelper.KEY_ID
				+ " = " + id, null);
		database.close();
	}
	
	public void resetDatabase() {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(ALL_ROWS, new String[] {});
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WordData worddata = cursorToWordData(cursor);
			long id = worddata.getId();
			database.delete(WordDBHelper.DATABASE_TABLE, WordDBHelper.KEY_ID
				+ " = " + id, null);
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
	}
	// gets all the entries in the table
	public Map<WordData, Integer> getAllWords() {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Map<WordData, Integer> words = new HashMap<WordData, Integer>();
		
		Cursor cursor = database.query(WordDBHelper.DATABASE_TABLE, allColumns,
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WordData worddata = cursorToWordData(cursor);
			words.put(worddata, cursor.getInt(2));
			cursor.moveToNext();
		}
		// close the cursor
		cursor.close();
		database.close();
		return words;
	}
	
	// private function to convert cursor pointer to WordData
	private WordData cursorToWordData(Cursor cursor) {
		WordData worddata = new WordData();
		worddata.setId(cursor.getLong(0));
		worddata.setWord(cursor.getString(1));
		return worddata;
	}
	
	
}
