package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * Test Class that tests the UI of the leaderboard. No logic involved.
 * 
 * @author oaknguyen & dyxliang
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

    /**
     * Tests the initial display which should be the singleplayer tab.
     */
    @Test(timeout = TIMEOUT)
    public void testInitialDisplay() {
        assertTrue(solo.searchText("Singleplayer Leaderboard"));
        ranksNamesScoreHelper(solo);
    }

    /**
     * Tests the main menu button and if it returned to title page.
     */
    @Test(timeout = TIMEOUT)
    public void testMainMenuButtonPress() {
        solo.clickOnButton(0);
        solo.assertCurrentActivity("should be TitlePage", TitlePage.class, true);
    }

    /**
     * Test the multiplayerTab functionality and the display.
     */
    @Test(timeout = TIMEOUT)
    public void testMultiplayerTabAndDisplay() {
        solo.clickOnText("Multiplayer");
        solo.sleep(3000);
        assertTrue(solo.searchText("Multiplayer Leaderboard"));
        ranksNamesScoreHelper(solo);
    }

    /**
     * Test the singleplayerTab functionality and the display.
     */
    @Test(timeout = TIMEOUT)
    public void testSingleplayerTabAndDisplay() {
        solo.clickOnText("Singleplayer");
        solo.sleep(3000);
        assertTrue(solo.searchText("Singleplayer Leaderboard"));
    }

    /**
     * Tests the Relative Position tab functionality.
     */
    @Test(timeout = TIMEOUT)
    public void testRelativePositionTab() {
        solo.clickOnText("Relative\nPosition");
        solo.searchText("Log in");
    }

    /**
     * Test the Relative Position tab, logs in as a user to test getting relative scores.
     */
    @Test(timeout = TIMEOUT)
    public void testRelativePositionInitalDisplay() {
        //go to tab
        solo.clickOnText("Relative\nPosition");
        solo.enterText(0, "bbbb");
        solo.enterText(1, "bbbb");
        final Button loginButton = (Button) solo.getView(com.example.zootypers.R.id.login_button);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loginButton.performClick();
            }
        });
        //wait for authorization/rendering
        solo.sleep(3000);
        final Button button = (Button) solo.getView(com.example.zootypers.R.id.main_menu_button);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        final Button logoutButton = (Button) solo.getView(com.example.zootypers.R.id.logout_button);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                logoutButton.performClick();
            }
        });
    }

    /**
     * Test Forgot Password screen in the login pop up when we click on it.
     */
    @Test(timeout = TIMEOUT)
    public void testRelativePositionLoginForgotPasswordPopsUp(){
        solo.clickOnText("Relative\nPosition");
        final TextView forgotButton = (TextView) solo.getView(com.example.zootypers.R.id.forgot_pw);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                forgotButton.performClick();
            }
        });
        assertTrue(solo.searchText("Reset"));
    }

    /**
     * Test Registration screen in the login pop up when we click on it.
     */
    @Test(timeout = TIMEOUT)
    public void testRelativePositionLoginRegisterPopsUp(){
        solo.clickOnText("Relative\nPosition");
        final TextView joinNow = (TextView) solo.getView(com.example.zootypers.R.id.join_now);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                joinNow.performClick();
            }
        });
        assertTrue(solo.searchText("Register"));
    }

    /*
     * Private helper methods for testing the UI.
     */
    private void ranksNamesScoreHelper(Solo solo){
        assertTrue(solo.searchText("Rank"));
        assertTrue(solo.searchText("Player Name"));
        assertTrue(solo.searchText("Score"));
        for (int i = 1; i <= 10; i++) {
            assertTrue(solo.searchText("" + i));
        }
    }

    /**
     * Clear the leaderboard and also finish up all opened activities.
     */
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}