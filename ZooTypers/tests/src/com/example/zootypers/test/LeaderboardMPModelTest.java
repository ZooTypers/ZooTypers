package com.example.zootypers.test;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.util.InternetConnectionException;

@Suppress
public class LeaderboardMPModelTest extends AndroidTestCase {

    private MultiLeaderBoardModel model;
    private static final int TIMEOUT = 10000;
    
    @Before
    public void setUp() throws Exception {
        model = new MultiLeaderBoardModel();
        model.setPlayer("David");
    }

    @Test(timeout = TIMEOUT)
    public void testCreatingADefaultConstructor() throws InternetConnectionException {
        MultiLeaderBoardModel testmlb = new MultiLeaderBoardModel(10);
        testmlb.setPlayer("David");
    }
    
    @Test(timeout = TIMEOUT)
    public void testDefaultLeaderboardSize() {
        assertEquals(10, model.getTopScores().length);
    }
    
    @Test(timeout = TIMEOUT)
    public void testLeaderBoardWithParam() throws InternetConnectionException {
        model = new MultiLeaderBoardModel(50);
        model.setPlayer("Bryan");
        assertEquals(50, model.getTopScores().length);
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingALowScoreEntry() {
        model.addEntry(3);
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingTheHighestScoreAndExistsInLeaderboard() {
        model.addEntry(1000);
        ScoreEntry[] entries = model.getTopScores();
        int expectedScore = 1000;
        int actualScore = entries[0].getScore();
        assertEquals(expectedScore, actualScore);
    }
    
    @After
    public void tearDown() throws Exception {
        //tear down?
    }
}