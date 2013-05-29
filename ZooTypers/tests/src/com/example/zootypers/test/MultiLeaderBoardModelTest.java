package com.example.zootypers.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;

public class MultiLeaderBoardModelTest {

	private MultiLeaderBoardModel ml;

	@Before
	public void setUp() {
		
	}
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructorWithNameOnlyTest() {
		ml = new MultiLeaderBoardModel("Oak");
		assertEquals(10, ml.getTopScores().size());
	}
	@Test
	public void constructorWithNameAndSizeTest() {
		ml = new MultiLeaderBoardModel("Oak", 5);
		assertEquals(5, ml.getTopScores().size());
	}
	
	@Test
	public void getTopScoresTest() {
		ml = new MultiLeaderBoardModel("Oak", 10);
		List<ScoreEntry> scores = ml.getTopScores();
		for(int i = 10; i > 0; i--){
			assertEquals(i, scores.get(i).getScore());
			assertEquals("Oak", scores.get(i).getScore());
		}
	}

	@Test
	public void addEntryTest() {
		ml = new MultiLeaderBoardModel("Oak", 10);
		ml.addEntry(100000);
		ScoreEntry shouldEquals = ml.getTopScores().get(0);
		assertEquals("Oak", shouldEquals.getName());
		assertEquals(100000, shouldEquals.getScore());
	}
	
	@Test
	public void addMultipleEntriesTest() {
		ml = new MultiLeaderBoardModel("Oak", 10);
		MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel("George", 10);
		ml.addEntry(100000);
		
		//both model should pull the same numbers
		ScoreEntry mlShouldEquals = ml.getTopScores().get(0);
		assertEquals("Oak", mlShouldEquals.getName());
		assertEquals(100000, mlShouldEquals.getScore());
		
		ScoreEntry ml2ShouldEquals = ml2.getTopScores().get(0);
		assertEquals("Oak", ml2ShouldEquals.getName());
		assertEquals(100000, ml2ShouldEquals.getScore());
		
		//add top highest
		ml2.addEntry(100001);
		
		
		//both model should pull the new highest
		ScoreEntry mlShouldEquals2 = ml.getTopScores().get(0);
		assertEquals("George", mlShouldEquals2.getName());
		assertEquals(100001, mlShouldEquals2.getScore());
		
		ScoreEntry ml2ShouldEquals2 = ml.getTopScores().get(0);
		assertEquals("George", ml2ShouldEquals2.getName());
		assertEquals(100001, ml2ShouldEquals2.getScore());
	}
	
	@Test
	public void getRankNoEntryTest() {
		ml = new MultiLeaderBoardModel("Oak");
		assertEquals(0, ml.getRank());
	}
	
	@Test 
	public void getRankTopEntryTest() {
		ml = new MultiLeaderBoardModel("Oak");
		ml.addEntry(100000);
		assertEquals(1, ml.getRank());
	}
	
	@Test
	public void getRankSecondtoTopEntryTest() {
		ml = new MultiLeaderBoardModel("Oak", 10);
		MultiLeaderBoardModel ml2 = new MultiLeaderBoardModel("George", 10);
		ml.addEntry(100000);
		ml2.addEntry(100001);
		assertEquals(2, ml.getRank());
	}
	
	@Test 
	public void getRelativeScoreSizeTest() {
		ml = new MultiLeaderBoardModel("Oak");
		ml.addEntry(50);
		List<ScoreEntry> scores = ml.getRelativeScores(6);
		assertEquals(6, scores.size());
	}
	
	@Test
	public void isInTopEntriesTrueTest() {
		ml = new MultiLeaderBoardModel("Oak");
		assertTrue(ml.isInTopEntries());
	}
	
	@Test
	public void isInTopEntriesFalseTest() {
		ml = new MultiLeaderBoardModel("Oak");
		ml.addEntry(1);
		assertFalse(ml.isInTopEntries());
	}
	
	
}
