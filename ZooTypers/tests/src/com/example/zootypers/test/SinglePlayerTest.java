package com.example.zootypers.test;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Sampler.Value;
import android.test.ActivityInstrumentationTestCase2;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;	
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.zootypers.SinglePlayer;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;
import com.example.zootypers.PreGameSelection;

/**
 * Testing the single player game mode using robotium testing framework.
 * Checking to see if typing a invalid letter and valid letter works. We are
 * also checking to see if typing word works and if the scores are updated properly;
 * the pause screen and other aspect of single player will be tested as well.
 * 
 * White box test.
 * 
 * @author oaknguyen & dyxliang
 *
 */

public class SinglePlayerTest extends  ActivityInstrumentationTestCase2<PreGameSelection> {

	private Solo solo;
	private char[] lowChanceLetters = {'j', 'z', 'x', 'q', 'k', 'o'};
	
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
  	
	public static List<TextView> getWordsPresented(Solo solo){
		solo.sleep(1000);
		List<TextView> retVal = new ArrayList<TextView>();
		retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word0)));
		retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word1)));
		retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word2)));
		retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word3)));
		retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word4)));
		return retVal;
	}
	
	public void testInvalidCharacterPressed(){
		List<TextView> views = getWordsPresented(solo);
		String firstLetters = "";
		for(TextView s : views){
			firstLetters += s.getText().charAt(0);
		}
		for(char c : lowChanceLetters){
			if(firstLetters.indexOf(c) < 0 ){
				//sendKeys('j' - 68);
				sendKeys(c);
				assertTrue(solo.searchText("Invalid Letter Typed"));
			}
		}
		
	}
	
//	public void testCorrectCharacterPressed(){
//		List<TextView> views = getWordsPresented(solo);
//		TextView s = views.get(0);
//		Log.v("words", s.getText().toString());
//		solo.sleep(5000);
//		sendKeys(s.getText().charAt(0) - 68);//words.get(0).substring(0, 1));
//		Log.v("char typed", String.valueOf(Character.toUpperCase(s.getText().charAt(0))));
//		views = getWordsPresented(solo);
//		solo.sleep(1000);
//		CharSequence word = views.get(0).getText();
//		Log.v("word", word.toString());
//		SpannableString spanString = new SpannableString(word);
//		Log.v("Span", spanString.toString());
//		ForegroundColorSpan[] spans = spanString.getSpans(0, spanString.length(), ForegroundColorSpan.class);
//		assertTrue(spans.length > 0);//Color.rgb(0, 255, 0) == spans[0].getForegroundColor());
//	}
//	
//	public void testTypingCorrectWordUpdateScore() {
//		List<TextView> textList = getWordsPresented(solo);
//		TextView currTextView = textList.get(0);
//		String currWord = currTextView.getText().toString();
//		Log.v("current-word", currWord);
//		for (int i = 0; i < currWord.length(); i++) {
//			char c = currWord.charAt(i);
//			sendKeys(c - 68);
//			Log.v("current-letter", Character.toString(c));
//			//solo.sleep(1000);
//		}
//		TextView score = (TextView) solo.getCurrentActivity().findViewById(R.id.score);
//		String scoreString = score.getText().toString();
//		int expectedScore = currWord.length();
//		int actualScore = Integer.parseInt(scoreString);
//		assertEquals(expectedScore, actualScore);
//	}
	
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
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
