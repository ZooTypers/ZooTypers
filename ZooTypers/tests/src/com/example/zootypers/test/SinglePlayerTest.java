package com.example.zootypers.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.zootypers.PreGameSelection;
import com.example.zootypers.R;
import com.example.zootypers.SinglePlayer;
import com.example.zootypers.SinglePlayerModel;
import com.jayway.android.robotium.solo.Solo;

/**
 * Testing the single player game mode using robotium testing framework.
 * Checking to see if typing a invalid letter and valid letter works. We are
 * also checking to see if typing a complete word works and if the scores are 
 * updated properly; the pause screen and other aspect of single player will 
 * be tested as well.
 * 
 * (White box test since we looked at the Single Player code as well as the UI.)
 * 
 * @author oaknguyen & dyxliang
 *
 */

public class SinglePlayerTest extends  ActivityInstrumentationTestCase2<PreGameSelection> {

    private Solo solo;
    private char[] lowChanceLetters = {'j', 'z', 'x', 'q', 'k', 'o'};
    private static final int TIMEOUT = 30000;
    
    public SinglePlayerTest() {
        super(PreGameSelection.class);
    }

    protected void setUp() throws Exception {
        this.setActivityInitialTouchMode(false);
        solo = new Solo(getInstrumentation(), getActivity());
        solo.clickOnButton("Continue");
    }
    
    private void automateKeyboardTyping() {
        List<TextView> textList = getWordsPresented(solo);
        Random randy = new Random();
        int randomValue = randy.nextInt(5);
        TextView currTextView = textList.get(randomValue);
        String currWord = currTextView.getText().toString();
        for (int i = 0; i < currWord.length(); i++) {
            char c = currWord.charAt(i);
            sendKeys(c - 68);
        }
    }
    
