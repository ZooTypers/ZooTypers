package com.example.zootypers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PostGameScreenMulti extends PostGameScreen {

	String username;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

    // Get & display background
    setContentView(R.layout.activity_pregame_selection_multi);
    Drawable background = ((ImageButton) 
        findViewById(getIntent().getIntExtra("bg", 0))).getDrawable();

    setContentView(R.layout.activity_post_game_screen_multi);
    findViewById(R.id.postgame_layout).setBackground(background);

    // Get and display score
    Integer score = getIntent().getIntExtra("score", 0);
    TextView finalScore = (TextView) findViewById(R.id.final_score);
    finalScore.setText(score.toString());

    // Display result of game
    TextView resultMessage = (TextView) findViewById(R.id.game_result);
    if (getIntent().getBooleanExtra("discon", false)) {
      // Opponent disconnected
      resultMessage.setText("Your Opponent Disconnected.");
      resultMessage.setTextSize(20);
      TextView opp = (TextView) findViewById(R.id.opp_final_score_text);
      opp.setText("");
    } else {
      Integer oppScore = getIntent().getIntExtra("oppScore", 0);
      TextView oppFinalScore = (TextView) findViewById(R.id.opp_final_score);
      oppFinalScore.setText(oppScore.toString());

      if (getIntent().getBooleanExtra("won", true)) {
        resultMessage.setText("You Won!");
      } else {
        resultMessage.setText("You Lost.");
      }
    }

    // TODO store score
  }

	@Override
	public final void goToPreGameSelection(final View view) {
		Intent intent = new Intent(this, PreGameSelectionMulti.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}

}
