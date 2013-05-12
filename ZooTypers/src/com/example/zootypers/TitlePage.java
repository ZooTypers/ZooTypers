package com.example.zootypers;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
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
        ppw = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        ViewGroup parentLayout = (ViewGroup) findViewById(R.id.title_page_layout);
        // set the position and size of popup
        ppw.showAtLocation(parentLayout, Gravity.CENTER, 10, 20);
        ppw.update(1000, 600);
    }
    
    /**
     * Handles what happens when user clicks the login button
     * @param view Button that is pressed
     */
    public void loginButton(View view) {
    	// get the username and password inputs
    	EditText usernameInput = (EditText) findViewById(R.id.username_login_input);
    	EditText passwordInput = (EditText) findViewById(R.id.password_input_register);
    	
    	final String usernameString = usernameInput.getText().toString();
    	final String passwordString = passwordInput.getText().toString();
    	
    	// intent to go to the pregame multiplayer screen
    	final Intent multiIntent = new Intent(this, PreGameSelectionMulti.class);
    	/*
    	// try to login with the given inputs
    	ParseUser.logInInBackground(usernameString, passwordString, new LogInCallback() {
    	public void done(ParseUser user, ParseException e) {
    		    if (user != null) {
    		    	// login successful
    		    	multiIntent.putExtra("username", usernameString);
    		    	startActivity(multiIntent);
    		    } else {
    		    	TextView errorMessage = (TextView) findViewById(R.id.login_error_message);
    		    	errorMessage.setText("An Error has Occured");
    		    	//TODO : figure out how to know what is wrong
    		    }
    		  }
    	});  */
    	ppw.dismiss();
    }
    
    public void goToRegister(View view) {
    	Intent registerIntent = new Intent(this, RegisterPage.class);
    	startActivity(registerIntent);
    }
}
