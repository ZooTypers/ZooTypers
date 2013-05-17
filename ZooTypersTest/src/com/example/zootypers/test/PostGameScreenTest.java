//package com.example.zootypers.test;
//
//import org.junit.Test;
//
//import com.example.zootypers.PostGameScreen;
//import com.example.zootypers.TitlePage;
//import com.jayway.android.robotium.solo.Solo;
//import android.test.ActivityInstrumentationTestCase2;
//
///**
// * Testing the post game screen for the game to ensure the information is there.
// * 
// * (Black box test just to test the UI.)
// * 
// * @author dyxliang
// *
// */
//
//public class PostGameScreenTest extends ActivityInstrumentationTestCase2<TitlePage> {
//	
//	private Solo solo;
//	private static final int TIMEOUT = 10000;
//	
//	public PostGameScreenTest() {
//		super(TitlePage.class);
//	}
//
//	protected void setUp() throws Exception {
//		solo = new Solo(getInstrumentation(), getActivity());
//		solo.clickOnButton("Single Player");
//		solo.clickOnButton("Continue");
//		solo.sleep(1000);
//	}
//	
//	@Test(timeout = TIMEOUT)
//	public void testTheMainMenuButtonWorks() {
//		solo.assertCurrentActivity("Check on title page activity.", PostGameScreen.class);
//		assertTrue(solo.searchButton("Main Menu"));
//		solo.clickOnButton("Main Menu");
//		solo.goBack();
//	}
//	
//	@Test(timeout = TIMEOUT)
//	public void testTheNewGameButtonWorks() {
//		solo.assertCurrentActivity("Check on title page activity.", PostGameScreen.class);
//		assertTrue(solo.searchButton("New Game"));
//		solo.clickOnButton("New Game");
//		solo.goBack();
//	}
//	
//	@Test(timeout = TIMEOUT)
//	public void testAllThePostGameTextsExist() {
//		assertTrue(solo.searchText("Your ad could be here!"));
//		assertTrue(solo.searchText("Final Score: "));
//		assertTrue(solo.searchText("0"));
//	}
//	
//	protected void tearDown() throws Exception {
//		solo.finishOpenedActivities();
//	}
//
//}
