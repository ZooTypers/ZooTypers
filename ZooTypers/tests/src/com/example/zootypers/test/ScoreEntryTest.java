package com.example.zootypers.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.example.zootypers.core.ScoreEntry;

public class ScoreEntryTest extends AndroidTestCase {

    private ScoreEntry entry;
    private static final int TIMEOUT = 10000;

    @Before
    public void setUp() throws Exception {
        entry = new ScoreEntry("Oak", 100);
    }

    @Test(timeout = TIMEOUT)
    public void scoreEntryConstructorTest() {
        assertEquals(entry.getName(), "Oak");
        assertEquals(entry.getScore(), 100);
    }
    
    @Test(timeout = TIMEOUT)
    public void toStringTest() {
        assertEquals("Oak 100", entry.toString());
    }

    @After
    public void tearDown() throws Exception {

    }
}
