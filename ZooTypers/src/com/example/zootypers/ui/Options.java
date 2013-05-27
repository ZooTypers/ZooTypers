package com.example.zootypers.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		final String title = "Cleared Leaderboard";
		final String message = "The single player leaderboard has been successfully cleared.";
		buildAlertDialog(title, message);
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
		final String title = "Cleared Leaderboard";
		final String message = "Your multiplayer leaderboard has been successfully cleared.";
		buildAlertDialog(title, message);
    }

	/**
	 * Called when the user clicks the "Main Menu" button.
	 * @param view The button clicked
	 */
	public final void goToTitlePage(final View view) {
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}

    
    // TODO remove repetition from title page / post game
	/**
	 * builds an AlertDialog popup with the given title and message
	 * @param title String representing title of the AlertDialog popup
	 * @param message String representing the message of the AlertDialog
	 * popup
	 */
	private void buildAlertDialog(String title, String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// if this button is clicked, close the dialog box
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show the message
		alertDialog.show();
	}

}
