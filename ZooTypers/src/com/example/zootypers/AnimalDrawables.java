package com.example.zootypers;

import android.graphics.drawable.Drawable;

/**
 * Translates various representations of animals.
 * Static
 * @author cdallas
 */
public class AnimalDrawables {
  
  /**
   * @param d The drawable of an animal. 
   * @return A unique string representation of that animal (elephant if invalid).
   * Returns 
   */
  public static String drawableToString(Drawable d) {
    return "giraffe";
  }
  
  /**
   * @param s A unique string representation of an animal.
   * @return The resource id of that animal (elephant if invalid).
   */
  public static int stringToResID(String s) {
    return R.drawable.animal_giraffe_opp;
  }

}
