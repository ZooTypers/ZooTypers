package com.example.zootypers.test;

import com.example.zootypers.PreGameSelection;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Testing the Pre-Game Selection screen for the game.
 * 
 * @author dyxliang
 *
 */

public class PreGameSelectionTest extends ActivityInstrumentationTestCase2<PreGameSelection> {
	
	private Solo solo;
	
	public PreGameSelectionTest() {
		super(PreGameSelection.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testMakeSureAvailableTextDisplayed() {
		solo.searchButton("Easy");
		solo.searchButton("Medium");
		solo.searchButton("Hard");
		solo.searchButton("Main Menu");
		solo.searchButton("Continue");
	}
	
	public void testSelectingDifficultyButtonToggles() {
		solo.clickOnButton("Easy");
		solo.searchToggleButton("Easy");
		solo.clickOnButton("Medium");
		solo.searchToggleButton("Medium");
		solo.clickOnButton("Hard");
		solo.searchToggleButton("Hard");
	}
	
	public void testSelectingElephant() {
//		ImageButton imageButton = (Image) solo.getView(R.id.of.imagebutton);
//		solo.clickOnView(imageButton);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
