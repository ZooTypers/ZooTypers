package com.example.zootypers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;

import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;


public class LeaderBoardUITest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button leaderboardButton;

    public LeaderBoardUITest() {
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
        solo.sleep(3000);
    }
    
//    @Test
//    public void initialDisplayTest() {
//    	assertTrue(solo.searchText("Singleplayer Leaderboard"));
//    }
    
    @Test
    public void multiplayerDisplayTest() {
    	android.app.Fragment m = solo.getCurrentActivity().getFragmentManager().findFragmentByTag("multiplayer");
    	Log.e(m.getTag(), "Should be Multiplayer");
    
    }
    
    @Test
    public void multiplayerDisplayTest2() {
    	Context mContext = this.getInstrumentation().getContext();
    	int height = mContext.getResources().getDisplayMetrics().heightPixels;
	    int width = mContext.getResources().getDisplayMetrics().widthPixels;
    	solo.clickOnScreen(width/2, height - 10);
    	
    	solo.sleep(5000);
    
    }
    @Test
    public void multiplayerDisplayTest3() {
    	Context mContext = this.getInstrumentation().getContext();
    	int height = mContext.getResources().getDisplayMetrics().heightPixels;
	    int width = mContext.getResources().getDisplayMetrics().widthPixels;
    	solo.clickOnScreen(width/2, 2);
    	
    
    }
    /**
     * Clear the leaderboard and also finish up all opened activities.
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}