package com.example.zootypers.test;

import org.junit.Test;

import android.annotation.SuppressLint;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

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
	
	@SuppressLint("NewApi")
    public TitlePageTest() {
		super(TitlePage.class);
	}
	
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

    /**
     * Tests the back button of android devices to not go to any other screen.
     */
	@Test(timeout = TIMEOUT)
	public void testBackButtonStayInTitlePage() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.goBack();
		solo.goBack();
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}

    /**
     * Tests the title page to have all the required buttons.
     */
	@Test(timeout = TIMEOUT)
	public void testSearchAllButtonsExist() {
		solo.searchText("Single Player");
		solo.searchText("Multiplayer");
		solo.searchText("Leaderboard");
	}

    /**
     * Tests the button 'Single Player'.
     */
	@Test(timeout = TIMEOUT)
	public void testSinglePlayerButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Single Player");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}

    /**
     * Tests the button 'Multiplayer' and see that it works.
     */
	@Test(timeout = TIMEOUT)
	public void testMultiplayerButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Multiplayer");
		Button xButton = (Button) solo.getView(com.example.zootypers.R.id.exit_login_button);
		solo.clickOnView(xButton);
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}

    /**
     * Tests the button 'Leaderboard' and see that it works.
     */
	@Test(timeout = TIMEOUT)
	public void testLeaderboardButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Leaderboard");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}

    /**
     * Tests the button 'Options' and see that it works.
     */
	@Test(timeout = TIMEOUT)
	public void testOptionsButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Options");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}

    /**
     * Tests the button 'Single Player' and see if it displays the right view.
     */
   @Test(timeout = TIMEOUT)
    public void testSinglePlayerButtonExistsAndDisplayCorrectText() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.single_player_button));
        Button singlePlayerButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.single_player_button);
        singlePlayerButton.getText().equals("Single Player");
    }

    /**
     * Tests the button 'Multiplayer' and see if it displays the right view.
     */
	@Test(timeout = TIMEOUT)
    public void testMultiPlayerButtonExistsAndDisplayCorrectText() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.multiplayer_button));
        Button multiPlayerButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.multiplayer_button);
        multiPlayerButton.getText().equals("Multiplayer");
    }

    /**
     * Tests the button 'Leaderboard' and see if it displays the right view.
     */
	@Test(timeout = TIMEOUT)
    public void testLeaderboardButtonExistsAndDisplayCorrectText() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.leaderboard_button));
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.single_player_button));
        Button leaderboardButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.single_player_button);
        leaderboardButton.getText().equals("Leaderboard");
    }

    /**
     * Tests the button 'Options' and see if it displays the right view.
     */
	@Test(timeout = TIMEOUT)
    public void testOptionsButtonExistsAndDisplayCorrectText() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.options_button));
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.single_player_button));
        Button optionsButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.single_player_button);
        optionsButton.getText().equals("Options");
	}
	
	protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
