//package com.example.zootypers.test;
//
//import static org.junit.Assert.*;
//import org.junit.Test;
//import com.example.zootypers.*;
//
///**
// * LeaderBoard test class helps to test adding, deleteing, and checking whether or not
// * the leaderboard is fully functional without any errors.
// * 
// * (A test that meets the "test-driven development" requirement, since the LeaderBoardModel is not yet completed.)
// * 
// * @author kobryan & dyxliang
// *
// */
//public class LeaderBoardTest {
//	
//	private LeaderBoardModel lbm;
//	
//	@Test(timeout = 100)
//	public void testLeaderBoard() {
//		lbm = new LeaderBoardModel();
//		assertEquals(50, lbm.getTopEntries());
//		assertEquals(0, lbm.getEntryNumber());
//	}
//	
//	@Test(timeout = 100)
//	public void testLeaderBoardWithParam() {
//		lbm = new LeaderBoardModel(10);
//		assertEquals(10, lbm.getTopEntries());
//		assertEquals(0, lbm.getEntryNumber());
//	}
//	
//	@Test(timeout = 100)
//	public void testAddEntry(){
//		//Test 1
//		lbm = new LeaderBoardModel(0);
//		assertEquals(true, lbm.addEntry("Bryan", 1000000));
//		//Test 2
//		lbm = new LeaderBoardModel(5);
//		assertEquals(true, lbm.addEntry("_", 0));
//	}
//	
//	@Test(timeout = 100)
//	public void testGetTopScoresNoCase(){
//		LeaderBoardModel lbm = new LeaderBoardModel(0);
//		//Test once
//		lbm.addEntry("Bryan", 1000000);
//		ScoreEntry[] entry = lbm.getTopScores();
//		assertEquals(0, entry.length);
//		//Test twice
//		lbm.addEntry("Oak", 0);
//		entry = lbm.getTopScores();
//		assertEquals(0, entry.length);
//	}
//	
//	@Test(timeout = 100)
//	public void testGetTopScoresMultipleCase(){
//		lbm = new LeaderBoardModel(3);
//		//One input
//		lbm.addEntry("Bryan", 100);
//		ScoreEntry[] entry = lbm.getTopScores();
//		assertEquals("Bryan", (entry[0]).getName());
//		assertEquals(100, (entry[0]).getScore());
//		assertEquals(0, entry.length);
//		//Full input
//		//Test the overflow case
//		lbm.addEntry("Oak", 1);
//		lbm.addEntry("Bryan", 1);
//		lbm.addEntry("Oak", 2);
//		entry = lbm.getTopScores();
//		assertEquals("Bryan", (entry[0]).getName());
//		assertEquals("Oak", (entry[1]).getName());
//		assertEquals("Bryan", (entry[2]).getName());
//		assertEquals(2, (entry[1]).getScore());
//		assertEquals(3, entry.length);
//	}
//	
//	@Test(timeout = 100)
//	public void testDeleteLowestScore(){
//		//No case
//		lbm = new LeaderBoardModel(0);
//		for(int i = 0; i < 5 ; i ++){
//			lbm.addEntry(("#" + i), i);
//		}
//		assertEquals(false,lbm.deleteLowestScore());
//		//Multiple case
//		lbm = new LeaderBoardModel(5);
//		for(int i = 0; i < 5 ; i ++){
//			lbm.addEntry(("#" + i), i);
//		}
//		//delete one
//		assertEquals(true,lbm.deleteLowestScore());
//		ScoreEntry[] entry = lbm.getTopScores();
//		assertEquals(4, entry.length);
//		assertEquals(1, (entry[3]).getScore());
//		//delete four
//		for(int i = 0; i < 4; i++){
//			lbm.deleteLowestScore();
//		}
//		entry = lbm.getTopScores();
//		assertEquals(4, (entry[0]).getScore());
//		//delete All
//		lbm.deleteLowestScore();
//		entry = lbm.getTopScores();
//		assertEquals(0, entry.length);
//	}
//	
//	@Test(timeout = 100)
//	public void testClearLeaderboard(){
//		lbm = new LeaderBoardModel(5);
//		for(int i = 0; i < 5 ; i ++){
//			lbm.addEntry(("#" + i), i);
//		}
//		ScoreEntry[] entry = lbm.getTopScores();
//		assertEquals(5, entry.length);
//		lbm.clearLeaderboard();
//		lbm.getTopScores();
//		assertEquals(0, lbm.getEntryNumber());
//		assertEquals(0, entry.length);
//	}
//}
