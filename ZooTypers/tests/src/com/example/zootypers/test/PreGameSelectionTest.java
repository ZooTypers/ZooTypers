package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.zootypers.ui.PreGameSelection;
import com.example.zootypers.ui.SinglePlayer;
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
    private static final int TIMEOUT = 10000;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;
    private Button menuButton;
    private Button continueButton;

    public PreGameSelectionTest() {
        super(PreGameSelection.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        easyButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.easy_difficulty_button);
        mediumButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.medium_difficulty_button);
        hardButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.hard_difficulty_button);
        menuButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.menu_button);
        continueButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.continue_button);
    }

    /**
     * Test to see if all the buttons exist and have correct text.
     */
    @Test(timeout = TIMEOUT)
    public void testCorrectButtonAndTextDisplayed() {
        assertNotNull(easyButton);
        easyButton.getText().equals("Easy");

        assertNotNull(mediumButton);
        mediumButton.getText().equals("Medium");

        assertNotNull(hardButton);
        hardButton.getText().equals("Hard");

        assertNotNull(menuButton);
        menuButton.getText().equals("Main Menu");

        assertNotNull(continueButton);
        continueButton.getText().equals("Continue");
    }

    /**
     * Tests to see if 'Easy' can be pushed and saves the correct view.
     */
    @Test(timeout = TIMEOUT)
    public void testSelectingEasyDifficultyButton() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                easyButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.easy_difficulty_button);
        View actual = getActivity().getDiffView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if 'Medium' can be pushed and saves the correct view.
     */
    @Test(timeout = TIMEOUT)
    public void testSelectingMediumDifficultyButton() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mediumButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.medium_difficulty_button);
        View actual = getActivity().getDiffView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if 'Hard' can be pushed and saves the correct view.
     */
    @Test(timeout = TIMEOUT)
    public void testSelectingHardDifficultyButton() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hardButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.hard_difficulty_button);
        View actual = getActivity().getDiffView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if Elephant button view can be selected (full button is on the screen).
     */
    @Test(timeout = TIMEOUT)
    public void testSelectingElephantButtonWorks() {
        final ImageButton imageButton = (ImageButton) 
        solo.getView(com.example.zootypers.R.id.elephant_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.elephant_button);
        View actual = getActivity().getAnimalView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if Kangaroo button can be clicked. (half the button is off the screen).
     */
    @Test(timeout = TIMEOUT)
    public void testSelectionKangarooButtonWorks() {
        final ImageButton imageButton = (ImageButton) 
        solo.getView(com.example.zootypers.R.id.kangaroo_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.kangaroo_button);
        View actual = getActivity().getAnimalView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if Panda button view can be selected (full button is off the screen).
     */
    @Test(timeout = TIMEOUT)
    public void testSelectionPandaButtonWorks() {
        final ImageButton imageButton = (ImageButton) 
        solo.getView(com.example.zootypers.R.id.panda_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.panda_button);
        View actual = getActivity().getAnimalView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if Grassland terrain can be pushed and saves the correct view.
     */
    @Test(timeout = TIMEOUT)
    public void testSelectingGrasslandBackground() {
        final ImageButton imageButton = (ImageButton) 
        solo.getView(com.example.zootypers.R.id.BG1_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
            }
        });
        solo.sleep(1000);
        View expected = solo.getView(com.example.zootypers.R.id.BG1_button);
        View actual = getActivity().getBackgroundView();
        assertEquals(expected, actual);
    }

    /**
     * Tests to see if Rainbow terrain can be pushed and saves the correct view.
     */
    @Test(timeout = TIMEOUT)
    public void testingSelectingRainbowBackground() {
        final ImageButton imageButton = (ImageButton) 
        solo.getView(com.example.zootypers.R.id.BG2_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageButton.performClick();
            }
        });
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.goBack();
        solo.assertCurrentActivity("Check on current page activity.", TitlePage.class);
    }

    /**
     * Tests to see if the button 'Continue' works with the selected options.
     */
    @Test(timeout = TIMEOUT)
    public void testingSelectingContinueButton() {
        solo.assertCurrentActivity("Check on current page activity.", PreGameSelection.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                continueButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.assertCurrentActivity("Check on current page activity.", SinglePlayer.class);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
