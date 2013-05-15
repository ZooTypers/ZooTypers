package com.example.zootypers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 *
 * UI / Activity for pre-game selection screen for a multiplayer game.
 * @author cdallas
 *
 */
public class PreGameSelectionMulti extends PreGameSelection {
	
	@Override
	protected final void storeSelected() {
    	// TODO store selected animal & background
		// (super.animal and super.background)
    }

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.activity_pregame_selection_multi);

        animal = findViewById(R.id.elephant_button);
        setAnimal(animal);
        background = findViewById(R.id.BG1_button);
        setBackground(background);
        
        /*
        Intent userIntent = getIntent();
        userIntent.getExtras();
        String currentUser = userIntent.getStringExtra("username");
        
        TextView usernameText = (TextView) findViewById(R.id.current_user);
        usernameText.setText("You are logged in as " + currentUser); */
    }

    /**
    * When continue is clicked, goes to the game play screen.
    * Passes id of animal & background button selected.
    * @param view The button clicked.
    */
    public final void goToMultiPlayer(final View view) {
        Intent intent = new Intent(this, MultiPlayer.class);
    
        // pass animal and background
        intent.putExtra("anm", super.animal.getId());
        intent.putExtra("bg", super.background.getId());
    
        startActivity(intent);
    }

}
