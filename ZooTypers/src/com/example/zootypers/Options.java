package com.example.zootypers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
*
* UI / Activity for options screen.
* @author cdallas
*
*/
public class Options extends Activity {

  @Override
  protected final void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_options);
  }

  @Override
  public final boolean onCreateOptionsMenu(final Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }

}
