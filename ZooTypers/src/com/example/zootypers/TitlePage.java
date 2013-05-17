package com.example.zootypers;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
*
* UI / Activity for title screen.
* @author cdallas
*
*/
public class TitlePage extends Activity {

	PopupWindow ppw; // for the multiplayer login popup
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
        Parse.initialize(this, "yUgc5n1ws3KrVpdSnagD" +
		  		"5vwHvaGKpq00KUP3Kkak", "e9tvSeC8GtMEE3ux" +
		  				"3B4phnWNtL9QRjmk7VG1zdZI");
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
    		// there is no one logged in. prompt the login screen
    		// make the login popup screen with the login_popup layout
        	LayoutInflater layoutInflater = 
                     (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = layoutInflater.inflate(R.layout.login_popup, null);
            ppw = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
            ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);
            // set the position and size of popup
            ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
    	}
    }
    
    /**
     * Handles what happens when user clicks the login button
     * @param view Button that is pressed
     */
    public void loginButton(View view) {
    	// get the username and password inputs
    	final View contentView = ppw.getContentView();
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
     * Exits the popup window
     * @param view the button clicked
     */
    public void exitPopup(View view) {
    	ppw.dismiss();
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
    	final View contentView = ppw.getContentView();
    	EditText emailReset = (EditText) contentView.findViewById(R.id.email_forgot_password_input);
    	final String emailString = emailReset.getText().toString();
    	
    	final TextView errorMessage = (TextView) contentView.findViewById(R.id.login_error_message);
    	// try to reset the password by sending an email
    	ParseUser.requestPasswordResetInBackground(emailString, new RequestPasswordResetCallback() {
    		public void done(ParseException e) {
    			if (e == null) {
    				// success
    				final String title = "Password Reset";
    				final String message = "An email has been sent to " +
    						emailString;
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
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
	
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
    	TextView loggedInText = (TextView) findViewById(R.id.loggedin_text);
    	TextView currentUserText = (TextView) findViewById(R.id.current_user_text);
    	Button logoutButton = (Button) findViewById(R.id.logout_button);
    	
    	// set the views to be invisible
    	loggedInText.setVisibility(View.INVISIBLE);
    	currentUserText.setVisibility(View.INVISIBLE);
    	logoutButton.setVisibility(View.INVISIBLE);
    }
}
