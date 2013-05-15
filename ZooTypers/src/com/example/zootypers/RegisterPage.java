package com.example.zootypers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);
		Parse.initialize(this, "yUgc5n1ws3KrVpdSnagD" +
		  		"5vwHvaGKpq00KUP3Kkak", "e9tvSeC8GtMEE3ux" +
		  				"3B4phnWNtL9QRjmk7VG1zdZI");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register_page, menu);
		return true;
	}

	/**
	 * Handles what happens when the submit button is pressed
	 * @param view The button that is pressed
	 */
	public void submitRegister(View view) {
		// get the inputs and prompts from the user
		TextView usernameText = (TextView) findViewById(R.id.username_register);
		EditText usernameEdit = (EditText) findViewById(R.id.username_register_input);
		String usernameString = usernameEdit.getText().toString();
		
		TextView passwordText = (TextView) findViewById(R.id.password_register);
		EditText passwordEdit = (EditText) findViewById(R.id.password_input_register);
		String passwordString = passwordEdit.getText().toString();
		
		TextView confirmPWText = (TextView) findViewById(R.id.confirm_password_register);
		EditText confirmPWEdit = (EditText) findViewById(R.id.confirm_password_input_register);
		String confirmPWString = confirmPWEdit.getText().toString();
		
		TextView emailText = (TextView) findViewById(R.id.email_register);
		EditText emailEdit = (EditText) findViewById(R.id.email_input_register);
		String emailString = emailEdit.getText().toString();
		
		if (usernameString.length() == 0 || passwordString.length() == 0
				|| confirmPWString.length() == 0
				|| emailString.length() == 0) {
			// case where everything is not filled out 
			final String title = "Missing information";
			final String message = "Please fill in all of the fields";
			buildAlertDialog(title, message, false);
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
				final String title = "Invalid password";
				final String message = "Password fields do not match";
				buildAlertDialog(title, message, false);
				indicateError(passwordText, true);
				indicateError(confirmPWText, true);
				// all other text fields should not be red
				indicateError(usernameText, false);
				indicateError(emailText, false);
			} else {
				if (usernameString.length() >= 4 && passwordString.length() >= 4) {
					// make sure username and pw is reasonable length, for security purposes
					// need to put the color back to normal
					indicateError(usernameText, false);
					indicateError(passwordText, false);
					indicateError(confirmPWText, false);
					indicateError(emailText, false);
					// inputs are valid put into the database
					setupDatabase(usernameString, passwordString, emailString);
				} else {
					final String title = "Invalid username/password";
					final String message = "Username/password must be at least 4 characters long";
					if (usernameString.length() < 4) {
						indicateError(usernameText, true);
					} else {
						indicateError(usernameText, false);
					}
					if (passwordString.length() < 4) {
						indicateError(passwordText, true);
					} else {
						indicateError(passwordText, false);
					}
					buildAlertDialog(title, message, false);
				}
				
			}
		}
	}
	
	/**
	 * builds an alertdialog(popup) for a given title, message, and
	 * optional multiplayer option
	 * @param title String representing the title of the popup
	 * @param message String representing the message displayed in popup
	 * @param goToTitle indicates whether popup should handle
	 * case where it goes back to titlepage
	 */
	private void buildAlertDialog(String title, String message,
			final boolean goToTitle) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
	
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
			.setPositiveButton("Close", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
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
	 * highlights the given textview depending on if it is an error
	 * @param text TextView to be highlighted
	 * @param errorFlag indicates whether text should be highlighted
	 * to indicate an error or not
	 */
	private void indicateError(TextView text, boolean errorFlag) {
		if (errorFlag) {
			// highlight error 
			text.setTextColor(getResources().getColor(R.color.red));
		} else {
			// put back to original color
			text.setTextColor(getResources().getColor(R.color.black));
		}
	}
	
	/**
	 * puts the user information into database
	 * @param username String representing user's username
	 * @param password String representing user's password
	 * @param email String representing user's email
	 */
	private void setupDatabase(final String username, final String password, final String email) {
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// sign up succeeded so go to multiplayer screen
					// store the username of the current player
					currentUser = username;
					final String title = "Account Created Successfully!";
					final String message = "Please verify your email before playing";
					buildAlertDialog(title, message, true);
				} else {
					// sign up didnt succed. //TODO: figure out how do deal with error
					int errorCode = e.getCode();
					// figure out what the error was
					final String title = "Registration failed";
					String message;
					if (errorCode == ParseException.ACCOUNT_ALREADY_LINKED) {
						message = "Account already in use";
					} else if (errorCode == ParseException.EMAIL_TAKEN) {
						message = "Email already in use";
					} else if (errorCode == ParseException.USERNAME_TAKEN) {
						message = "Username is already in use";
					} else if (errorCode == ParseException.INVALID_EMAIL_ADDRESS) {
						message = "Invalid Email Address";
					} else {
						e.printStackTrace();
						message = "Account could not be created";
					}
					buildAlertDialog(title, message, false);
				}
			}
		});
	}
}
