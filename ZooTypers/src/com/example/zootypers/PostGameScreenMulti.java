package com.example.zootypers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PostGameScreenMulti extends PostGameScreen {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        // get & display background
        setContentView(R.layout.activity_pregame_selection_multi);
        Drawable background = ((ImageButton) 
                findViewById(getIntent().getIntExtra("bg", 0))).getDrawable();
    
        setContentView(R.layout.activity_post_game_screen_multi);
        findViewById(R.id.postgame_layout).setBackground(background);
        
        // get and display score
        String score = getIntent().getStringExtra("score");
        TextView finalScore = (TextView) findViewById(R.id.final_score);
        finalScore.setText(score);
        
        // get and display score
        boolean win = getIntent().getBooleanExtra("won", true);
        TextView resultMessage = (TextView) findViewById(R.id.game_result);
        if (win) {
        	resultMessage.setText("You Won!");
        } else {
        	resultMessage.setText("You Lost.");
        }        
        
        // TODO store score
	}
	
	@Override
    public final void goToPreGameSelection(final View view) {
        Intent intent = new Intent(this, PreGameSelectionMulti.class);
        startActivity(intent);
    }

}
