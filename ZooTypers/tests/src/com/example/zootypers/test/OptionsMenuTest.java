package com.example.zootypers.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.Button;

import com.example.zootypers.ui.Leaderboard;
import com.example.zootypers.ui.Options;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;

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

    @Test(timeout = TIMEOUT)
    public void testClearingSinglePlayerLeaderboard() {
        final Button clearSingleButton = (Button) solo.getView(com.example.zootypers.R.id.clearSingle);
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
    
    @Test(timeout = TIMEOUT)
    public void testClearingMultiPlayerLeaderboard() {
        final Button clearMultiButton = (Button) solo.getView(com.example.zootypers.R.id.clearMulti);
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
    
    @Test(timeout = TIMEOUT)
    public void testGoingBackToMainMenu() {
        final Button menuButton = (Button) solo.getView(com.example.zootypers.R.id.main_menu_options);
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
