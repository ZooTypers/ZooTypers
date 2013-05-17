//package com.example.zootypers.test;
//import org.junit.*;
//
//import android.test.ActivityInstrumentationTestCase2;
//
//import com.example.zootypers.*;
//import com.jayway.android.robotium.solo.Solo;
//
//import junit.framework.TestCase;
//
///**
// * 
// * Testing the model of the single player.
// * 
// * (White box testing.)
// * 
// * TODO: tests
// * 
// * @author dyxliang
// *
// */
//
//public class SinglePlayerModelTest extends ActivityInstrumentationTestCase2<PreGameSelection> {
//    
//    private Solo solo;
//    private char[] lowChanceLetters = {'j', 'z', 'x', 'q', 'k', 'o'};
//    private static final int TIMEOUT = 10000;
//    
//    public SinglePlayerModelTest() {
//        super(PreGameSelection.class);
//    }
//
//    protected void setUp() throws Exception {
//        this.setActivityInitialTouchMode(false);
//        solo = new Solo(getInstrumentation(), getActivity());
//        solo.clickOnButton("Continue");
//    }
//    
//    public void testTypingFirstLetterCorrectIndex() {
//        
//    }
//    
//    protected void tearDown() throws Exception {
//        solo.finishOpenedActivities();
//    }
//    
//    private SinglePlayer view;
//    private SinglePlayerModel model;
//    private String[] wordsList = {"apple", "beach", "cake"};
//    
//    @Before
//    public void setUpModel() {
//        view = new SinglePlayer();
//        model = new SinglePlayerModel(States.difficulty.EASY, view.getAssets(), 5);
//    }
//    
//    @Test
//    public void testConstructorDefaultValues() {
//        model = new SinglePlayerModel(States.difficulty.EASY, view.getAssets(), 5);
//    }
//    
//    @Test
//    public void testTypingOneLetterUpdateIndex() {
////        model.typedLetter('a');
////        assertEquals(1, model.getCurrLetterIndex());
//    }
//}
