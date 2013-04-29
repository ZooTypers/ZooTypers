package com.example.zootypers;

import java.io.File;
import java.io.IOException;
import java.util.Observable;

import org.apache.commons.io.FileUtils;

/** Model class for Single Player.
 * @author winglam, nhlien93, dyxliang
 */
public class SinglePlayerModel extends Observable {

  // array of words
  private String[] wordsList;
  // array of indices that references strings inside wordsList
  private int[] wordsDisplayed;
  // index of a string inside wordsDisplayed, should never be used on wordsList!
  private int currWordIndex;
  // index of letter that has been parsed from the currWordIndex
  private int currLetterIndex;
  // index of the next word to pull from wordsList, should only be used with wordsList
  private int nextWordIndex;
  private int score;
  private SinglePlayerUI view;

  // number of words displayed
  private final int numWordsDisplayed = 5;

  public SinglePlayerModel(final String animalID, final String backgroudID,
      final States.difficulty diff) {
    fillWordsList(diff);

    wordsDisplayed = new int[numWordsDisplayed];
    nextWordIndex = numWordsDisplayed;
    score = 0;
    currWordIndex = -1;
    currLetterIndex = -1;

    // putting first five words into word
    for (int i = 0; i < numWordsDisplayed; i++) {
      wordsDisplayed[i] = i;
    }

    view = new SinglePlayerUI();
    this.addObserver(view);
  }

  // reads some files into wordsList based on diff
  private void fillWordsList(final States.difficulty diff) {
    File f;
    if (diff == States.difficulty.EASY) {
      f = new File("4words.txt");
    } else if (diff == States.difficulty.MEDIUM) {
      f = new File("5words.txt");
    } else {
      f = new File("6words.txt");
    }

    try {
      String contents = FileUtils.readFileToString(f);
      String[] parsedWords = contents.split("\n");
      wordsList = parsedWords;
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public final void typedLetter(final char letter) {
    // currently not locked to a word
    if (currWordIndex == -1) {
      for (int i = 0; i < wordsDisplayed.length; i++) {
        if (wordsList[wordsDisplayed[i]].charAt(0) == letter) {
          currWordIndex = i;
          currLetterIndex = 1;
          setChanged();
          notifyObservers(States.update.HIGHLIGHT);
          return;
        }
      }
    } else if (wordsList[wordsDisplayed[currWordIndex]].charAt(currLetterIndex) == letter) {
      // letter is the next letter in locked word
      int wordLen = wordsList[wordsDisplayed[currWordIndex]].length();

      // word is completed after letter
      if (currLetterIndex + 1 > wordLen) {
        score += wordLen;
        updateWordsDisplayed();
        currLetterIndex = -1;
        currWordIndex = -1;
      } else {
        currLetterIndex += 1;
        setChanged();
        notifyObservers(States.update.HIGHLIGHT);
      }
      return;
    }

    // wrong letter typed
    setChanged();
    notifyObservers(States.update.WRONG_LETTER);
  }

  // replace the current word on the list with a new word
  // post: nextWord will always be set to a valid index of wordsList
  private void updateWordsDisplayed() {
    if (nextWordIndex >= wordsList.length) {
      nextWordIndex = 0;
    }
    wordsDisplayed[currWordIndex] = nextWordIndex;

    nextWordIndex += 1;

    setChanged();
    notifyObservers(States.update.FINISHED_WORD);
  }

  /**
   * @return current score of the player
   */
  public final int getScore() {
    return score;
  }

  /**
   * @return the string representation of the current word the player is locked to,
   * null if player is not locked to a word
   */
  public final String getCurrWord() {
    if (currWordIndex == -1) {
    return null;
  }

    return wordsList[wordsDisplayed[currWordIndex]];
  }

  /**
   * @return the index of the word the player is currently locked to within the list displayed
   */
  public final int getCurrWordIndex() {
    return currWordIndex;
  }

  /**
   * @return the index of the letter the player is expected to type next
   * from the word he/she is locked to
   */
  public final int getCurrLetterIndex() {
    return currLetterIndex;
  }
}
