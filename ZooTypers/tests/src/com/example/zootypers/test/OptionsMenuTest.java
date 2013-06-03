package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.Switch;

import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

/**
 * Testing the options menu by clicking on all the buttons and making sure they all work.
 * 
 * @author dyxliang
 *
 */

public class OptionsMenuTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private static final int TIMEOUT = 10000;
    private Button optionsButton;

    public OptionsMenuTest() {
        super(TitlePage.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        optionsButton = (Button) getActivity().findViewById
        (com.example.zootypers.R.id.options_button);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                optionsButton.performClick();
            }
        });
        solo.sleep(1500);
    }

    /**
     * Checking to see if you can clear single player leaderboard properly.
     */
    @Test(timeout = TIMEOUT)
    public void testClearingSinglePlayerLeaderboard() {
        final Button clearSingleButton = (Button) 
        solo.getView(com.example.zootypers.R.id.clearSingle);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clearSingleButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.searchText("Cleared Leaderboard");
    }

    /**
     * Checking to see if you can clear multiplayer leaderboard properly.
     */
    @Test(timeout = TIMEOUT)
    public void testClearingMultiPlayerLeaderboard() {
        final Button clearMultiButton = (Button) 
        solo.getView(com.example.zootypers.R.id.clearMulti);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clearMultiButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.searchText("Login");
    }
    
    /**
     * Checking to see if you can turn sound on and off properly.
     */
    @Test(timeout = TIMEOUT)
    public void testTogglingSoundOnAndOff() {
        final Switch bgmButton = (Switch) solo.getView(com.example.zootypers.R.id.bgm);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bgmButton.performClick();
            }
        });
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bgmButton.performClick();
            }
        });
        solo.sleep(1000);
    }
    
    /**
     * Checking to see if you can turn vibration on and off properly.
     */
    @Test(timeout = TIMEOUT)
    public void testTogglingVibrationOnAndOff() {
        final Switch vibrateButton = (Switch) solo.getView(com.example.zootypers.R.id.vibrate);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vibrateButton.performClick();
            }
        });
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vibrateButton.performClick();
            }
        });
        solo.sleep(1000);
    }

    /**
     * Checking to see if you can go back to menu from options properly.
     */
    @Test(timeout = TIMEOUT)
    public void testGoingBackToMainMenu() {
        final Button menuButton = (Button) 
        solo.getView(com.example.zootypers.R.id.main_menu_options);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuButton.performClick();
            }
        });
        solo.sleep(1000);
        solo.assertCurrentActivity("Check on the current activity.", TitlePage.class);
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
