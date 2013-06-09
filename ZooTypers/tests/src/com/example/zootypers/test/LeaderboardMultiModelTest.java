package com.example.zootypers.test;

import java.util.List;

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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * The LeaderboardMultiModel Test will test creating default constructors and ones with
 * parameters. It will test adding an entry and see if it's in the database; it will also
 * test getting the highest rank, relative scores, and top scores to make sure all the 
 * methods in the multiplayer model works properly. Since each model fetches parse data
 * at the beginning, we constantly have to update the records with a new model of the same 
 * person. This is perfectly fine since the model will never have an adding and viewing of 
 * scores in the same class instance.
 * 
 * (White box testing).
 * 
 * @author dyxliang & oaknguyen
 *
 */
public class LeaderboardMultiModelTest extends ActivityInstrumentationTestCase2<TitlePage> {

    // private fields for testing methods
    private Solo solo;
    private static final int TIMEOUT = 30000;
    private Button leaderboardButton;
    private MultiLeaderBoardModel lbModel;

    public LeaderboardMultiModelTest() {
        super(TitlePage.class);
    }

    /**
     * Setting up the data and activity before running each test method.
     */
    @Override
    public void setUp() throws Exception {        
        Intent myIntent = new Intent();
        myIntent.putExtra("Testing", true);
        setActivityIntent(myIntent);
        solo = new Solo(getInstrumentation(), getActivity());

        //intent to tell the Leaderboard that this is for testing database


        //start off in title page and click on leaderboard to start tests
        leaderboardButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.leaderboard_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });

        //wait for the leaderboard activity to start and get the leaderboard model
        solo.waitForActivity(Leaderboard.class, 15000);
        lbModel = ((Leaderboard) solo.getCurrentActivity()).getMultiLeaderboard();
        lbModel.setPlayer("David");
        solo.sleep(3000);
    }

    /**
     * Test to make sure that you can create a MultiLeaderBoardModel with the player name only.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testCreatingADefaultConstructorNameOnly() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel();
        lbModel.setPlayer("David");
    }

    /**
     * Test to make sure you can create a constructor with parameter of size 13.
     * Note: It was required in the code that the param is positive.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testDefaultLeaderboardDefaultSize13() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(13);
        lbModel.setPlayer("David");
    }

    /**
     * Test to make sure you can create a constructor with param of size 300.
     * Note: It was required in the code that the param is positive.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testLeaderBoardWithParamSize300() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(300);
        lbModel.setPlayer("David");
    }
    
    /**
     * Test to make sure that when you have a very high score it become the number one
     * top score and the name of that score also matches the name in database.
     * Adding dynamically does not show up since model gets top scores at initialization. Must create
     * another model to get most recent ones. 
     * 
     * @throws InternetConnectionException
     * @throws ParseException 
     */
    @Test(timeout = TIMEOUT)
    public void testAddingVeryHighScoreAndNameExists() throws InternetConnectionException, ParseException {
        lbModel.addEntry(1000000);
        //get all top scores..need new model since top scores are fetched at model initialization
        solo.sleep(5000);
        MultiLeaderBoardModel refresh = new MultiLeaderBoardModel();
        refresh.setPlayer("David");
        solo.sleep(5000);
        ScoreEntry[] scoreList = refresh.getTopScores();
        int actualScore = scoreList[0].getScore();
        String actualName = scoreList[0].getName();
        //make sure that the highest score TEST still the highest
        assertEquals("David", actualName);
        assertEquals(1000000, actualScore);
    }

    /**
     * Testing if adding a very high score that it is in one of the top ranks.
     * @throws InterruptedException 
     * @throws InternetConnectionException 
     */
    @Test(timeout = TIMEOUT)
    public void testAddingHighScoreInTopRank() throws InterruptedException, InternetConnectionException {
        lbModel.addEntry(33333);
        //wait for parse to save the scores
        Thread.sleep(3000);
        //get the refresh topscores
        MultiLeaderBoardModel refresh = new MultiLeaderBoardModel();
        refresh.setPlayer("David");

        assertTrue(refresh.isInTopEntries());
    }

    /**
     * Adding a high score above and below the current player's high score and getting
     * 1 relative high score above and below that current player and see if they match.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testAddingMultipleScoresAndChecking1RelativeScore() 
    throws InternetConnectionException {
        //instantiate the other 2 test models
        MultiLeaderBoardModel lbModel2 = null;
        MultiLeaderBoardModel lbModel3 = null;
        //necessary for refresh of allscore fetch 
        MultiLeaderBoardModel refresh = null;
        try {
            //add the current player's high score
            lbModel.addEntry(200000);

            //add the two test players' high score around the current player
            lbModel2 = new MultiLeaderBoardModel();
            lbModel2.setPlayer("John");
            lbModel2.addEntry(100000);

            lbModel3 = new MultiLeaderBoardModel();
            lbModel3.setPlayer("Bob");
            lbModel3.addEntry(300000);

            //wait for parse to save the scores
            Thread.sleep(5000);
            
            //get the new allscores once scores added to db
            refresh = new MultiLeaderBoardModel();
            refresh.setPlayer("David");
            //check to see if the high score are relative to the (David) 200000 score
            ScoreEntry[] relativeList = refresh.getRelativeScores();
            assertEquals(300000, relativeList[0].getScore());
            assertEquals(200000, relativeList[1].getScore());
            assertEquals(100000, relativeList[2].getScore());
            assertEquals(3, relativeList.length);
        } catch (Exception e) {
        	e.fillInStackTrace();
            Log.v("There is an error in leaderboard MP testing.", "error");
        } finally {
            //clear leaderboards
            lbModel2.clearLeaderboard();
            solo.sleep(1000);
            lbModel3.clearLeaderboard();
        }
    }

    /**
     * Test if the get highest relative rank works and return the appropariate highest rel rank.
     * @throws InterruptedException 
     * @throws InternetConnectionException 
     */
    @Test(timeout = TIMEOUT)
    public void testGettingHighestRelativeRank() throws InterruptedException, InternetConnectionException {
        lbModel.addEntry(200);
        //wait for parse to save the scores
        Thread.sleep(3000);
        
        //refresh fetch of parse
        MultiLeaderBoardModel refresh = new MultiLeaderBoardModel();
        refresh.setPlayer("David");
        
        int highestRelRank = refresh.getHighestRelScoreRank();
        assertEquals(1, highestRelRank);
    }
    
    /**
     * Adding a player but not giving him a score and checking it the rank is still 0.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testGetRankNoEntryTest() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = new MultiLeaderBoardModel();
        lbModel2.setPlayer("Oak");
        assertEquals(0, lbModel.getRank());
    }

    /**
     * Adding 2 highest scores in the database and make sure that the 2nd highest is rank 2.
     * 
     * @throws InternetConnectionException
     */
    @Test(timeout = TIMEOUT)
    public void testGetRankSecondtoTopEntryTest() throws InternetConnectionException {
        MultiLeaderBoardModel lbModel2 = new MultiLeaderBoardModel();
        lbModel2.setPlayer("Oak");
        lbModel.addEntry(100000);
        lbModel2.addEntry(100001);
        solo.sleep(3000);
        
        //get refreshed fetch of parse data
        MultiLeaderBoardModel refresh = new MultiLeaderBoardModel();
        refresh.setPlayer("David");
        assertEquals(2, refresh.getRank());
        lbModel2.clearLeaderboard();
    }
    
    /**
     * Tear down by clearing the leaderboards and finishing opened activities in Robotium.
     */
    @Override
    public void tearDown() throws Exception {
        lbModel.clearLeaderboard();
        solo.finishOpenedActivities();
    }
}