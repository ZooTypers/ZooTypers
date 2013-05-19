package com.example.zootypers;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

/**
 * Activity / UI displayed when there is an interrupt error.
 * @author cdallas
 */
public class InterruptError extends Activity {

  String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_interrupt_error);
    username = getIntent().getStringExtra("username");
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.interrupt_error, menu);
    return true;
  }
  
  /**
  * Called when the user clicks the "Main Menu" button.
  * @param view The button clicked
  */
  public final void goToTitlePage(final View view) {
      Intent intent = new Intent(this, TitlePage.class);
      startActivity(intent);
  }

  /**
  * Called when the user clicks the "New Game" button.
  * @param view The button clicked
  */
  public void goToPreGameSelection(final View view) {
      Intent intent = new Intent(this, PreGameSelectionMulti.class);
      intent.putExtra("username", username);
      startActivity(intent);
  }

}
