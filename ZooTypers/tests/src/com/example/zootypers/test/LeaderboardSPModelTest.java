package com.example.zootypers.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.test.AndroidTestCase;

import com.example.zootypers.core.SingleLeaderBoardModel;

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
    	List<Integer> scoreList = model.getTopScores();
    	int expectedSize = 0;
    	int actualSize = scoreList.size();
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingOneEntryToLeaderBoard() {
    	model.addEntry(10);
    	List<Integer> scoreList = model.getTopScores();
    	int expectedSize = 1;
    	int actualSize = scoreList.size();
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingFiveDuplicateEntryToLeaderBoard() {
    	for (int i = 0; i < 5; i++) {
        	model.addEntry(10);	
    	}
    	List<Integer> scoreList = model.getTopScores();
    	int expectedSize = 5;
    	int actualSize = scoreList.size();
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingMoreThanDefaultSize() {
        for (int i = 0; i < 13; i++) {
            model.addEntry(10); 
        }
        List<Integer> scoreList = model.getTopScores();
        int expectedSize = 10;
        int actualSize = scoreList.size();
        assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAddingOneEntry() {
        model.addEntry(5); 
        List<Integer> scoreList = model.getTopScores();
        int expectedScore = 5;
        int actualScore = scoreList.get(0);
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding3Entries() {
        model.addEntry(5);
        model.addEntry(3);
        model.addEntry(7); 
        List<Integer> scoreList = model.getTopScores();
        int expectedScore = 7;
        int actualScore = scoreList.get(0);
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding11Entries() {
        int[] scores = {10, 3, 6, 50, 20, 15, 23, 9, 17, 11, 100};
        for (int i = 0; i < scores.length; i++) {
            model.addEntry(scores[i]);
        }
        List<Integer> scoreList = model.getTopScores();
        int expectedScore = 100;
        int actualScore = scoreList.get(0);
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testClearingTheLeaderboardAfterAdding3Entries() {
        model.addEntry(1);
        model.addEntry(2);
        model.addEntry(3);
        List<Integer> scoreList = model.getTopScores();
        model.clearLeaderboard();
        int expectedSize = 0;
        int actualSize = scoreList.size();
        assertEquals(expectedSize, actualSize);
    }
    
    @After
    public void tearDown() throws Exception {
        //tear down?
    }
}
