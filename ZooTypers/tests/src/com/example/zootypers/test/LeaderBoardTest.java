package com.example.zootypers.test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import android.test.suitebuilder.annotation.Suppress;
import com.example.zootypers.core.LeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;


/**
 * LeaderBoard test class helps to test adding, deleteing, and checking whether or not
 * the leaderboard is fully functional without any errors.
 * 
 * (A test that meets the "test-driven development" requirement, since the LeaderBoardModel is not yet completed.)
 * 
 * NOTE: The methods below are commented out because the LeaderBoardModel is not yet implemented.
 * TODO: Uncomment the methods once the leaderboard model is implemented in the Version 1.0 Phase.
 * 
 * @author kobryan & dyxliang
 *
 */
@Suppress
public class LeaderBoardTest {

    private LeaderBoardModel lbm;
    private static final int TIMEOUT = 1000;

    /**
     * Tests the model of the leader and that defaults is correct.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testLeaderBoard() {
        lbm = new LeaderBoardModel();
        assertEquals(50, lbm.getTopEntries());
        assertEquals(0, lbm.getEntryNumber());
    }

    /**
     * Tests the leaderboard model constructor with 10.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testLeaderBoardWithParam() {
        lbm = new LeaderBoardModel(10);
        assertEquals(10, lbm.getTopEntries());
        assertEquals(0, lbm.getEntryNumber());
    }

    /**
     * Tests that we can add an entry to the model.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testAddEntry(){
        //Test 1
        lbm = new LeaderBoardModel(0);
        assertEquals(true, lbm.addEntry("Bryan", 1000000));
        //Test 2
        lbm = new LeaderBoardModel(5);
        assertEquals(true, lbm.addEntry("_", 0));
    }

    /**
     * Test that we can get the top score in the current model.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testGetTopScoresNoCase(){
        LeaderBoardModel lbm = new LeaderBoardModel(0);
        //Test once
        lbm.addEntry("Bryan", 1000000);
        ScoreEntry[] entry = lbm.getTopScores();
        assertEquals(0, entry.length);
        //Test twice
        lbm.addEntry("Oak", 0);
        entry = lbm.getTopScores();
        assertEquals(0, entry.length);
    }

    /**
     * Test that we can pull certain top entries.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testGetTopScoresMultipleCase(){
        lbm = new LeaderBoardModel(3);
        //One input
        lbm.addEntry("Bryan", 100);
        ScoreEntry[] entry = lbm.getTopScores();
        assertEquals("Bryan", (entry[0]).getName());
        assertEquals(100, (entry[0]).getScore());
        assertEquals(0, entry.length);
        //Full input
        //Test the overflow case
        lbm.addEntry("Oak", 1);
        lbm.addEntry("Bryan", 1);
        lbm.addEntry("Oak", 2);
        entry = lbm.getTopScores();
        assertEquals("Bryan", (entry[0]).getName());
        assertEquals("Oak", (entry[1]).getName());
        assertEquals("Bryan", (entry[2]).getName());
        assertEquals(2, (entry[1]).getScore());
        assertEquals(3, entry.length);
    }

    /**
     * Test that we can delete the lowest entry in the model.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testDeleteLowestScore(){
        //No case
        lbm = new LeaderBoardModel(0);
        for(int i = 0; i < 5 ; i ++){
            lbm.addEntry(("#" + i), i);
        }
        assertEquals(false,lbm.deleteLowestScore());
        //Multiple case
        lbm = new LeaderBoardModel(5);
        for(int i = 0; i < 5 ; i ++){
            lbm.addEntry(("#" + i), i);
        }
        //delete one
        assertEquals(true,lbm.deleteLowestScore());
        ScoreEntry[] entry = lbm.getTopScores();
        assertEquals(4, entry.length);
        assertEquals(1, (entry[3]).getScore());
        //delete four
        for(int i = 0; i < 4; i++){
            lbm.deleteLowestScore();
        }
        entry = lbm.getTopScores();
        assertEquals(4, (entry[0]).getScore());
        //delete All
        lbm.deleteLowestScore();
        entry = lbm.getTopScores();
        assertEquals(0, entry.length);
    }

    /**
     * Test that we can wipe all the data in the board.
     */
    @Ignore("leader board model is not yet fully implemented")
    @Test(timeout = TIMEOUT)
    public void testClearLeaderboard(){
        lbm = new LeaderBoardModel(5);
        for(int i = 0; i < 5 ; i ++){
            lbm.addEntry(("#" + i), i);
        }
        ScoreEntry[] entry = lbm.getTopScores();
        assertEquals(5, entry.length);
        lbm.clearLeaderboard();
        lbm.getTopScores();
        assertEquals(0, lbm.getEntryNumber());
        assertEquals(0, entry.length);
    }
}
