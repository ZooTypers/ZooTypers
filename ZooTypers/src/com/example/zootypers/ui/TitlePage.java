package com.example.zootypers.ui;

import com.example.zootypers.R;
import com.example.zootypers.util.InternetConnectionException;
import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	private int useTestDB;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		setContentView(R.layout.activity_title_page);
		// initialize the Intent to go to Pregame selection
		multiIntent = new Intent(this, PreGameSelectionMulti.class);

		//used intent to allow testing or not
		useTestDB = getIntent().getIntExtra("Testing", 0);
		Log.i("Extra", "INTENT " + useTestDB);
		// Initialize the database
		if (useTestDB == 1) {
			Parse.initialize(this, "E8hfMLlgnEWvPw1auMOvGVsrTp1C6eSoqW1s6roq",
					"hzPRfP284H5GuRzIFDhVxX6iR9sgTwg4tJU08Bez"); 
		} else {
			Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
					"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
		}
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
		Log.i("ZooTypers", "User entered title screen");
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
		Log.i("ZooTypers", "Proceeding to single player pre game");
		Intent intent = new Intent(this, PreGameSelection.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Multiplayer" button, if they are logged in.
	 * @param view The button clicked
	 */
	public final void goToPreGameSelectionMulti(final View view) {
		Log.i("ZooTypers", "Proceeding to multiplayer player pre game");
		Intent intent = new Intent(this, PreGameSelectionMulti.class);
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Leaderboard" button.
	 * @param view The button clicked
	 */
	public final void goToLeaderboard(final View view) {
		Log.i("ZooTypers", "Proceeding to leaderboard");
		Intent intent = new Intent(this, Leaderboard.class);
		if (useTestDB == 1) {
			intent.putExtra("Testing", 1);
		}
		startActivity(intent);
	}

	/**
	 * Called when the user clicks the "Options" button.
	 * @param view The button clicked
	 */
	public final void goToOptions(final View view) {
		Log.i("ZooTypers", "Proceeding to options menu");
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
			Log.i("ZooTypers", "user is logged in");
			// someone is already logged in
			String currentUserString = currentUser.getString("username");
			multiIntent.putExtra("username", currentUserString);
			startActivity(multiIntent);
		} else {
			Log.i("ZooTypers", "user begins logging in");
			buildPopup(false);
		}
	}

	/**
	 * Build the login popup.
	 * @param dismisspsw Whether or not the reset popup is currently displayed.
	 */
	private void buildPopup(final boolean dismisspsw) {
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
				(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);

		// inflate either the login layout
		lp.buildLoginPopup(layoutInflater, parentLayout, dismisspsw);
	}

	/**
	 * Handles what happens when user clicks the "Forgot your password" link.
	 * @param view Button that is pressed
	 */
	public final void forgotPassword(final View view) {
		Log.i("ZooTypers", "user has forgotten password");

		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
				(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);

		// inflate the password layout
		lp.buildResetPopup(layoutInflater, parentLayout);
	}

	/**
	 * Handles what happens when user clicks the login button.
	 * @param view Button that is pressed
	 */
	public final void loginButton(final View view) {
		// Try to login
		String usernameString;
		try {
			usernameString = lp.loginButton();
		} catch (InternetConnectionException e) {
			e.fillInStackTrace();
			Log.i("ZooTypers", "triggering internet connection error screen");
			Intent intent = new Intent(this, ErrorScreen.class);
			intent.putExtra("error", R.layout.activity_connection_error);
			startActivity(intent);
			return;
		}
		// If login was successful, go to the multiplayer game
		if (!usernameString.equals("")) {
			multiIntent.putExtra("username", usernameString);
			startActivity(multiIntent);
			Log.i("ZooTypers", "user has logged in");
		}
	}

	/**
	 * Exits the login popup window.
	 * @param view the button clicked
	 */
	public final void exitLoginPopup(final View view) {
		Log.i("ZooTypers", "user exits log in");
		lp.exitLoginPopup();
	}

	/**
	 * Exits the password popup window.
	 * @param view the button clicked
	 */
	public final void exitPasswordPopup(final View view) {
		Log.i("ZooTypers", "user exits password popup");
		buildPopup(true);
	}

	/**
	 * Logs out the current user.
	 * @param view the button clicked
	 */
	public final void logoutUser(final View view) {
		Log.i("ZooTypers", "user has logged out");
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
	public final void resetPassword(final View view) {
		Log.i("ZooTypers", "user resets password");
		// Sort through the reset info
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		boolean reset = lp.resetPassword(alertDialogBuilder);
		// Go back to the login popup
		if (reset) {
			buildPopup(true);
		}
	}

	/**
	 * Goes to the Registration page.
	 * @param view the button clicked
	 */
	public final void goToRegister(final View view) {
		Log.i("ZooTypers", "proceeding to register page");
		Intent registerIntent = new Intent(this, RegisterPage.class);
		startActivity(registerIntent);
	}

	/**
	 * helper method that sets the logged in status views to invisible.
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

