package com.example.zootypers.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiPlayerModel;
import com.example.zootypers.ui.MultiPlayer;
import com.example.zootypers.ui.TitlePage;
import com.jayway.android.robotium.solo.Solo;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
	private static final int GAME_TIME = 63000;
    private char[] lowChanceLetters = {'j', 'z', 'x', 'q', 'k', 'o'};
	private ParseObject match;
	// maximum number of words in wordLists on Parse database
	private static final int NUMOFWORDS = 709;
	private static final int LIST_SIZE = 100;
    private static boolean loginFlag = true;
	
	public MultiplayerModelTest() {
		super(TitlePage.class);
	}
	
	protected void setUp() throws Exception {
	    model = new MultiPlayerModel(5, "Player", 1);
	    solo = new Solo(getInstrumentation(), getActivity());
	    
	    //initial login for running all the multiplayer tests
	    if (loginFlag) {
    	    solo.clickOnButton("Multiplayer");
    	    solo.sleep(1000);
    	    EditText username = (EditText) solo.getView(R.id.username_login_input);
    	    solo.enterText(username, "David");
    	    EditText password = (EditText) solo.getView(R.id.password_login_input);
    	    solo.enterText(password, "1234567");
    	    solo.clickOnButton("Login");
    	    solo.sleep(3000);
    	    loginFlag = false;
    	     
	    } else {
	        solo.clickOnButton("Multiplayer");
	        solo.sleep(1000);
	    }
	    
	    setUpOpponent();
        solo.sleep(3000);
        solo.clickOnButton("Continue");
	}
	
	/*
	 * set up the opponent bot for testing multiplayer
	 */
	public void setUpOpponent() {
	    // Initialize the database
        Parse.initialize(this.getActivity(), "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", "SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C");
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
            // TODO do something
        }
	}
	
	/*
	 * make it so that the opponent is set to finish the match knows to display final scores
	 */
	private void setOpponentFinished() {
	    match.put("p1finished", true);
	    try {
	        match.save();
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	/*
	 * make it so that the myself is set to finish the match knows to display final scores
	 */
	private void setMyselfFinished() {
	    match.put("p2finished", true);
	    try {
	        match.save();
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}
	
	/*
	 * delete a match after testing
	 */
	private void deleteThisMatch() {
	    try {
            match.delete();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/*
	 * save the match results
	 */
	private void saveMatch() {
        try {
            match.save();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	private static List<TextView> getWordsPresented(Solo solo){
        solo.sleep(3000);
	    List<TextView> retVal = new ArrayList<TextView>();
	    retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word0)));
	    retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word1)));
	    retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word2)));
	    retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word3)));
	    retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word4)));
        solo.sleep(3000);
	    return retVal;
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
	
	/*
	 * make sure that when you create a model, all the fields are at default values
	 */
	@Test(timeout = TIMEOUT)
	public void testInitialValues() {
	    assertEquals(5, model.getWordsDisplayed().length);
	    assertEquals(-1, model.getCurrWordIndex());
	    assertEquals(-1, model.getCurrLetterIndex());
	}
	
	/*
	 * check to see if the first 5 words are displayd in multiplayer screen
	 */
    @Test(timeout = TIMEOUT)
    public void testFiveWordsPresentInMulti(){
        List<TextView> views = getWordsPresented(solo);
        assertEquals(5, views.size());
        for(int i = 0; i < 5; i++){
            Log.v("words", views.get(i).getText().toString());
            int expectedLength = views.get(i).getText().toString().length();
            solo.sleep(1000);
            assertTrue(expectedLength > 0);
        }
    }
    
    /*
     * test if typing a correct word would update the multiplayer score
     */
    @Test(timeout = TIMEOUT)
    public void testTypingCorrectWordOnceUpdateScore() {
        int expectedScore = 0;
        int actualScore = 0;
        List<TextView> textList = getWordsPresented(solo);
        TextView currTextView = textList.get(0);
        String currWord = currTextView.getText().toString();
        for (int j = 0; j < currWord.length(); j++) {
            char c = currWord.charAt(j);
            sendKeys(c - 68);
        }
        TextView score = (TextView) solo.getCurrentActivity().findViewById(R.id.score);
        String scoreString = score.getText().toString();
        expectedScore += currWord.length();
        actualScore = Integer.parseInt(scoreString);
        assertEquals(expectedScore, actualScore);
    }
    
    /*
     * testing if typing an invalid letter would display the red error string
     */
    @Test(timeout = TIMEOUT)
    public void testInvalidCharacterPressed(){
        List<TextView> views = getWordsPresented(solo);
        solo.sleep(1000);
        String firstLetters = "";
        for(TextView s : views){
            firstLetters += s.getText().charAt(0);
        }
        solo.sleep(1000);
        for (char c : lowChanceLetters){
            if(firstLetters.indexOf(c) < 0 ){
                sendKeys(c - 68);
                assertTrue(solo.searchText("Invalid Letter Typed"));
                break;	
            }
        }
        solo.sleep(1000);
    }
	
    /*
     * testing manually making the player 1 win the game and get score methods
     */
    @Test(timeout = TIMEOUT)
    public void testWinningAMultiplayerGamePlay() {
        solo.sleep(3000);
        MultiPlayerModel model = ((MultiPlayer) solo.getCurrentActivity()).getModel();
        match.put("p1score", 0);
        match.put("p2score", 10);
        saveMatch();
        solo.sleep(3000);
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore > opponentScore);
    }
    
    /*
     * testing manually making the player 1 tie the game and get score methods
     */
	@Test(timeout = TIMEOUT)
	public void testTieingAMultiplayerGamePlay() {
        solo.sleep(3000);
        MultiPlayerModel model = ((MultiPlayer) solo.getCurrentActivity()).getModel();
        match.put("p1score", 0);
        match.put("p2score", 0);
        saveMatch();
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore == opponentScore);
	}
	
    /*
     * testing manually making the player 1 lose the game and get score methods
     */
    @Test(timeout = TIMEOUT)
    public void testLosingAMultiplayerGameWithModel() {
        solo.sleep(3000);
        MultiPlayerModel model = ((MultiPlayer) solo.getCurrentActivity()).getModel();
        match.put("p1score", 100);
        match.put("p2score", 5);
        saveMatch();
        solo.sleep(3000);
        int myScore = model.getScore();
        int opponentScore = model.getOpponentScore();
        assertTrue(myScore < opponentScore);
    }
	
    /*
     *  populates wordsList by contacting the database for LIST_SIZE amount of words
     */
    private List<String> getWordsList() {
        List<ParseObject> wordObjects = null;
        try {
            ParseQuery query = new ParseQuery("WordList");
            query.setSkip(match.getInt("wordIndex"));
            query.setLimit(LIST_SIZE); // limit to at most 100 results
            wordObjects= query.find();
            if (wordObjects.size() < LIST_SIZE) {
                ParseQuery query2 = new ParseQuery("WordList");
                query2.setLimit(LIST_SIZE - wordObjects.size());
                wordObjects.addAll(query2.find());
            }
        } catch (ParseException e1) {
            // TODO do something
        }   
        // changing words from parse objects into a list of strings.
        List<String> wordsList = new ArrayList<String>();
        for (ParseObject o : wordObjects) {
            wordsList.add(o.getString("word"));
        }
        return wordsList;
    }
    
	protected void tearDown() throws Exception {
        setMyselfFinished();
        setOpponentFinished();
        deleteThisMatch();
        solo.sleep(3000);
		solo.finishOpenedActivities();
	}
}
