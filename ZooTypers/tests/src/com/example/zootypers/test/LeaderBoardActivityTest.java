package com.example.zootypers.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

public class LeaderBoardActivityTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button leaderboardButton;
    
    public LeaderBoardActivityTest() {
        super(TitlePage.class);
    }

    @Override @Before
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
        solo.sleep(1000);
    }

    @Test(timeout = TIMEOUT)
    public void testGoingIntoTheLeaderboard() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.assertCurrentActivity("Check on the current activity.", Leaderboard.class);
        solo.sleep(1000);
        
        
        final Button multiLBButton = (Button) solo.getView(com.example.zootypers.R.id.multi_lb_title);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiLBButton.performClick();
            }
        });
        solo.sleep(1000);
    }
    
    @Override @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
