package com.example.zootypers.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.example.zootypers.util.InternetConnectionException;
import com.jayway.android.robotium.solo.Solo;
import com.parse.ParseObject;

public class LeaderboardMPModelTest extends ActivityInstrumentationTestCase2<TitlePage> {

    
    private Solo solo;
    private static final int TIMEOUT = 30000;
    private Button leaderboardButton;
    private MultiLeaderBoardModel lbModel;
    
    public LeaderboardMPModelTest() {
        super(TitlePage.class);
    }

    @Override
    public void setUp() throws InternetConnectionException {
        Intent in = new Intent();
        in.putExtra("Testing", 1);
        setActivityIntent(in);
        
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
        lbModel.setPlayer("David");
        solo.sleep(3000);
    }
    
    @Test(timeout = TIMEOUT)
    public void testCreatingADefaultConstructorNameOnly() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel();
        lbModel.setPlayer("David");
    }
    
    @Test(timeout = TIMEOUT)
    public void testDefaultLeaderboardDefaultSize10() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(10);
        lbModel.setPlayer("David");
    }
    
    @Test(timeout = TIMEOUT)
    public void testLeaderBoardWithParamSize300() throws InternetConnectionException {
        lbModel = new MultiLeaderBoardModel(300);
        lbModel.setPlayer("David");
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingALowScoreEntry() {
        lbModel.addEntry(33333);
    }
    
    @Test(timeout = TIMEOUT)
    public void testAddingTheHighestScoreAndExistsInLeaderboard() {
        
    }
    
    @Override
    public void tearDown() throws Exception {
        //lbModel.clearLeaderboard();
        solo.finishOpenedActivities();
    }
}