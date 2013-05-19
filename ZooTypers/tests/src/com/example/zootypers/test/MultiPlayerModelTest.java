package com.example.zootypers.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.zootypers.MultiPlayerModel;

import junit.framework.TestCase;

/**
 * 
 * Testing the multi-player model of the game.
 * 
 * (White box testing.)
 * 
 * TODO: tests
 * 
 * @author oaknguyen && dyxliang
 *
 */

public class MultiPlayerModelTest extends TestCase{
	MultiPlayerModel model;
	final int TIMEOUT = 10000;

	@Before
	public void setUp() throws Exception {
		model = new MultiPlayerModel(5, "Oak", 1);
	}

	@Test(timeout = TIMEOUT)
	public void testInitialValues() {
		assertEquals(5, model.getWordsDisplayed().length);
		assertEquals(-1, model.getCurrWordIndex());
		assertEquals(-1, model.getCurrLetterIndex());
	}
	@After
	public void tearDown() throws Exception {
	    
	}


}
