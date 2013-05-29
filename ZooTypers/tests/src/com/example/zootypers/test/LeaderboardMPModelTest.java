package com.example.zootypers.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;

import com.example.zootypers.core.MultiLeaderBoardModel;

@Suppress
public class LeaderboardMPModelTest extends AndroidTestCase {

    private MultiLeaderBoardModel model;
    private static final int TIMEOUT = 10000;
    
    @Before
    public void setUp() throws Exception {
        model = new MultiLeaderBoardModel("David");
    }

    @Test(timeout = TIMEOUT)
    public void testCreatingADefaultConstructor() {
        
    }
    
    @After
    public void tearDown() throws Exception {
        //tear down?
    }
}
