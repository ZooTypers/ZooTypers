package com.example.zootypers.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.parse.Parse;
import com.parse.ParseUser;

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_options);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }
    
    /**
     * Clears the single player leaderboard.
     * @param view The button clicked.
     */
    public final void clearSingle(final View view) {
    	// TODO get context (?)
    	SingleLeaderBoardModel sl = new SingleLeaderBoardModel(null);
    	sl.clearLeaderboard();
    	/// TODO make notifying popup
    }
    
    /**
     * Clears the multiplayer leaderboard.
     * @param view The button clicked.
     */
    public final void clearMulti(final View view) {
    	Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", 
    			"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
    	ParseUser currentUser = ParseUser.getCurrentUser();
    	if (currentUser == null) {
    		// TODO login popup
    		return;
    	}
    	MultiLeaderBoardModel sl = new MultiLeaderBoardModel("username");
    	sl.clearLeaderboard();   
    	/// TODO make notifying popup 	
    }

}
