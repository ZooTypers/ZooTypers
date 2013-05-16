package com.example.zootypers.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.zootypers.*;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Testing to see if the multiplayer feature for our game works.
 * 
 * Test-driven development.
 * 
 * @author dyxliang
 *
 */

public class MultiplayerTest extends ActivityInstrumentationTestCase2<TitlePage> {
	
	private Solo solo;
	
	public MultiplayerTest() {
		super(TitlePage.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("Multiplayer");
		EditText username = (EditText) solo.getView(R.id.username_login_input);
		solo.enterText(username, "David");
		solo.sleep(1000);
		EditText password = (EditText) solo.getView(R.id.password_login_input);
		solo.enterText(password, "1234567");
		solo.sleep(1000);
		solo.clickOnButton("Login");
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
	
		
//	public void testingPlayingAMultiplayerGame() {
//		boolean gameFlag = true;
//		while (gameFlag) {
//			automateKeyboardTyping();
//			if (solo.searchButton("New Game") == true) {
//				gameFlag = false;
//			}
//		}
//		assertTrue(solo.searchButton("New Game"));
//		assertTrue(solo.searchButton("Main Menu"));
//	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
