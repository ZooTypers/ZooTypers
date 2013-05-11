package com.example.zootypers.test;

import com.example.zootypers.TitlePage;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * Testing the title page using android unit tests.
 * 
 * @author dyxliang
 *
 */

public class TitlePageTest extends ActivityInstrumentationTestCase2<TitlePage> {

    public TitlePageTest() {
        super(TitlePage.class);
    }
    
    @SmallTest
    public void testSinglePlayerButtonExists() {
        assertNotNull(getActivity().findViewById(com.example.zootypers.R.id.single_player_button));
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
