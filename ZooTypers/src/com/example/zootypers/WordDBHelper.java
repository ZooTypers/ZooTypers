package com.example.zootypers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
	
	// Database variables
	public static final String DATABASE_NAME = "ZooTypers.db";
	public static final String DATABASE_TABLE = "ZooWords";
	public static final int DATABASE_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_WORDDATA = "WordData";
	public static final String KEY_DIFFICULTY = "Difficulty";
	// Database queries
	private static final String CREATE_DATABASE =
			"create table " + DATABASE_TABLE + " ("
			+ KEY_ID + " integer primary key autoincrement, "
			+ KEY_DIFFICULTY + " integer, "
			+ KEY_WORDDATA + " text not null); ";
	
	// constructs a new WordDBHelper with the given Context
	public WordDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// creates the Database table
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_DATABASE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	}
}
