package com.example.zootypers;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class MultiPlayer extends SinglePlayer {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get animal & background selected by user
        setContentView(R.layout.activity_pregame_selection_multi);
        Drawable animal = ((ImageButton) findViewById
                (getIntent().getIntExtra("anm", 0))).getDrawable();
        bg = getIntent().getIntExtra("bg", 0);
        Drawable background = ((ImageButton) findViewById(bg)).getDrawable();
            
        // start model
        model = new SinglePlayerModel(States.difficulty.MEDIUM, this.getAssets(), NUM_WORDS);
        model.addObserver(this);
        
        // change screen view
        setContentView(R.layout.activity_multi_player);
        initialDisplay(animal, background);
        
        // create and start timer
        gameTimer = new GameTimer(START_TIME, INTERVAL);
        gameTimer.start();
    }

    /**
    * Displays the oppenent's animal on the screen.
    * @param score The score to display.
    */
    @SuppressLint("NewApi")
	public final void displayOpponentAnimal(final Drawable animal) {
        TextView oppAnimal = (TextView) findViewById(R.id.opp_animal_image);
        oppAnimal.setBackground(animal);
    }

    /**
    * Updates the oppenent's score on the screen.
    * @param score The score to display.
    */
    public final void displayOpponentScore(final int score) {
        TextView currentScore = (TextView) findViewById(R.id.opp_score);
        currentScore.setText(Integer.toString(score));
    }
    
    @Override
    public void pauseGame(View view) {
    	// do nothing
    }
    
    // TODO overrride update method

    /**
    * Called when the timer runs out; goes to the post game screen.
    */
    @Override
    public final void goToPostGame() {
        Intent intent = new Intent(this, PostGameScreenMulti.class);
        // pass score
        intent.putExtra("score", ((TextView) findViewById(R.id.score)).getText().toString());
        // TODO get whether you won from the model
        intent.putExtra("won", true);
        intent.putExtra("bg", bg);
        startActivity(intent);
    }
}
