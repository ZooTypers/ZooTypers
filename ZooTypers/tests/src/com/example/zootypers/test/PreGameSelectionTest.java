package com.example.zootypers.test;

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
 * Testing the Pre-Game Selection screen for the game.
 * 
 * White box test.
 * 
 * @author dyxliang
 *
 */

public class PreGameSelectionTest extends ActivityInstrumentationTestCase2<PreGameSelection> {
	
	private Solo solo;
	private int screenWidth;
	private int screenHeight;
	private Display display;
	
	public PreGameSelectionTest() {
		super(PreGameSelection.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    screenWidth = display.getWidth(); 
	    screenHeight = display.getHeight();
	}
	
	public void testCorrectButtonAndTextDisplayed() {
		 assertTrue(solo.searchButton("Easy"));
		 assertTrue(solo.searchButton("Medium"));
		 assertTrue(solo.searchButton("Hard"));
		 assertTrue(solo.searchButton("Main Menu"));
		 assertTrue(solo.searchButton("Continue"));
		 assertFalse(solo.searchButton("WRONG_BUTTON"));
	}
	
	public void testSelectingEasyDifficultyButton() {
		solo.clickOnButton("Easy");
		View expected = solo.getView(com.example.zootypers.R.id.easy_difficulty_button);
		//solo.clickOnView(expected);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
		
		//ColorDrawable expectedColor = (ColorDrawable) expected.getBackground();
		//int buttonColorValue = expectedColor.getColor();
		//View actual = getActivity().findViewById(R.id.easy_difficulty_button);
		//boolean actual = solo.isToggleButtonChecked(0);
		//assertEquals("Easy button is OFF", true, actual);
		
		// solo.clickOnButton("Medium");
		// solo.searchToggleButton("Medium");
		// solo.clickOnButton("Hard");
		// solo.searchToggleButton("Hard");
	}
	
	public void testSelectingMediumDifficultyButton() {
		solo.clickOnButton("Medium");
		View expected = solo.getView(com.example.zootypers.R.id.medium_difficulty_button);
		//solo.clickOnView(expected);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}
	
	public void testSelectingHardDifficultyButton() {
		solo.clickOnButton("Hard");
		View expected = solo.getView(com.example.zootypers.R.id.hard_difficulty_button);
		//solo.clickOnView(expected);
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
	
//	public void testSelectionPandaButtonWorks() {
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
//		
//		solo.waitForView(ImageButton.class);
//		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.panda_button);
//		solo.clickOnView(imageButton);
//		View expected = solo.getView(com.example.zootypers.R.id.panda_button);
//		View actual = getActivity().getAnimalView();
//		assertEquals(expected, actual);
//	}
	
	public void testSelectionGiraffeButtonWorks() {
		solo.waitForView(ImageButton.class);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.giraffe_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.giraffe_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}
	
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
	
	public void testSelectionKangarooButtonWorks() {
//		int expectedValue = 0; // 0=VISIBLE, 4=INVISIBLE, 8=GONE
//		assertEquals("Message when assert failed", expectedValue, solo.getView(com.example.zootypers.R.id.kangaroo_button).getVisibility());
//		
//		int[] location = new int[2]; // this will hold the x and y position
//		// retrieve coordinates
//		solo.getView(com.example.zootypers.R.id.kangaroo_button).getLocationOnScreen(location);
//		// and check if possitive or whatever fits your needs
//		assertTrue("Message when assert failed", location[0] >= 0 && location[1] >= 0);
		
		//Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		//Display d =((WindowManager)activity.getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    //int screenWidth = display.getWidth(); 
	    //int screenHeight = display.getHeight();
	    //int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
	    //int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
	    
//	    int fromX, toX, fromY, toY = 0;
//	    fromX = screenWidth/2 - (screenHeight/3);
//	    toX = screenWidth/2 + (screenHeight/3);
//	    fromY = (screenHeight/2);
//	    toY = (screenHeight/2);
//	    //int scroll_time = 10000;             
//	    //solo.sleep(5000);
//	        // Drag UP  
//	    solo.drag(fromX, toX, fromY, toY, 10);
	    //Log.d(TAG, "Drag 1");
	        // here default origin (x,y = 0,0) is left upper corner
	    
	    solo.drag(screenWidth-10, 10, screenHeight/2, screenHeight/2, 40);
	    
//		int fromX = (screenWidth/2) - (screenWidth/2);
//		int toX = (screenWidth/2) + (screenWidth/2);
//		int fromY = screenHeight/2;
//		int toY = screenHeight/2;
		//solo.drag(fromX, toX, fromY, toY, 1);
		solo.sleep(1000);
		//Log.d(TAG,"Scroll Left");
	    
//	    solo.scrollToSide(screenWidth/2);
//	    solo.sleep(3000);
		
		solo.waitForView(ImageButton.class);
		solo.scrollToSide(5);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.kangaroo_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.kangaroo_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}
	
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
	
	public void testSelectingGrasslandBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG1_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.BG1_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}
	
	public void testingSelectingRainbowBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG2_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.BG2_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}
	
	public void testingSelectingMainMenuButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
		assertTrue(solo.searchButton("Main Menu"));
		solo.clickOnButton("Main Menu");
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
		solo.goBack();
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
	}
	
	public void testingSelectingContinueButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
		assertTrue(solo.searchButton("Continue"));
		solo.clickOnButton("Continue");
		solo.assertCurrentActivity("Check on current page activity.", SinglePlayer.class);
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
