package com.example.zootypers.test;

import java.util.List;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiPlayerModel;
import com.example.zootypers.ui.MultiPlayer;
import com.example.zootypers.ui.TitlePage;
import com.example.zootypers.util.InternalErrorException;
import com.example.zootypers.util.InternetConnectionException;
import com.jayway.android.robotium.solo.Solo;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Testing to see if the multiplayer feature works by matching against an opponents,
 * checking to see if they are playing a game, checking to see if the database stores players
 * trying to type correct and incorrect letters, testing when the player wins, loses and ties
 * a given game.
 * 
 * (White box testing since we looked at the Multiplayer model code).
 * 
 * @author dyxliang
 *
 */
public class MultiplayerModelTest extends ActivityInstrumentationTestCase2<TitlePage> {

    private Solo solo;
    private MultiPlayerModel model;
    private static final int TIMEOUT = 30000;
    private char[] lowChanceLetters = {'j', 'z', 'x', 'q', 'k', 'o'};
    private ParseObject match;
    private Button multiButton;
    // maximum number of words in wordLists on Parse database
    private static final int NUMOFWORDS = 709;
    private static boolean loginFlag = true;
    private static boolean quitGameFlag = true;
    private List<String> wordsList;

    public MultiplayerModelTest() {
        super(TitlePage.class);
    }

