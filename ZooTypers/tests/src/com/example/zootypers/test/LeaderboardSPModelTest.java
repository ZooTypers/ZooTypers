package com.example.zootypers.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.Suppress;

import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.ui.Leaderboard;

@Suppress
public class LeaderboardSPModelTest extends ActivityUnitTestCase<Leaderboard> {

	public LeaderboardSPModelTest() {
        super(Leaderboard.class);
        // TODO Auto-generated constructor stub
    }

	private Leaderboard lbActivity;
	private SingleLeaderBoardModel lbModel;
    private Context context;
	private static final int TIMEOUT = 10000;
	
    @Before @Override
    public void setUp() throws Exception {
        lbActivity = getActivity();
        context = lbActivity.getApplicationContext();
    	lbModel = lbActivity.getLeaderboard();
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
    	ScoreEntry[] scoreList = lbModel.getTopScores();
    	int expectedSize = 0;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingOneEntryToLeaderBoard() {
    	lbModel.addEntry("David", 10);
    	ScoreEntry[] scoreList = lbModel.getTopScores();
    	int expectedSize = 1;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingFiveDuplicateEntryToLeaderBoard() {
    	for (int i = 0; i < 5; i++) {
    	    lbModel.addEntry("David", 10);
    	}
    	ScoreEntry[] scoreList = lbModel.getTopScores();
    	int expectedSize = 5;
    	int actualSize = scoreList.length;
    	assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingMoreThanDefaultSize() {
        for (int i = 0; i < 13; i++) {
            lbModel.addEntry("David", 10);
        }
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedSize = 10;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAddingOneEntry() {
        lbModel.addEntry("David", 5);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedScore = 5;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding3Entries() {
        lbModel.addEntry("David", 5);
        lbModel.addEntry("David", 3);
        lbModel.addEntry("David", 7); 
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedScore = 7;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding11Entries() {
        int[] scores = {10, 3, 6, 50, 20, 15, 23, 9, 17, 11, 100};
        for (int i = 0; i < scores.length; i++) {
            lbModel.addEntry("David", scores[i]);
        }
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedScore = 100;
        int actualScore = scoreList.length;
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testClearingTheLeaderboardAfterAdding3Entries() {
        lbModel.addEntry("David", 1);
        lbModel.addEntry("David", 2);
        lbModel.addEntry("David", 3);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        lbModel.clearLeaderboard();
        int expectedSize = 0;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }
    
    @After
    public void tearDown() throws Exception {
        //tear down?
    }
}
