package com.example.zootypers.test;

import com.example.zootypers.PostGameScreen;
import com.example.zootypers.TitlePage;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Testing the post game screen for the game.
 * 
 * Black box test.
 * 
 * @author dyxliang
 *
 */

public class PostGameScreenTest extends ActivityInstrumentationTestCase2<TitlePage> {
	
	private Solo solo;
	
	public PostGameScreenTest() {
		super(TitlePage.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("Single Player");
		solo.clickOnButton("Continue");
		solo.sleep(1000);
	}
	
//	public void testTheMainMenuButtonWorks() {
//		solo.assertCurrentActivity("Check on title page activity.", PostGameScreen.class);
//		assertTrue(solo.searchButton("Main Menu"));
//		solo.clickOnButton("Main Menu");
//		solo.goBack();
//	}
//	
//	public void testTheNewGameButtonWorks() {
//		solo.assertCurrentActivity("Check on title page activity.", PostGameScreen.class);
//		assertTrue(solo.searchButton("New Game"));
//		solo.clickOnButton("New Game");
//		solo.goBack();
//	}
//	
//	public void testAllThePostGameTextsExist() {
//		assertTrue(solo.searchText("Your ad could be here!"));
//		assertTrue(solo.searchText("Final Score: "));
//		assertTrue(solo.searchText("0"));
//	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
