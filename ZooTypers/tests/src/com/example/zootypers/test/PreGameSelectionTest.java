package com.example.zootypers.test;

import org.junit.Test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.zootypers.ui.PreGameSelection;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

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
	
	@SuppressLint("NewApi")
    public PreGameSelectionTest() {
		super(PreGameSelection.class);
	}


	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    screenWidth = display.getWidth(); 
	    screenHeight = display.getHeight();
	}

    /**
     * Test to see if all the buttons are in view.
     */
	@Test(timeout = TIMEOUT)
	public void testCorrectButtonAndTextDisplayed() {
		 assertTrue(solo.searchButton("Easy"));
		 assertTrue(solo.searchButton("Medium"));
		 assertTrue(solo.searchButton("Hard"));
		 assertTrue(solo.searchButton("Main Menu"));
		 assertTrue(solo.searchButton("Continue"));
		 assertFalse(solo.searchButton("WRONG_BUTTON"));
	}

    /**
     * Tests to see if 'Easy' can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectingEasyDifficultyButton() {
		solo.clickOnButton("Easy");
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.easy_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if 'Medium' can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectingMediumDifficultyButton() {
		solo.clickOnButton("Medium");
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.medium_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if 'Hard' can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectingHardDifficultyButton() {
		solo.clickOnButton("Hard");
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.hard_difficulty_button);
		View actual = getActivity().getDiffView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if Elelphant avatar can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectingElephantButtonWorks() {
		solo.waitForView(ImageButton.class);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.elephant_button);
		solo.clickOnView(imageButton);
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.elephant_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if Giraffe avatar can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectionGiraffeButtonWorks() {
		solo.waitForView(ImageButton.class);
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.giraffe_button);
		solo.clickOnView(imageButton);
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.giraffe_button);
		View actual = getActivity().getAnimalView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if Kangaroo avatar can be pushed. This requires Robotium to automatically swipe to the right
     * to access Kangaroo avatar.
     */
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

    /**
     * Commented out because it is in the same testing domain as testSelectionKangarooButtonWorks method.
     */
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

    /**
     * Tests to see if Grassland terrain can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testSelectingGrasslandBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG1_button);
		solo.clickOnView(imageButton);
		View expected = solo.getView(com.example.zootypers.R.id.BG1_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if Rainbow terrain can be pushed.
     */
	@Test(timeout = TIMEOUT)
	public void testingSelectingRainbowBackground() {
		ImageButton imageButton = (ImageButton) solo.getView(com.example.zootypers.R.id.BG2_button);
		solo.clickOnView(imageButton);
		solo.sleep(1000);
		View expected = solo.getView(com.example.zootypers.R.id.BG2_button);
		View actual = getActivity().getBackgroundView();
		assertEquals(expected, actual);
	}

    /**
     * Tests to see if we can go back to Main Menu with the button 'Main Menu'.
     */
	@Test(timeout = TIMEOUT)
	public void testingSelectingMainMenuButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
		assertTrue(solo.searchButton("Main Menu"));
		solo.clickOnButton("Main Menu");
		solo.sleep(1000);
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
		solo.goBack();
		solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
	}

    /**
     * Tests to see if the button 'Continue' works with the selected options.
     */
	@Test(timeout = TIMEOUT)
	public void testingSelectingContinueButton() {
		solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
	    Button continueButton = (Button) solo.getView(com.example.zootypers.R.id.continue_button);
		assertTrue(solo.searchButton("Continue"));
	    solo.clickOnView(continueButton);
	    solo.sleep(1000);
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.sleep(1000);
        solo.clickOnButton("Main Menu");
        solo.sleep(1000);
        solo.searchButton("Single Player");
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
