package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

//import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.Options;
import com.example.zootypers.ui.PreGameSelection;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * Testing the title page using android unit tests and Robotium tests.
 * 
 * (Black box testing of the title page UI.)
 * 
 * @author dyxliang
 *
 */
public class TitlePageTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button singlePlayerButton;
    private Button multiplayerButton;
    private Button leaderboardButton;
    private Button optionsButton;

    public TitlePageTest() {
        super(TitlePage.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

        //initialize the buttons for testing purposes
        singlePlayerButton = (Button) getActivity().findViewById
                (com.example.zootypers.R.id.single_player_button);
        multiplayerButton = (Button) getActivity().findViewById
                (com.example.zootypers.R.id.multiplayer_button);
        leaderboardButton = (Button) getActivity().findViewById
                (com.example.zootypers.R.id.leaderboard_button);
        optionsButton = (Button) getActivity().findViewById
                (com.example.zootypers.R.id.options_button);
    }

    /**
     * Test clicking the back button stays in title page screen.
     */
    @Test(timeout = TIMEOUT)
    public void testBackButtonStayInTitlePage() {
        solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
        solo.goBack();
        solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
    }

    /**
     * Tests the button 'Single Player' and see if it displays the right view.
     */
    @Test(timeout = TIMEOUT)
    public void testSinglePlayerButtonExistsAndDisplayCorrectText() {
        assertNotNull(singlePlayerButton);
        singlePlayerButton.getText().equals("Single Player");
    }

    /**
     * Tests the button 'Multiplayer' and see if it displays the right view.
     */
    @Test(timeout = TIMEOUT)
    public void testMultiPlayerButtonExistsAndDisplayCorrectText() {
        assertNotNull(multiplayerButton);
        multiplayerButton.getText().equals("Multiplayer");
    }

    /**
     * Tests the button 'Leaderboard' and see if it displays the right view.
     */
    @Test(timeout = TIMEOUT)
    public void testLeaderboardButtonExistsAndDisplayCorrectText() {
        assertNotNull(leaderboardButton);
        leaderboardButton.getText().equals("Leaderboard");
    }

    /**
     * Tests the button 'Options' and see if it displays the right view.
     */
    @Test(timeout = TIMEOUT)
    public void testOptionsButtonExistsAndDisplayCorrectText() {
        assertNotNull(optionsButton);
        optionsButton.getText().equals("Options");
    }

    /**
     * Tests the button 'Single Player' and see if it leads to the next page.
     */
    @Test(timeout = TIMEOUT)
    public void testSinglePlayerButtonAndScreenWorks() {
        solo.assertCurrentActivity("Check on the current activity.", TitlePage.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                singlePlayerButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.assertCurrentActivity("Check on the current activity.", PreGameSelection.class);
    }

    /**
     * Tests the button 'Multiplayer' and see that it works.
     */
    @Test(timeout = TIMEOUT)
    public void testMultiplayerButtonAndScreenWorks() {
        solo.assertCurrentActivity("Check on the current activity.", TitlePage.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiplayerButton.performClick();
            }
        });
        solo.sleep(1000);
    }

    /**
     * Tests the button 'Leaderboard' and see that it works.
     */
    @Test(timeout = TIMEOUT)
    public void testLeaderboardButtonAndScreenWorks() {
        solo.assertCurrentActivity("Check on the current activity.", TitlePage.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                leaderboardButton.performClick();
            }
        });
        solo.sleep(1000);
        //solo.assertCurrentActivity("Check on the current activity.", Leaderboard.class);
    }

    /**
     * Tests the button 'Options' and see that it works.
     */
    @Test(timeout = TIMEOUT)
    public void testOptionsButtonAndScreenWorks() {
        solo.assertCurrentActivity("Check on the current activity.", TitlePage.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                optionsButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.assertCurrentActivity("Check on the current activity.", Options.class);
    }

    /**
     * tear doen any opened activities
     */
    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