    private static List<TextView> getWordsPresented(Solo solo){
        solo.sleep(1000);
        List<TextView> retVal = new ArrayList<TextView>();
        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word0)));
        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word1)));
        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word2)));
        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word3)));
        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word4)));
        return retVal;
    }
    
    private void goBackToMainMenu() {
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.sleep(5000);
        solo.clickOnButton("Main Menu");
        solo.sleep(3000);
        solo.searchButton("Single Player");
        solo.sleep(1000);
    }
    
    @Test(timeout = TIMEOUT)
    public void testFiveWordsPresent(){
        List<TextView> views = getWordsPresented(solo);
        solo.sleep(3000);
        for(int i = 0; i < 5; i++){
            assertTrue(views.get(i).getText().length() > 0);
        }
    }
    
    @Test(timeout = TIMEOUT)
    public void testInvalidCharacterPressed(){
        List<TextView> views = getWordsPresented(solo);
        solo.sleep(1000);
        String firstLetters = "";
        for(TextView s : views){
            firstLetters += s.getText().charAt(0);
        }
        solo.sleep(1000);
        for(char c : lowChanceLetters){
            if(firstLetters.indexOf(c) < 0 ){
                sendKeys(c - 68);
                assertTrue(solo.searchText("Invalid Letter Typed"));
            }
        }
        solo.sleep(1000);
        goBackToMainMenu();
    }
    
    @Test(timeout = 60000)
    public void testCorrectCharacterPressed(){
        List<TextView> views = getWordsPresented(solo);
        TextView s = views.get(0);
        solo.sleep(5000);
        sendKeys(s.getText().charAt(0) - 68);
        views = getWordsPresented(solo);
        solo.sleep(3000);
        CharSequence word = views.get(0).getText();
        solo.sleep(1000);
        SpannableString spanString = new SpannableString(word);
        ForegroundColorSpan[] spans = spanString.getSpans(0, spanString.length(), ForegroundColorSpan.class);
        solo.sleep(3000);
        assertTrue(spans.length > 0);
    }
    
    @Test(timeout = TIMEOUT)
    public void testChangeAWordWhenFinished(){
        List<TextView> textList = getWordsPresented(solo);
        TextView currTextView = textList.get(0);
        String currWord = currTextView.getText().toString();
        //Log.v("current-word", currWord);
        for (int i = 0; i < currWord.length(); i++) {
            char c = currWord.charAt(i);
            sendKeys(c - 68);
            //Log.v("current-letter", Character.toString(c));
        }
        textList = getWordsPresented(solo);
        assertTrue(textList.get(0).getText().toString() != currWord);
        goBackToMainMenu();
    }
    
    @Test(timeout = 60000)
    public void testTypingCorrectWordsThreeTimesUpdateScore() {
        int expectedScore = 0;
        int actualScore = 0;
        for (int i = 0; i < 3; i++) {
            List<TextView> textList = getWordsPresented(solo);
            TextView currTextView = textList.get(0);
            String currWord = currTextView.getText().toString();
            //Log.v("current-word", currWord);
            for (int j = 0; j < currWord.length(); j++) {
                char c = currWord.charAt(j);
                sendKeys(c - 68);               
                //Log.v("current-letter", Character.toString(c));

            }
            TextView score = (TextView) solo.getCurrentActivity().findViewById(R.id.score);
            solo.sleep(1000);
            String scoreString = score.getText().toString();
            solo.sleep(1000);
            expectedScore += currWord.length();
            actualScore = Integer.parseInt(scoreString);

        }
        assertEquals(expectedScore, actualScore);
    }
    
    @Test(timeout = 70000)
    public void testSimulatePlayingAOneMinuteGame() {
        boolean gameFlag = true;
        while (gameFlag) {
            automateKeyboardTyping();
            if (solo.searchButton("New Game") == true) {
                gameFlag = false;
            }
        }
        assertTrue(solo.searchButton("New Game"));
        assertTrue(solo.searchButton("Main Menu"));
        assertTrue(solo.searchText("Your ad could be here!"));
    }
    
    @Test(timeout = TIMEOUT)
    public void testTheKeyboardButtonWorks() {
         Button keyboardButton = (Button) solo.getView(com.example.zootypers.R.id.keyboard_open_button);
         solo.clickOnView(keyboardButton);
         solo.sleep(3000);
         solo.searchButton("Keyboard");
    }
    
    @Test(timeout = TIMEOUT)
    public void testPauseButtonWorksProperly() {
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.sleep(3000);
        solo.clickOnButton("Continue");
    }
    
    @Test(timeout = TIMEOUT)
    public void testPauseButtonCanGoToMainMenu() {
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.sleep(3000);
        solo.clickOnButton("Main Menu");
        solo.sleep(3000);
        solo.searchButton("Single Player");
    }
    
    @Test(timeout = TIMEOUT)
    public void testPauseButtonCanStartNewGame() {
        Button pauseButton = (Button) solo.getView(com.example.zootypers.R.id.pause_button);
        solo.clickOnView(pauseButton);
        solo.sleep(3000);
        solo.clickOnButton("New Game");
        solo.sleep(3000);
        solo.searchButton("Continue");
    }
    
    @Test(timeout = TIMEOUT)
	public void testModelInitial() {
		solo.sleep(1000);
		SinglePlayerModel model = ((SinglePlayer)solo.getCurrentActivity()).getModel();
		assertEquals(0, model.getScore());
		assertEquals(5, model.getWordsDisplayed().length);
		assertEquals(-1, model.getCurrWordIndex());
		assertEquals(-1, model.getCurrLetterIndex());
	}
	
    @Test(timeout = TIMEOUT)
	public void testModelAfterOneCharTyped() {
		solo.sleep(1000);
		SinglePlayerModel model = ((SinglePlayer)solo.getCurrentActivity()).getModel();
		List<TextView> views = getWordsPresented(solo);
		TextView s = views.get(0);
		solo.sleep(1000);
		sendKeys(s.getText().charAt(0) - 68);
		assertEquals(0, model.getScore());
		assertEquals(5, model.getWordsDisplayed().length);
		assertEquals(0, model.getCurrWordIndex());
		assertEquals(1, model.getCurrLetterIndex());
	}
	
    @Test(timeout = TIMEOUT)
	public void testModelAfterOneWordTyped() {
		solo.sleep(1000);
		SinglePlayerModel model = ((SinglePlayer)solo.getCurrentActivity()).getModel();
		List<TextView> views = getWordsPresented(solo);
		TextView s = views.get(0);
		solo.sleep(1000);
		for (int i = 0; i < s.getText().toString().length(); i++) {
			char c = s.getText().charAt(i);
			sendKeys(c - 68);
			Log.v("current-letter", Character.toString(c));
		}
		assertEquals(s.getText().length(), model.getScore());
		assertEquals(5, model.getWordsDisplayed().length);
		assertEquals(-1, model.getCurrWordIndex());
		assertEquals(-1, model.getCurrLetterIndex());
	}
	
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
