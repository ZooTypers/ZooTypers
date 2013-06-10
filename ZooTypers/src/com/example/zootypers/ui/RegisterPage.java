package com.example.zootypers.ui;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zootypers.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 *
 * @author ZooTypers
 * Handles new user registration
 *
 */
public class RegisterPage extends Activity {

	private String currentUser;
	private Intent titleIntent; // used to go back to title

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		setContentView(R.layout.activity_register_page);
		Log.d("RegisterPage: Using Test Database", "" +TitlePage.useTestDB);
		if (TitlePage.useTestDB) {
			Parse.initialize(this, "E8hfMLlgnEWvPw1auMOvGVsrTp1C6eSoqW1s6roq",
					"hzPRfP284H5GuRzIFDhVxX6iR9sgTwg4tJU08Bez"); 
		} else {
			Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
					"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C");
		}
		Log.i("Register", "entered register page");
	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register_page, menu);
		return true;
	}

	/**
	 * Cancel registering and go back to the title page.
	 * @param view The button clicked.
	 */
	public final void cancel(final View view) {
		Log.i("Register", "back to title page from register");
		finish();
	}

	/**
	 * Handles what happens when the submit button is pressed.
	 * @param view The button that is pressed
	 */
	public final void submitRegister(final View view) {
		Log.i("Register", "submitting registration");

		// get the inputs and prompts from the user
		TextView usernameText = (TextView) findViewById(R.id.username_register);
		EditText usernameEdit = (EditText) findViewById(R.id.username_register_input);
		String usernameString = usernameEdit.getText().toString().trim();

		TextView passwordText = (TextView) findViewById(R.id.password_register);
		EditText passwordEdit = (EditText) findViewById(R.id.password_input_register);
		String passwordString = passwordEdit.getText().toString();

		TextView confirmPWText = (TextView) findViewById(R.id.confirm_password_register);
		EditText confirmPWEdit = (EditText) findViewById(R.id.confirm_password_input_register);
		String confirmPWString = confirmPWEdit.getText().toString();

		TextView emailText = (TextView) findViewById(R.id.email_register);
		EditText emailEdit = (EditText) findViewById(R.id.email_input_register);
		String emailString = emailEdit.getText().toString();

		if ((((usernameString.length() == 0) || (passwordString.length() == 0))
				|| (confirmPWString.length() == 0))
				|| (emailString.length() == 0)) {
			// case where everything is not filled out
			Log.w("Register", "missing information for registration");

			buildAlertDialog(R.string.missing_reg_title, R.string.missing_reg_msg, false);
			// indicate which ones were not filled out
			if (usernameString.length() == 0) {
				indicateError(usernameText, true);
			} else {
				indicateError(usernameText, false);
			}

			if (passwordString.length() == 0) {
				indicateError(passwordText, true);
			} else {
				indicateError(passwordText, false);
			}

			if (confirmPWString.length() == 0) {
				indicateError(confirmPWText, true);
			} else {
				indicateError(confirmPWText, false);
			}

			if (emailString.length() == 0) {
				indicateError(emailText, true);
			} else {
				indicateError(emailText, false);
			}
		} else {
			// the fields arent empty, check if the passwords match
			if (!passwordString.equals(confirmPWString)) {
				Log.w("Register", "passwords do not match");

				buildAlertDialog(R.string.unmatch_pswd_title, R.string.unmatch_pswd_msg, false);
				indicateError(passwordText, true);
				indicateError(confirmPWText, true);
				// all other text fields should not be red
				indicateError(usernameText, false);
				indicateError(emailText, false);
			} else {
				if ((usernameString.length() >= 4) && (passwordString.length() >= 4)) {
					// make sure username and pw is reasonable length, for security purposes
					// need to put the color back to normal
					indicateError(usernameText, false);
					indicateError(passwordText, false);
					indicateError(confirmPWText, false);
					indicateError(emailText, false);
					// inputs are valid put into the database
					setupDatabase(usernameString, passwordString, emailString);
				} else {
					if (usernameString.length() < 4) {
						Log.w("Register", "username do not contain enough characters");
						indicateError(usernameText, true);
					} else {
						indicateError(usernameText, false);
					}
					if (passwordString.length() < 4) {
						Log.w("Register", "password do not contain enough characters");
						indicateError(passwordText, true);
					} else {
						indicateError(passwordText, false);
					}
					buildAlertDialog(R.string.short_up_title, R.string.short_up_msg, false);
				}

			}
		}
	}

	/**
	 * Builds an alertdialog(popup) for a given title, message, and
	 * optional multiplayer option.
	 * @param title String representing the title of the popup
	 * @param message String representing the message displayed in popup
	 * @param goToTitle indicates whether popup should handle
	 * case where it goes back to titlepage
	 */
	private void buildAlertDialog(final int title, final int message,
			final boolean goToTitle) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

		if (goToTitle) {
			titleIntent = new Intent(this, TitlePage.class);
			titleIntent.putExtra("current user", currentUser);
		}
		// set title
		alertDialogBuilder.setTitle(title);

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(false)
		.setPositiveButton(R.string.close_alert, new DialogInterface.OnClickListener() {
			public void onClick(final DialogInterface dialog, final int id) {
				// if this button is clicked, close the dialog box
				dialog.cancel();
				if (goToTitle) {
					// allowed to go back to title page
					startActivity(titleIntent);
				}
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show the message
		alertDialog.show();
	}

	/**
	 * Highlights the given textview depending on if it is an error.
	 * @param text TextView to be highlighted
	 * @param errorFlag indicates whether text should be highlighted
	 * to indicate an error or not
	 */
	private void indicateError(final TextView text, final boolean errorFlag) {
		if (errorFlag) {
			// highlight error
			text.setTextColor(getResources().getColor(R.color.red));
		} else {
			// put back to original color
			text.setTextColor(getResources().getColor(R.color.black));
		}
	}

	/**
	 * Puts the user information into database.
	 * @param username String representing user's username
	 * @param password String representing user's password
	 * @param email String representing user's email
	 */
	private void setupDatabase(final String username, final String password,
			final String email) {
		Log.i("Register", "all registration fields are valid, submitting to database");

		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

		user.signUpInBackground(new SignUpCallback() {
			public void done(final ParseException e) {
				if (e == null) {
					Log.i("Register", 
							"registration information successfully submitted to database");

					// sign up succeeded so go to multiplayer screen
					// store the username of the current player
					currentUser = username;
					ParseUser.logOut();
					buildAlertDialog(R.string.acc_created_title, R.string.acc_created_msg, true);
				} else {
					Log.i("Register", "registration unsuccessful");

					// sign up didn't succeed
					// TODO: figure out how do deal with error
					int errorCode = e.getCode();
					// figure out what the error was
					int message;
					if (errorCode == ParseException.ACCOUNT_ALREADY_LINKED) {
						message = R.string.reg_faila_msg;
					} else if (errorCode == ParseException.EMAIL_TAKEN) {
						message = R.string.reg_faile_msg;
					} else if (errorCode == ParseException.USERNAME_TAKEN) {
						message = R.string.reg_failu_msg;
					} else if (errorCode == ParseException.INVALID_EMAIL_ADDRESS) {
						message = R.string.reg_faili_msg;
					} else {
						e.printStackTrace();
						message = R.string.reg_failo_msg;
					}
					buildAlertDialog(R.string.reg_fail_title, message, false);
				}
			}
		});
	}
}
