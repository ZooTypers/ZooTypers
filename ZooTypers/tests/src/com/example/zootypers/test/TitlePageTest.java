package com.example.zootypers.test;

import com.example.zootypers.TitlePage;

import android.test.ActivityInstrumentationTestCase2;

public class TitlePageTest extends ActivityInstrumentationTestCase2<TitlePage> {

//    public TitlePageTest(String pkg, Class<TitlePage> activityClass) {
//        super(pkg, activityClass);
//        // TODO Auto-generated constructor stub
//    }

    private TitlePage titleActivity;
    
    @SuppressWarnings("deprecation")
    public TitlePageTest() {
        super("com.android.example.ZooTypers", TitlePage.class);
      } // end of SpinnerActivityTest constructor definition

    @Override
    protected void setUp() throws Exception {
      super.setUp();

      setActivityInitialTouchMode(false);
    } // end of setUp() method definition
    
}
