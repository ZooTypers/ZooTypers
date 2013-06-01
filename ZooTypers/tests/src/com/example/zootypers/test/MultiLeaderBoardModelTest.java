package com.example.zootypers.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.example.zootypers.util.InternetConnectionException;
import com.jayway.android.robotium.solo.Solo;
import com.parse.ParseObject;

public class MultiLeaderBoardModelTest extends ActivityInstrumentationTestCase2<TitlePage> {
    
    private Solo solo;
    private static final int TIMEOUT = 30000;
    private List<ParseObject> entries;
    private Button leaderboardButton;
    private MultiLeaderBoardModel lbModel;
    
    public MultiLeaderBoardModelTest() {
        super(TitlePage.class);
    }

    @Override
    public void setUp() {
        Intent in = new Intent();
        in.putExtra("Testing", 1);
        setActivityIntent(in);
        
        entries = new ArrayList<ParseObject>();
        solo = new Solo(getInstrumentation(), getActivity());
        
        leaderboardButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.leaderboard_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        
        solo.waitForActivity(Leaderboard.class, 15000);
        lbModel = ((Leaderboard) solo.getCurrentActivity()).getMultiLeaderboard();
        solo.sleep(3000);
        
        for (int i = 0; i < 10; i++) {
            ParseObject entry = new ParseObject("MultiLeaderBoard");
            entry.put("score", i);
            entry.put("name", i);
            //add to entries for teardown later on
            entries.add(entry);
            entry.saveInBackground();
        }
    }

    @Test(timeout = TIMEOUT)
    public void constructorWithNameOnlyTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        assertEquals(10, lbModel.getTopScores().length);
    }

    @Test(timeout = TIMEOUT)
    public void constructorWithNameAndSizeTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak", 5);
        assertEquals(5, lbModel.getTopScores().length);
    }

    @Test(timeout = TIMEOUT)
    public void getTopScoresTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak", 10);
        ScoreEntry[] scores = lbModel.getTopScores();
        for(int i = 10; i > 0; i--){
            assertEquals(i, scores[i].getScore());
            assertEquals("Oak", scores[i].getScore());
        }
    }

    @Test(timeout = TIMEOUT)
    public void addEntryTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak", 10);
        lbModel.addEntry(100000);
        ScoreEntry shouldEquals = lbModel.getTopScores()[0];
        assertEquals("Oak", shouldEquals.getName());
        assertEquals(100000, shouldEquals.getScore());
    }

    @Test(timeout = TIMEOUT)
    public void addMultipleEntriesTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak", 10);
        MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel("George", 10);
        lbModel.addEntry(100000);

        //both model should pull the same numbers
        ScoreEntry mlShouldEquals = lbModel.getTopScores()[0];
        assertEquals("Oak", mlShouldEquals.getName());
        assertEquals(100000, mlShouldEquals.getScore());

        ScoreEntry ml2ShouldEquals = ml2.getTopScores()[0];
        assertEquals("Oak", ml2ShouldEquals.getName());
        assertEquals(100000, ml2ShouldEquals.getScore());

        //add top highest
        ml2.addEntry(100001);


        //both model should pull the new highest
        ScoreEntry mlShouldEquals2 = lbModel.getTopScores()[0];
        assertEquals("George", mlShouldEquals2.getName());
        assertEquals(100001, mlShouldEquals2.getScore());

        ScoreEntry ml2ShouldEquals2 = lbModel.getTopScores()[0];
        assertEquals("George", ml2ShouldEquals2.getName());
        assertEquals(100001, ml2ShouldEquals2.getScore());
    }

    @Test(timeout = TIMEOUT)
    public void getRankNoEntryTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        assertEquals(0, lbModel.getRank());
    }

    @Test(timeout = TIMEOUT) 
    public void getRankTopEntryTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        lbModel.addEntry(100000);
        assertEquals(1, lbModel.getRank());
    }

    @Test(timeout = TIMEOUT)
    public void getRankSecondtoTopEntryTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak", 10);
        MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel("George", 10);
        lbModel.addEntry(100000);
        ml2.addEntry(100001);
        assertEquals(2, lbModel.getRank());
    }

    @Test(timeout = TIMEOUT) 
    public void getRelativeScoreSizeTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        lbModel.addEntry(50);
        ScoreEntry[] scores = lbModel.getRelativeScores(6);
        assertEquals(6, scores.length);
    }

    @Test(timeout = TIMEOUT)
    public void isInTopEntriesTrueTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        assertTrue(lbModel.isInTopEntries());
    }

    @Test(timeout = TIMEOUT)
    public void isInTopEntriesFalseTest() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel("Oak");
        lbModel.addEntry(1);
        assertFalse(lbModel.isInTopEntries());
    }

    @Override
    public void tearDown() throws Exception {
        for(ParseObject p : entries) {
            p.delete();
        }
        solo.finishOpenedActivities();
    }
}
