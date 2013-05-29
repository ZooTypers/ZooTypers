package com.example.zootypers.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;

import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.core.SingleLeaderBoardModel;

@Suppress
public class LeaderboardSPModelTest extends AndroidTestCase {

	private Context context;
	private SingleLeaderBoardModel model;
	private static final int TIMEOUT = 10000;
	
    @Before @Override
    public void setUp() throws Exception {
        context = getContext();
    	model = new SingleLeaderBoardModel(context);
    }

    @Test(timeout = TIMEOUT)
    public void testCreateDefaultValueConstructor() {
    	new SingleLeaderBoardModel(context);
    }

    @Test(timeout = TIMEOUT)
    public void testCreateConstructorZeroEntries() {
        new SingleLeaderBoardModel(context, 0);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCreateConstructorNegativeEntries() {
        new SingleLeaderBoardModel(context, -1);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCreateConstructorPositiveEntries() {
        new SingleLeaderBoardModel(context, 1);
    }
    
    @Test(expected=IllegalArgumentException.class, timeout = TIMEOUT)
    public void testCreateConstructorNullValues() {
        new SingleLeaderBoardModel(null, 0);
        new SingleLeaderBoardModel(null);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeWithZeroEntryToLeaderBoard() {
    	ScoreEntry[] scoreList = model.getTopScores();
    	int expectedSize = 0;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingOneEntryToLeaderBoard() {
    	model.addEntry("David", 10);
    	ScoreEntry[] scoreList = model.getTopScores();
    	int expectedSize = 1;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingFiveDuplicateEntryToLeaderBoard() {
    	for (int i = 0; i < 5; i++) {
    	    model.addEntry("David", 10);
    	}
    	ScoreEntry[] scoreList = model.getTopScores();
    	int expectedSize = 5;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingMoreThanDefaultSize() {
        for (int i = 0; i < 13; i++) {
            model.addEntry("David", 10);
        }
        ScoreEntry[] scoreList = model.getTopScores();
        int expectedSize = 10;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAddingOneEntry() {
        model.addEntry("David", 5);
        ScoreEntry[] scoreList = model.getTopScores();
        int expectedScore = 5;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding3Entries() {
        model.addEntry("David", 5);
        model.addEntry("David", 3);
        model.addEntry("David", 7); 
        ScoreEntry[] scoreList = model.getTopScores();
        int expectedScore = 7;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding11Entries() {
        int[] scores = {10, 3, 6, 50, 20, 15, 23, 9, 17, 11, 100};
        for (int i = 0; i < scores.length; i++) {
            model.addEntry("David", scores[i]);
        }
        ScoreEntry[] scoreList = model.getTopScores();
        int expectedScore = 100;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testClearingTheLeaderboardAfterAdding3Entries() {
        model.addEntry("David", 1);
        model.addEntry("David", 2);
        model.addEntry("David", 3);
        ScoreEntry[] scoreList = model.getTopScores();
        model.clearLeaderboard();
        int expectedSize = 0;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }
    
    @After
    public void tearDown() throws Exception {
        //tear down?
    }
}
