package com.example.zootypers.test;

import org.junit.Test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.example.zootypers.util.InternetConnectionException;
import com.jayway.android.robotium.solo.Solo;

public class LeaderboardMultiModelTest extends ActivityInstrumentationTestCase2<TitlePage> {
    private Solo solo;
    private static final int TIMEOUT = 30000;
    private Button leaderboardButton;
    private MultiLeaderBoardModel lbModel;
    
    public LeaderboardMultiModelTest() {
        super(TitlePage.class);
    }

    @Override
    public void setUp() throws Exception {        
        solo = new Solo(getInstrumentation(), getActivity());
        
        Intent myIntent = new Intent();
        myIntent.putExtra("Testing", 1);
        setActivityIntent(myIntent);
        
        leaderboardButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.leaderboard_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        
        solo.waitForActivity(Leaderboard.class, 15000);
        
        lbModel = ((Leaderboard) solo.getCurrentActivity()).getMultiLeaderboard();
        lbModel.setPlayer("David");
        solo.sleep(3000);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCreatingADefaultConstructorNameOnly() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel();
        lbModel.setPlayer("David");
        //assertEquals(10, lbModel.getTopScores().length);
    }
    
    @Test(timeout = TIMEOUT)
    public void testDefaultLeaderboardDefaultSize13() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(13);
        lbModel.setPlayer("David");
        //assertEquals(13, lbModel.getTopScores().length);
    }
    
    @Test(timeout = TIMEOUT)
    public void testLeaderBoardWithParamSize300() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(300);
        lbModel.setPlayer("David");
        //assertEquals(300, lbModel.getTopScores().length);
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingVeryHighScoreAndNameExists() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = new MultiLeaderBoardModel();
        lbModel2.setPlayer("John");
        lbModel2.addEntry(25000);
        solo.sleep(1000);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int actualScore = scoreList[0].getScore();
        String actualName = scoreList[0].getName();
        assertEquals("TEST", actualName);
        assertEquals(30000, actualScore);
        lbModel2.clearLeaderboard();
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingHighScoreInTopRank() {
        lbModel.addEntry(33333);
        assertTrue(lbModel.isInTopEntries());
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingMultipleScoresAndChecking1RelativeScore() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = null;
        MultiLeaderBoardModel lbModel3 = null;
        try {
            lbModel.addEntry(200000);
            
            lbModel2 = new MultiLeaderBoardModel();
            lbModel2.setPlayer("John");
            lbModel2.addEntry(100000);
            
            lbModel3 = new MultiLeaderBoardModel();
            lbModel3.setPlayer("Bob");
            lbModel3.addEntry(300000);
            
            ScoreEntry[] relativeList = lbModel.getRelativeScores(1);
            assertEquals(300000, relativeList[0].getScore());
            assertEquals(200000, relativeList[1].getScore());
            assertEquals(100000, relativeList[2].getScore());
            assertEquals(3, relativeList.length);
        } catch (Exception e) {
            Log.v("There is an error in leaderboard MP testing.", "error");
        } finally {
            lbModel2.clearLeaderboard();
            solo.sleep(1000);
            lbModel3.clearLeaderboard();
        }
    }
    
    @Test(timeout = TIMEOUT)
    public void getRankNoEntryTest() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = new MultiLeaderBoardModel();
        lbModel2.setPlayer("Oak");
        assertEquals(0, lbModel.getRank());
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingHighestScoreRankOne() {
        lbModel.addEntry(33333);
        solo.sleep(3000);
        assertEquals(1, lbModel.getRank());
    }
    
    @Test(timeout = TIMEOUT)
    public void getRankSecondtoTopEntryTest() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = new MultiLeaderBoardModel(10);
        lbModel2.setPlayer("Oak");
        lbModel.addEntry(100000);
        lbModel2.addEntry(100001);
        assertEquals(2, lbModel.getRank());
        lbModel2.clearLeaderboard();
    }
    
    @Override
    public void tearDown() throws Exception {
        lbModel.clearLeaderboard();
        solo.finishOpenedActivities();
    }
}