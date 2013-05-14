package com.example.zootypers.test;

import junit.framework.TestCase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.TextView;
import com.example.zootypers.TitlePage;
import com.example.zootypers.R;
import com.jayway.android.robotium.solo.Solo;

public class TestTitlePage extends  ActivityInstrumentationTestCase2<TitlePage> {

	  private Solo solo;

	  public TestTitlePage() {
            super(TitlePage.class);
	  }


	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testSinglePlayerButton(){
		solo.clickOnButton(("Single Player"));
		assertTrue(solo.searchText("Hard"));
	}
	
	public void testMultiPlayerButton(){
		solo.clickOnButton(("Multiplayer"));
		assertTrue(solo.searchText("Multiplayer"));		
	}

	public void testLeaderBoardButton(){
		solo.clickOnButton(("Leaderboard"));
		assertTrue(solo.searchText("Leaderboard"));
	}
	
	public void testOptionsButton(){
		solo.clickOnButton(("Options"));
		assertTrue(solo.searchText("Options"));
	}
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
