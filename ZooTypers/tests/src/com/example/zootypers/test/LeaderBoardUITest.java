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

/**
 * Test Class that tests the UI of the leaderboard. No logic involved
 * @author oaknguyen
 *
 */
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
    private void ranksNamesScoreHelper(Solo solo){
    	assertTrue(solo.searchText("Rank"));
    	assertTrue(solo.searchText("Player Name"));
    	assertTrue(solo.searchText("Score"));
    	for (int i = 1; i <= 10; i++) {
    		assertTrue(solo.searchText("" + i));
    	}
    
    }
    
    /**
     * Tests the initial display which should be the singleplayer tab
     */
    @Test(timeout = TIMEOUT)
    public void initialDisplayTest() {
    	assertTrue(solo.searchText("Singleplayer Leaderboard"));
    	ranksNamesScoreHelper(solo);
    }
    /**
     * Tests the main menu button and if it returned to titlepage
     */
    @Test(timeout = TIMEOUT)
    public void mainMenuButtonPressTest() {
    	solo.clickOnButton(0);
    	solo.assertCurrentActivity("should be TitlePage", TitlePage.class, true);
    }
    /**
     * Test the multiplayerTab functionality and the display
     */
    @Test(timeout = TIMEOUT)
    public void multiplayerTabAndDisplayTest() {
    	solo.clickOnText("Multiplayer");
    	assertTrue(solo.searchText("Multiplayer Leaderboard"));
    	ranksNamesScoreHelper(solo);
    }
    /**
     * Tests the Relative Position tab functionality
     */
    @Test(timeout = TIMEOUT)
    public void relativePositionTabTest() {
    	solo.clickOnText("Relative\nPosition");
    	assertTrue(solo.searchText("Log in"));
    }

    /**
     * Test the Relative Position tab, logs in to test the initial Display
     */
    @Test(timeout = TIMEOUT)
    public void relativePositionInitalDisplayTest() {
    	//go to tab
    	solo.clickOnText("Relative\nPosition");
    	solo.enterText(0, "oakage");
    	solo.enterText(1, "asdfasdf");
    	solo.clickOnButton("Login");
    	//wait for authorization/rendering
    	solo.sleep(2000);
    	solo.clickOnButton("Close");
    	assertTrue(solo.searchText("Your Relative Position :"));
    	ranksNamesScoreHelper(solo);
    	solo.clickOnButton(0);
    	solo.clickOnButton("Logout");
    }
    /**
     * Test Forgot Password screen in the login pop up
     */
    @Test(timeout = TIMEOUT)
    public void relativePositionLoginForgotPasswordPopsUpTest(){
    	solo.clickOnText("Relative\nPosition");
    	solo.clickOnText("Forgot your password?");
    	solo.clickOnButton("X");
    	assertTrue(solo.searchText("Login"));
    }
    /**
     * Test Registration screen in the login pop up
     */
    @Test(timeout = TIMEOUT)
    public void relativePositionLoginRegisterPopsUpTest(){
    	solo.clickOnText("Relative\nPosition");
    	solo.clickOnText("Join now!");
    	solo.clickOnButton("X");
    }
    /**
     * Clear the leaderboard and also finish up all opened activities.
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}