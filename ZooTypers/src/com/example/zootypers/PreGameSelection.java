package com.example.zootypers;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;

public class PreGameSelection extends Activity {
 
  // TODO either set buttons to background:@android:color/transparent in layout
  // or change default to actually current bg
  private final int DEFAULT_BUTTON_BG = 0;
  private final int HIGHLIGH_BUTTON_BG = android.R.drawable.btn_default;

  private View diff;
  private View animal;
  private View background;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pregame_selection);
   
    // TODO change so initial values are gotten from storage
    diff = null;
    animal = null;
    background = null;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.pregame_selection, menu);
    return true;
  }
 
  /**
   * When the user clicks a button to select a difficulty, highlights that
   * button and un-highlights the last selected button.
   * @param view The button clicked
   */
  public void setDiff(View view) {
    if (diff != null)
      diff.setBackgroundResource(DEFAULT_BUTTON_BG);
    view.setBackgroundResource(HIGHLIGH_BUTTON_BG);
    diff = view;
  }
 
  /**
   * When the user clicks a button to select an animal, highlights that
   * button and un-highlights the last selected button.
   * @param view The button clicked
   */
  public void setAnimal(View view) {
    if (animal != null)
      animal.setBackgroundResource(DEFAULT_BUTTON_BG);
    view.setBackgroundResource(HIGHLIGH_BUTTON_BG);
    animal = view;
  }
 
  /**
   * When the user clicks a button to select a background, highlights that
   * button and un-highlights the last selected button.
   * @param view The button clicked
   */
  public void setBackground(View view) {
    if (background != null)
      background.setBackgroundResource(DEFAULT_BUTTON_BG);
    view.setBackgroundResource(HIGHLIGH_BUTTON_BG);
    background = view;
  }

  public void goToSinglePlayer(View view) {
    // TODO write these diff/animal/bg to storage
    //STORED animal = animal;
    //STORED background = background;   
    //STORED difficulty = diff;   

   
    Intent intent = new Intent(this, SinglePlayer.class);
    startActivity(intent);
  }

}