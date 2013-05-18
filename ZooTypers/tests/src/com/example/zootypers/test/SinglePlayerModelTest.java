//package com.example.zootypers.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.*;
//
//import android.content.res.AssetManager;
//import android.test.ActivityInstrumentationTestCase2;
//import android.widget.TextView;
//
//import com.example.zootypers.*;
//import com.example.zootypers.R;
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
//    private SinglePlayerModel model;
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
//        //model = (SinglePlayerModel) solo.getCurrentActivity().getModel();
//        
//    }
//    
//    public static List<TextView> getWordsPresented(Solo solo){
//        solo.sleep(1000);
//        List<TextView> retVal = new ArrayList<TextView>();
//        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word0)));
//        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word1)));
//        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word2)));
//        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word3)));
//        retVal.add(((TextView)solo.getCurrentActivity().findViewById(R.id.word4)));
//        return retVal;
//    }
//    
//    public void testTypingFirstLetterCorrectIndex() {
//        List<TextView> textList = getWordsPresented(solo);
//        TextView currTextView = textList.get(0);
//        String currWord = currTextView.getText().toString();
//        char c = currWord.charAt(0);
//        sendKeys(c - 68);
//        int expectedCurrLetterIndex = 1;
//        int actualCurrLetterIndex = 1;
//    }
//    
//    protected void tearDown() throws Exception {
//        solo.finishOpenedActivities();
//    }
//    
////    private SinglePlayer view;
////    private SinglePlayerModel model;
////    private String[] wordsList = {"apple", "beach", "cake"};
////    
////    @Before
////    public void setUpModel() {
////        view = new SinglePlayer();
////        model = new SinglePlayerModel(States.difficulty.EASY, view.getAssets(), 5);
////    }
////    
////    @Test
////    public void testConstructorDefaultValues() {
////        model = new SinglePlayerModel(States.difficulty.EASY, view.getAssets(), 5);
////    }
////    
////    @Test
////    public void testTypingOneLetterUpdateIndex() {
//////        model.typedLetter('a');
//////        assertEquals(1, model.getCurrLetterIndex());
////    }
//}
