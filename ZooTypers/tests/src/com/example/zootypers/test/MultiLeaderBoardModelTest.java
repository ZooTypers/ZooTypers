package com.example.zootypers.test;



import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.PreGameSelection;
import com.example.zootypers.util.InternetConnectionException;
import com.jayway.android.robotium.solo.Solo;
import com.parse.ParseObject;


public class MultiLeaderBoardModelTest extends ActivityInstrumentationTestCase2<Leaderboard>{
	private Solo solo;
	private Intent in;	
	private MultiLeaderBoardModel ml;
	private Leaderboard b;
    private static final int TIMEOUT = 30000;
	private List<ParseObject> entries;
	public MultiLeaderBoardModelTest() {
		super(Leaderboard.class);
	}
	 
	@Override
	public void setUp() {
		Intent in = new Intent();
		in.putExtra("Testing", 1);
		setActivityIntent(in);
		entries = new ArrayList<ParseObject>();
        solo = new Solo(getInstrumentation(), getActivity());
		for (int i = 0; i < 10; i++) {
			ParseObject entry = new ParseObject("MultiLeaderBoard");
			entry.put("score", i);
			entry.put("name", i);
			//add to entries for teardown later on
			entries.add(entry);
			entry.saveInBackground();
		}
        

	}
	
	@Override
	public void tearDown() throws Exception {
		for(ParseObject p : entries) {
			p.delete();			
		}
        solo.finishOpenedActivities();
	}

	@Test
	public void constructorWithNameOnlyTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		assertEquals(10, ml.getTopScores().length);
	}
	@Test
	public void constructorWithNameAndSizeTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel(5);
		ml.setPlayer("Oak");
		assertEquals(5, ml.getTopScores().length);
	}
	
	@Test
	public void getTopScoresTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel(10);
		ml.setPlayer("Oak");
		ScoreEntry[] scores = ml.getTopScores();
		for(int i = 10; i > 0; i--){
			assertEquals(i, scores[i].getScore());
			assertEquals("Oak", scores[i].getScore());
		}
	}

	@Test
	public void addEntryTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel(10);
		ml.setPlayer("Oak");
		ml.addEntry(100000);
		ScoreEntry shouldEquals = ml.getTopScores()[0];
		assertEquals("Oak", shouldEquals.getName());
		assertEquals(100000, shouldEquals.getScore());
	}
	
	@Test
	public void addMultipleEntriesTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel(10);
		ml.setPlayer("Oak");

		MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel(10);
		ml2.setPlayer("George");
		ml.addEntry(100000);
		
		//both model should pull the same numbers
		ScoreEntry mlShouldEquals = ml.getTopScores()[0];
		assertEquals("Oak", mlShouldEquals.getName());
		assertEquals(100000, mlShouldEquals.getScore());
		
		ScoreEntry ml2ShouldEquals = ml2.getTopScores()[0];
		assertEquals("Oak", ml2ShouldEquals.getName());
		assertEquals(100000, ml2ShouldEquals.getScore());
		
		//add top highest
		ml2.addEntry(100001);
		
		
		//both model should pull the new highest
		ScoreEntry mlShouldEquals2 = ml.getTopScores()[0];
		assertEquals("George", mlShouldEquals2.getName());
		assertEquals(100001, mlShouldEquals2.getScore());
		
		ScoreEntry ml2ShouldEquals2 = ml.getTopScores()[0];
		assertEquals("George", ml2ShouldEquals2.getName());
		assertEquals(100001, ml2ShouldEquals2.getScore());
	}
	
	@Test
	public void getRankNoEntryTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		assertEquals(0, ml.getRank());
	}
	
	@Test 
	public void getRankTopEntryTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		ml.addEntry(100000);
		assertEquals(1, ml.getRank());
	}
	
	@Test
	public void getRankSecondtoTopEntryTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel(10);
		ml.setPlayer("Oak");
		MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel(10);
		ml2.setPlayer("George");
		ml.addEntry(100000);
		ml2.addEntry(100001);
		assertEquals(2, ml.getRank());
	}
	
	@Test 
	public void getRelativeScoreSizeTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		ml.addEntry(50);
		ScoreEntry[] scores = ml.getRelativeScores(6);
		assertEquals(6, scores.length);
	}
	
	@Test
	public void isInTopEntriesTrueTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		assertTrue(ml.isInTopEntries());
	}
	
	@Test
	public void isInTopEntriesFalseTest() throws InternetConnectionException {
		ml = new MultiLeaderBoardModel();
		ml.setPlayer("Oak");
		ml.addEntry(1);
		assertFalse(ml.isInTopEntries());
	}
	
}