    @Override
    protected void setUp() throws Exception {
        //to tell the database this is a test
        /*Intent in = new Intent();
        in.putExtra("Testing", 1);
        setActivityIntent(in);*/

        solo = new Solo(getInstrumentation(), getActivity());
        multiButton = (Button) getActivity().
        findViewById(com.example.zootypers.R.id.multiplayer_button);

        //initial login for running all the multi-player tests (checking if logged in or not)
        if (loginFlag) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    multiButton.performClick();
                }
            });
            solo.sleep(1000);
            EditText username = (EditText) solo.getView(R.id.username_login_input);
            solo.enterText(username, "David");
            EditText password = (EditText) solo.getView(R.id.password_login_input);
            solo.enterText(password, "1234567");
            final Button loginButton = (Button) 
            solo.getView(com.example.zootypers.R.id.login_button);
            solo.sleep(1000);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loginButton.performClick();
                }
            });
            solo.sleep(1000);
            loginFlag = false;
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    multiButton.performClick();
                }
            });
            solo.sleep(1000);
        }
        
        //set up opponent and proceed to the tests
        setUpOpponent();
        solo.sleep(3000);
        final Button continueButton = (Button) 
        solo.getView(com.example.zootypers.R.id.continue_button);
        solo.sleep(1000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                continueButton.performClick();
            }
        });
        
        //wait for multiplayer activity to get the model
        solo.waitForActivity(MultiPlayer.class, 15000);
        model = ((MultiPlayer) solo.getCurrentActivity()).getModel();
        solo.sleep(5000);
        wordsList = model.getWordsList();
        solo.sleep(5000);
    }

    /**
     * Make sure that the words list is at the expected size when you set it;
     * also make sure that all the default values are correct. Also checking
     * to set user finish and see if opponent is finished or not.
     * @throws InternalErrorException 
     * @throws InternetConnectionException 
     */
    @Test(timeout = TIMEOUT)
    public void testMakingSureWordsListCorrectSize() 
    throws InternetConnectionException, InternalErrorException {
        int expected = 100;
        assertEquals(expected, wordsList.size());
        assertEquals(5, model.getWordsDisplayed().length);
        assertEquals(-1, model.getCurrWordIndex());
        assertEquals(-1, model.getCurrLetterIndex());
        assertFalse(model.isOpponentFinished());
        model.setUserFinish();
    }

    /**
     * Test typing a letter will change the indices of the model's word/letter.
     * @throws InternalErrorException 
     * @throws InternetConnectionException 
     */
    @Test(timeout = TIMEOUT) @Suppress
    public void testTypingCorrectLetterChangeIndex() {
        String firstWord = wordsList.get(0);
        char firstChar = firstWord.charAt(0);
        sendKeys(firstChar - 68);
        assertEquals(1, model.getCurrLetterIndex());
    }
    
    /**
     * Test if typing a correct word would update the multiplayer score properly.
     */
    @Test(timeout = TIMEOUT)
    public void testTypingCorrectWordOnceUpdateScore() {
        //type a whole word and see if index sets back to -1
        String firstWord = wordsList.get(0);
        for (int i = 0; i < firstWord.length(); i++) {
            sendKeys(firstWord.charAt(i) - 68);
        }
        assertEquals(-1, model.getCurrLetterIndex());
        
        //get the score and check if properly updated
        TextView score = (TextView) solo.getView(com.example.zootypers.R.id.score);
        solo.sleep(1500);
        String scoreString = score.getText().toString();
        int expectedScore = firstWord.length();
        int actualScore = Integer.parseInt(scoreString);
        assertEquals(expectedScore, actualScore);
    }

    /**
     * Testing if typing an invalid letter would display the red error string.
     * @throws InternalErrorException 
     * @throws InternetConnectionException 
     */
    @Test(timeout = TIMEOUT) @Suppress
    public void testInvalidCharacterPressedDoesNotChangeIndex() {
        String firstWord = wordsList.get(0);
        char firstChar = firstWord.charAt(0);
        //try to type 6 letters and see if error string occurs
        for (char eachChar : lowChanceLetters) {
            if(firstChar != eachChar) {
                sendKeys(eachChar - 68);
                solo.searchText("Wrong Letter!");
                break;
            }
        }
        assertEquals(-1, model.getCurrLetterIndex());
    }

    /**
     * Testing manually making the player 1 win the game and get score methods.
     */
    @Test(timeout = TIMEOUT) @Suppress
    public void testWinningAMultiplayerGamePlay() {
        match.put("p1score", 0);
        match.put("p2score", 10);
        saveMatch();
        solo.sleep(3000);
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore > opponentScore);
    }

    /**
     * Testing manually making the players tie the game and get score methods.
     */
    @Test(timeout = TIMEOUT)
    public void testTieingAMultiplayerGamePlay() {
        match.put("p1score", 0);
        match.put("p2score", 0);
        saveMatch();
        solo.sleep(1000);
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore == opponentScore);
    }

    /**
     * Testing manually making the player 1 lose the game and get score methods.
     */
    @Test(timeout = TIMEOUT) @Suppress
    public void testLosingAMultiplayerGameWithModel() {
        match.put("p1score", 100);
        match.put("p2score", 5);
        saveMatch();
        solo.sleep(3000);
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore < opponentScore);
    }

    /**
     * Tests that the post game screen pops up after 1 min.
     */
    @Test(timeout = 90000) @Suppress
    public void testSimulatePlayingAOneMinuteGame() {
        boolean gameFlag = true;
        automateKeyboardTyping(0);
        while (gameFlag) {
            if (solo.searchText("New Game")) {
                gameFlag = false;
            }
        }
        quitGameFlag = false;
        assertTrue(solo.searchText("New Game"));
        assertTrue(solo.searchText("Main Menu"));
        assertTrue(solo.searchText("Your ad could be here!"));
    }
    
    /**
     * Uses the current words to figure out what to automatically type.
     */
    private void automateKeyboardTyping(int i) {
        String firstWord = wordsList.get(i);
        for (int j = 0; j < firstWord.length(); j++) {
            sendKeys(firstWord.charAt(j) - 68);
        }
    }
    
    /*
     * Set up the opponent bot for testing multiplayer.
     */
    private void setUpOpponent() {
        // Initialize the database
        Parse.initialize(this.getActivity(), 
        "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", "SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C");
        final int randy = (int) (Math.random() * (NUMOFWORDS));
        try {
            match = new ParseObject("Matches");
            match.put("p1name", "TestOpponent");
            match.put("p1animal", 2131296288);
            match.put("p1score", 0);
            match.put("p1finished", false);
            match.put("p2name", "");
            match.put("wordIndex", randy);
            match.save();
        } catch (ParseException e) {
        	e.fillInStackTrace();
            Log.e("setUp Opponent", "error in setting up opponent");
        }
    }

    /*
     * Make it so that the opponent is set to finish the match knows to display final scores.
     */
    private void setOpponentFinished() {
        match.put("p1finished", true);
        try {
            match.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * Make it so that the myself is set to finish the match knows to display final scores.
     */
    private void setMyselfFinished() {
        match.put("p2finished", true);
        try {
            match.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * Delete a match after testing.
     */
    private void deleteThisMatch() {
        try {
            match.delete();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * Save the match results.
     */
    private void saveMatch() {
        try {
            match.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /*
     * Quit the game and reset values to default.
     */
    private void quitGame() {
        final View quitButton = solo.getView(com.example.zootypers.R.id.quit_button);
        solo.sleep(3000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                quitButton.performClick();
            }
        });
    }

    @Override
    protected void tearDown() throws Exception {
        setMyselfFinished();
        setOpponentFinished();
        deleteThisMatch();
        if (quitGameFlag) {
            quitGame();
        }
        solo.sleep(1500);
        solo.finishOpenedActivities();
    }
}
