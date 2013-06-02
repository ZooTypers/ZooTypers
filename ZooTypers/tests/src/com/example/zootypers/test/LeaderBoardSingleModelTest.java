package com.example.zootypers.test;

import org.junit.Test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.Button;

import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.core.SinglePlayerModel;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.SinglePlayer;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

public class LeaderBoardSingleModelTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button leaderboardButton;
    private SingleLeaderBoardModel lbModel;
    
    public LeaderBoardSingleModelTest() {
        super(TitlePage.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        leaderboardButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.leaderboard_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        //solo.sleep(5000);
        solo.waitForActivity(Leaderboard.class, 15000);
        lbModel = ((Leaderboard) solo.getCurrentActivity()).getSingleLeaderboard();
        solo.sleep(3000);
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
        ScoreEntry entry = scoreList[0];
        int actualScore = entry.getScore();
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAdding3Entries() {
        lbModel.addEntry("David", 5);
        lbModel.addEntry("David", 3);
        lbModel.addEntry("David", 7); 
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedScore = 7;
        ScoreEntry entry = scoreList[0];
        int actualScore = entry.getScore();
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
        ScoreEntry entry = scoreList[0];
        int actualScore = entry.getScore();
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = TIMEOUT)
    public void testClearingTheLeaderboardAfterAdding3Entries() {
        lbModel.addEntry("David", 1);
        lbModel.addEntry("David", 2);
        lbModel.addEntry("David", 3);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        lbModel.clearLeaderboard();
        scoreList = lbModel.getTopScores();
        int expectedSize = 0;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }
    
//    @Test(timeout = TIMEOUT)
//    public void testGoingIntoTheLeaderboard() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                leaderboardButton.performClick();
//            }
//        });
//        solo.sleep(1000);
//        solo.assertCurrentActivity("Check on the current activity.", Leaderboard.class);
//        solo.sleep(1000);
//    }
    
//    @Test(timeout = TIMEOUT)
//    public void testGoingToMultiplayerTab() {
//        solo.clickOnText("MULTIPLAYER");
//        solo.searchText("Multiplayer Leaderboard");
//    }
//
//    @Test(timeout = TIMEOUT)
//    public void testGoingToSingleplayerTab() {
//        solo.clickOnText("SINGLEPLAYER");
//        solo.searchText("Singleplayer Leaderboard");
//    }
//    
//    @Test(timeout = TIMEOUT)
//    public void testGoingToFriendsTab() {
//        solo.clickOnText("MULTIPLAYER");
//        solo.searchText("Friends Leaderboard");
//    }
    
    @Override
    public void tearDown() throws Exception {
        lbModel.clearLeaderboard();
        solo.finishOpenedActivities();
    }
}
