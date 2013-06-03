package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * The LeaderboardSingleModel Test will test the different sizes of the score entries
 * stored in the local database; it will test adding the different scores and seeing if
 * they are correctly added by checking the actual scores with the expected scores by
 * getting the top entry scores.
 * 
 * (White box testing).
 * 
 * @author dyxliang
 *
 */
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
        leaderboardButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.leaderboard_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        solo.waitForActivity(Leaderboard.class, 15000);
        lbModel = ((Leaderboard) solo.getCurrentActivity()).getSingleLeaderboard();
        solo.sleep(3000);
    }

    /**
     * Test to see if the size of the leaderboard model is 0 by default.
     */
    @Test(timeout = TIMEOUT)
    public void testSizeWithZeroEntryToLeaderBoard() {
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedSize = 0;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }

    /**
     * Check to make sure that the model size is 1 after adding one entry.
     */
    @Test(timeout = TIMEOUT)
    public void testSizeAfterAddingOneEntryToLeaderBoard() {
        lbModel.addEntry("David", 10);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedSize = 1;
        int actualSize = scoreList.length;
        assertEquals(expectedSize, actualSize);
    }

    /**
     * Check to make sure that the model size is 5 after adding 5 entry.
     * Also making sure that you can have duplicate scores.
     */
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

    /**
     * Checking to make sure that the top score only keep track of the
     * number of top scores specified in the constructor.
     */
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

    /**
     * Adding one top entry and checking that it is the correct top score.
     */
    @Test(timeout = TIMEOUT)
    public void testTopScoreIsCorrectAfterAddingOneEntry() {
        lbModel.addEntry("David", 5);
        ScoreEntry[] scoreList = lbModel.getTopScores();
        int expectedScore = 5;
        ScoreEntry entry = scoreList[0];
        int actualScore = entry.getScore();
        assertEquals(expectedScore, actualScore);
    }

    /**
     * Adding 3 distinct scores and checking if the highest one added is
     * the first top score in the leaderboard model database.
     */
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

    /**
     * Add 1 more entry than the default top score size and making sure that
     * the last highest score added gets placed into number 1 in the top score list.
     */
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

    /**
     * Testing that the clearing leaderboard method is working properly by
     * adding 3 entries and then clearing all of them.
     */
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
    
    /**
     * Clear the leaderboard and also finish up all opened activities.
     */
    @Override
    public void tearDown() throws Exception {
        lbModel.clearLeaderboard();
        solo.finishOpenedActivities();
    }
}
