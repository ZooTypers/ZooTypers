package com.example.zootypers.test;

import org.junit.Test;

import com.example.zootypers.*;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Testing the Pre-Game Selection screen by checking and clicking all the
 * buttons, selecting all the animal choices, selecting all the background,
 * and also checking it goes to the single player correctly.
 * 
 * (Black box testing of the pre-game selection UI.)
 * 
 * @author dyxliang
 *
 */

public class PreGameSelectionTest extends ActivityInstrumentationTestCase2<PreGameSelection> {
	
	private Solo solo;
	private int screenWidth;
	private int screenHeight;
	private Display display;
	private static final int TIMEOUT = 10000;
	
	public PreGameSelectionTest() {
		super(PreGameSelection.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    screenWidth = display.getWidth(); 
	    screenHeight = display.getHeight();
	}
	
	@Test(timeout = TIMEOUT)
	public void testCorrectButtonAndTextDisplayed() {
		 assertTrue(solo.searchButton("Easy"));
		 assertTrue(solo.searchButton("Medium"));
		 assertTrue(solo.searchButton("Hard"));
		 assertTrue(solo.searchButton("Main Menu"));
		 assertTrue(solo.searchButton("Continue"));
		 assertFalse(solo.searchButton("WRONG_BUTTON"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSelectingEasyDifficultyButton() {
		solo.clickOnButton("Easy");
		View expected = solo.getView(com.example.zootypers.R.id.easy_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testSelectingMediumDifficultyButton() {
		solo.clickOnButton("Medium");
		View expected = solo.getView(com.example.zootypers.R.id.medium_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testSelectingHardDifficultyButton() {
		solo.clickOnButton("Hard");
		View expected = solo.getView(com.example.zootypers.R.id.hard_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}
	
	public void testSelectingElephantButtonWorks() {
		solo.waitForView(ImageButton.class);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.elephant_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.elephant_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testSelectionGiraffeButtonWorks() {
		solo.waitForView(ImageButton.class);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.giraffe_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.giraffe_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
    public void testSelectionKangarooButtonWorks() {
        int expectedValue = 0; // 0=VISIBLE, 4=INVISIBLE, 8=GONE
        assertEquals("The button is out of view.", expectedValue, solo.getView(com.example.zootypers.R.id.kangaroo_button).getVisibility());
        
        int[] location = new int[2]; // this will hold the x and y position
        solo.getView(com.example.zootypers.R.id.kangaroo_button).getLocationOnScreen(location);
        assertTrue("The button is out of view.", location[0] >= 0 && location[1] >= 0);
        
        solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
        solo.sleep(1000);
        solo.waitForView(ImageButton.class);
        solo.scrollToSide(5);
        ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.kangaroo_button);
        solo.clickOnView(imageButton);
        View expected = solo.getView(com.example.zootypers.R.id.kangaroo_button);
        View actual = getActivity().getAnimalView();
        assertEquals(expected, actual);
    }
	
//	@Test(timeout = TIMEOUT)
//	public void testSelectionTurtleButtonWorks() {
//		
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//		
//		solo.waitForView(ImageButton.class);
//		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.turtle_button);
//		solo.clickOnView(imageButton);
//		View expected = solo.getView(com.example.zootypers.R.id.turtle_button);
//		View actual = getActivity().getAnimalView();
//		assertEquals(expected, actual);
//	}
//	
//	@Test(timeout = TIMEOUT)
//	public void testSelectionLionButtonWorks() {
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//		
//		solo.waitForView(ImageButton.class);
//		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.lion_button);
//		solo.clickOnView(imageButton);
//		View expected = solo.getView(com.example.zootypers.R.id.lion_button);
//		View actual = getActivity().getAnimalView();
//		assertEquals(expected, actual);
//	}
//
//	@Test(timeout = TIMEOUT)
//  public void testSelectionPandaButtonWorks() {
//      solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//      solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//      solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//      
//      solo.waitForView(ImageButton.class);
//      ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.panda_button);
//      solo.clickOnView(imageButton);
//      View expected = solo.getView(com.example.zootypers.R.id.panda_button);
//      View actual = getActivity().getAnimalView();
//      assertEquals(expected, actual);
//  }
//	
//	public void testSelectionMonkeyButtonWorks() {
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-50, 50, screenHeight/2, screenHeight/2, 40);
//		
//		solo.waitForView(ImageButton.class);
//		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.monkey_button);
//		solo.clickOnView(imageButton);
//		View expected = solo.getView(com.example.zootypers.R.id.monkey_button);
//		View actual = getActivity().getAnimalView();
//		assertEquals(expected, actual);
//	}
//	
//	public void testSelectionPenguinButtonWorks() {
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//		
//		solo.waitForView(ImageButton.class);
//		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.penguin_button);
//		solo.clickOnView(imageButton);
//		View expected = solo.getView(com.example.zootypers.R.id.penguin_button);
//		View actual = getActivity().getAnimalView();
//		assertEquals(expected, actual);
//	}
	
	@Test(timeout = TIMEOUT)
	public void testSelectingGrasslandBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG1_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.BG1_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testingSelectingRainbowBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG2_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.BG2_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}
	
	@Test(timeout = TIMEOUT)
	public void testingSelectingMainMenuButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
		assertTrue(solo.searchButton("Main Menu"));
		solo.clickOnButton("Main Menu");
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
		solo.goBack();
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
	}
	
	@Test(timeout = TIMEOUT)
	public void testingSelectingContinueButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
	    Button continueButton = (Button) solo.getView(com.example.zootypers.R.id.continue_button);
		assertTrue(solo.searchButton("Continue"));
	    solo.clickOnView(continueButton);
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.clickOnButton("Main Menu");
        solo.searchButton("Single Player");
	    solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
