package com.example.zootypers.ui;

import com.example.zootypers.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

	PopupWindow login_ppw; // for the multiplayer login popup
	PopupWindow password_ppw;
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
	 * Called when the user clicks the "Multiplayer" button.
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
			buildPopup(true);
		}
	}

	private void buildPopup(boolean isLogin) {
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupView;

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);

		// inflate either the login layout or password layout depending on parameter
		if (isLogin) {
			popupView = layoutInflater.inflate(R.layout.login_popup, null);
			login_ppw = new PopupWindow(popupView, 
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
			login_ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		} else {
			popupView = layoutInflater.inflate(R.layout.reset_pw_layout, null);
			password_ppw = new PopupWindow(popupView, 
			LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
			password_ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
		}
	}
	
	/**
	 * Handles what happens when user clicks the "Forgot your password" link
	 * @param view Button that is pressed
	 */
	public final void forgotPassword(View view) {
		buildPopup(false);
		login_ppw.dismiss();
	}
	/**
	 * Handles what happens when user clicks the login button
	 * @param view Button that is pressed
	 */
	public void loginButton(View view) {
		// get the username and password inputs
		final View contentView = login_ppw.getContentView();
		EditText usernameInput = (EditText) contentView.findViewById(R.id.username_login_input);
		EditText passwordInput = (EditText) contentView.findViewById(R.id.password_login_input);

		final String usernameString = usernameInput.getText().toString();
		final String passwordString = passwordInput.getText().toString();

		// intent to go to the pregame multiplayer screen
		final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);
		// try to login with the given inputs
		ParseUser.logInInBackground(usernameString, passwordString, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// login successful
					boolean emailVerified = user.getBoolean("emailVerified");
					if (emailVerified) {
						multiIntent.putExtra("username", usernameString);
						startActivity(multiIntent);
					} else {
						errorMessage.setText("Email is not verified");
						ParseUser.logOut();
						currentUser = ParseUser.getCurrentUser();
					}
				} else {
					// an error occured. Figure out if invalid username or password
					// or another error 
					errorMessage.setText("An Error has Occured");
				}
			}
		}); 
	}

	/**
	 * Exits the login popup window
	 * @param view the button clicked
	 */
	public void exitLoginPopup(View view) {
		login_ppw.dismiss();
	}

	/**
	 * Exits the password popup window
	 * @param view the button clicked
	 */
	public void exitPasswordPopup(View view) {
		password_ppw.dismiss();
		buildPopup(true);
	}
	/**
	 * Logs out the current user
	 * @param view the button clicked
	 */
	public void logoutUser(View view) {
		ParseUser.logOut();
		currentUser = ParseUser.getCurrentUser();
		final String title = "Logged Out";
		final String message = "You have successfully logged out";
		buildAlertDialog(title, message);
		// make the views disappear
		makeViewsInvisible();
	}

	/**
	 * Handles what happens when user wants to reset password.
	 * @param view the button clicked
	 */
	public void resetPassword(View view) {
		// get the contents of the popup window and get the email
		// user typed in
		final View contentView = password_ppw.getContentView();
		EditText emailReset = (EditText) contentView.findViewById(R.id.email_forgot_password_input);
		final String emailString = emailReset.getText().toString();

		final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);
		// try to reset the password by sending an email
		ParseUser.requestPasswordResetInBackground(emailString, new RequestPasswordResetCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// success
					final String title = "Password Reset";
					final String message = "An email has been sent to " + emailString;
					buildAlertDialog(title, message);
				} else {
					// failure
					int errorCode = e.getCode();
					if (errorCode == ParseException.INVALID_EMAIL_ADDRESS) {
						errorMessage.setText("Invalid Email Address");
					} else {
						errorMessage.setText("Password Reset Failed");
					}
				}
			}
		});
		password_ppw.dismiss();
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

