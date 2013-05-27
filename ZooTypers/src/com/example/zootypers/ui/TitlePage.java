package com.example.zootypers.ui;

import com.example.zootypers.R;
import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

/**
 *
 * UI / Activity for title screen.
 * @author cdallas
 *
 */
public class TitlePage extends Activity {

  LoginPopup lp;
  
	Intent multiIntent;  // used to go to MultiplayerPregameScreen
	ParseUser currentUser;
	// used for figuring out valid login inputs
	boolean foundUser;
	boolean foundPassword;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		setContentView(R.layout.activity_title_page);
		// initialize the Intent to go to Pregame selection
		multiIntent = new Intent(this, PreGameSelectionMulti.class);
		Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM", 
		"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			// there is no current user so dont display logged in views
			makeViewsInvisible();
		} else {
			// there is a user logged in. set the current user text
			TextView currentUserText = (TextView) findViewById(R.id.current_user_text);
			String currentUserString = currentUser.getString("username");
			currentUserText.setText(currentUserString);
		}
    lp = new LoginPopup(currentUser);
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title_page, menu);
		return true;
	}

	/**
	 * Called when the user clicks the "Single Player" button.
	 * @param view The button clicked
	 */
	public final void goToPreGameSelection(final View view) {
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Multiplayer" button, if they are logged in.
	 * @param view The button clicked
	 */
	public final void goToPreGameSelectionMulti(final View view) {
		Intent intent = new Intent(this, PreGameSelectionMulti.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Leaderboard" button.
	 * @param view The button clicked
	 */
	public final void goToLeaderboard(final View view) {
		Intent intent = new Intent(this, Leaderboard.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Options" button.
	 * @param view The button clicked
	 */
	public final void goToOptions(final View view) {
		Intent intent = new Intent(this, Options.class);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		// TODO trigger pause screen!
	}

	/**
	 * Called when the user presses the "Multiplayer" button.
	 * Prompts a login screen if user is not logged in already
	 * @param view The button clicked
	 */
	public final void multiplayerLogin(final View view) {
		if (currentUser != null) {
			// someone is already logged in
			String currentUserString = currentUser.getString("username");
			multiIntent.putExtra("username", currentUserString);
			startActivity(multiIntent);
		} else {
			buildPopup(false);
		}
	}

	private void buildPopup(boolean dismisspsw) {
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);

		// inflate either the login layout
		lp.buildLoginPopup(layoutInflater, parentLayout, dismisspsw);
	}
	
	/**
	 * Handles what happens when user clicks the "Forgot your password" link
	 * @param view Button that is pressed
	 */
	public final void forgotPassword(View view) {
    // set up the layout inflater to inflate the popup layout
    LayoutInflater layoutInflater =
    (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

    // the parent layout to put the layout in
    ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);

    // inflate the password layout
    lp.buildResetPopup(layoutInflater, parentLayout);
	}
	
	/**
	 * Handles what happens when user clicks the login button
	 * @param view Button that is pressed
	 */
	public void loginButton(View view) {
	  // Try to login
	  String usernameString = lp.loginButton();
	  // If login was successful, go to the multiplayer game
	  if (!usernameString.equals("")) {
	    multiIntent.putExtra("username", usernameString);
	    startActivity(multiIntent);
	  }
	}

	/**
	 * Exits the login popup window
	 * @param view the button clicked
	 */
	public void exitLoginPopup(View view) {
		lp.exitLoginPopup();
	}

	/**
	 * Exits the password popup window
	 * @param view the button clicked
	 */
	public void exitPasswordPopup(View view) {
		buildPopup(true);
	}
	
	/**
	 * Logs out the current user
	 * @param view the button clicked
	 */
	public void logoutUser(View view) {
	  // Log the user out
    ParseUser.logOut();
    currentUser = ParseUser.getCurrentUser();
    
    // Display appropriate message / etc
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		lp.logoutUser(alertDialogBuilder);
    makeViewsInvisible();
	}

	/**
	 * Handles what happens when user wants to reset password.
	 * @param view the button clicked
	 */
	public void resetPassword(View view) {
	  // Sort through the reset info
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	  lp.resetPassword(alertDialogBuilder);		
	  // Go back to the login popup
		buildPopup(true);
	}

	/**
	 * Goes to the Registration page
	 * @param view the button clicked
	 */
	public void goToRegister(View view) {
		Intent registerIntent = new Intent(this, RegisterPage.class);
		startActivity(registerIntent);
	}

	/**
	 * helper method that sets the logged in status views to invisible
	 */
	private void makeViewsInvisible() {
		// get all the logged in related views
		RelativeLayout loginBox = (RelativeLayout) findViewById(R.id.title_log_info);
		TextView loggedInText = (TextView) findViewById(R.id.loggedin_text);
		TextView currentUserText = (TextView) findViewById(R.id.current_user_text);
		Button logoutButton = (Button) findViewById(R.id.logout_button);

		// set the views to be invisible
		loginBox.setVisibility(View.INVISIBLE);
		loggedInText.setVisibility(View.INVISIBLE);
		currentUserText.setVisibility(View.INVISIBLE);
		logoutButton.setVisibility(View.INVISIBLE);
	}
}

