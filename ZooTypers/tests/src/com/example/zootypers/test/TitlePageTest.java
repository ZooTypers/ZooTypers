package com.example.zootypers.test;

import com.example.zootypers.TitlePage;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * Testing the title page using android unit tests and robotium UI testing.
 * 
 * Black box test.
 * 
 * @author dyxliang
 *
 */

public class TitlePageTest extends ActivityInstrumentationTestCase2<TitlePage> {
	
	private Solo solo;
	
	public TitlePageTest() {
		//super("com.example.zootypers", TitlePage.class);
		super(TitlePage.class);
	}

	//@Before
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testBackButtonStayInTitlePage() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.goBack();
		solo.goBack();
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}
	
	public void testSearchAllButtonsExist() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		assertTrue(solo.searchButton("Single Player"));
		assertTrue(solo.searchButton("Multiplayer"));
		assertTrue(solo.searchButton("Leaderboard"));
		assertTrue(solo.searchButton("Options"));
		assertFalse(solo.searchButton("WRONG_BUTTON"));
	}

	public void testSinglePlayerButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Single Player");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}
	
	public void testMultiplayerButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Multiplayer");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}
	
	public void testLeaderboardButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Leaderboard");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}
	
	public void testOptionsButtonWorks() {
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
		solo.clickOnButton("Options");
		solo.goBack();
		solo.assertCurrentActivity("Check on title page activity.", TitlePage.class);
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

    @SmallTest
    public void testMultiPlayerButtonExists() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.multiplayer_button));
    }
    
    @SmallTest
    public void testLeaderboardButtonExists() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.leaderboard_button));
    }
    
    @SmallTest
    public void testOptionsButtonExists() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.options_button));
    }

    @SmallTest
    public void testSinglePlayerButtonDisplayCorrectText() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.single_player_button));
        Button singlePlayerButton = (Button) getActivity().findViewById(com.example.zootypers.R.id.single_player_button);
        singlePlayerButton.getText().equals("Single Player");
    }
}
