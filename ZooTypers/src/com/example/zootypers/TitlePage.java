package com.example.zootypers;

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
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
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
	
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_title_page);
        Parse.initialize(this, "yUgc5n1ws3KrVpdSnagD" +
		  		"5vwHvaGKpq00KUP3Kkak", "e9tvSeC8GtMEE3ux" +
		  				"3B4phnWNtL9QRjmk7VG1zdZI");
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
    * Prompts a login screen
    * @param view The button clicked
    */
    public final void multiplayerLogin(final View view) {
    	// make the login popup screen with the login_popup layout
    	LayoutInflater layoutInflater = 
                 (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.login_popup, null);
        ppw = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);
        // set the position and size of popup
        ppw.showAtLocation(parentLayout, Gravity.TOP, 10, 50);
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
    	final Intent multiIntent = new Intent(this, PreGameSelectionMulti.class);
    	
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
    		    e.printStackTrace();
    		    errorMessage.setText("An Error has Occured");
    		    //TODO : figure out how to know what is wrong
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
}
