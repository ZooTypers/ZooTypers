package com.example.zootypers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.zootypers.core.ScoreEntry;

import android.os.Parcel;

public class ScoreEntryTest {

	private ScoreEntry p;

	@Before
	public void setUp() throws Exception {
		p = new ScoreEntry("Oak", 100);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void scoreEntryConstructorTest() {
		assertEquals(p.getName(), "Oak");
		assertEquals(p.getScore(), 100);
	}
	
}
