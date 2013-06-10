package com.example.zootypers.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.util.InterfaceUtils;
import com.example.zootypers.util.InternetConnectionException;
import com.parse.Parse;
import com.parse.ParseUser;

/**
 *
 * UI / Activity for options screen.
 * @author cdallas
 *
 */
@SuppressWarnings("unused")
public class Options extends Activity {

	LoginPopup lp;
	ParseUser currentUser;
	private boolean useTestDB;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("Options", "entered options");

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.activity_options);
		lp = new LoginPopup(currentUser);

		//Vibration listener
		Switch mySwitch = (Switch) findViewById(R.id.vibrate);
		setCorrectPosition(mySwitch, "vibrate.txt");
		//attach a listener to check for changes in state
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					Log.i("Options", "vibrate is switched on");
					deleteFile("vibrate.txt");
				}else{
					try {
						Log.i("Options", "vibrate is switched off");
						FileOutputStream fos = openFileOutput("vibrate.txt", Context.MODE_PRIVATE);
						fos.write(0);
						fos.close();
					} catch (IOException e){
						Log.e("Options.java", "vibrate.txt", e);
					}
				}
			}
		});

		//BGM listener
		mySwitch = (Switch) findViewById(R.id.bgm);
		setCorrectPosition(mySwitch, "bgm.txt");
		//attach a listener to check for changes in state
		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					Log.i("Options", "background music is switched on");
					deleteFile("bgm.txt");
				}else{
					try {
						Log.i("Options", "background music is switched off");
						FileOutputStream fos = openFileOutput("bgm.txt", Context.MODE_PRIVATE);
						fos.write(0);
						fos.close();
					} catch (IOException e){
						Log.e("Options.java", "vibrate.txt", e);
					}
				}
			}
		});

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
		Log.i("Options", "clearing single player leaderboard");
		SingleLeaderBoardModel sl = new SingleLeaderBoardModel(this.getApplicationContext());
		sl.clearLeaderboard();
		InterfaceUtils.buildAlertDialog(this, R.string.clear_lb_title, R.string.clear_lbs_msg);

	}

	/**
	 * Clears the multiplayer leaderboard.
	 */
	public final void clearMulti(final View view) {
		Log.i("Options", "clearing multiplayer leaderboard");

		// Initialize the database
		Log.d("Options: Using Test Database", "" + TitlePage.useTestDB);
		if (TitlePage.useTestDB) {
			Parse.initialize(this, "E8hfMLlgnEWvPw1auMOvGVsrTp1C6eSoqW1s6roq",
			"hzPRfP284H5GuRzIFDhVxX6iR9sgTwg4tJU08Bez"); 
		} else {
			Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
			"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
		}
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Log.i("Options", "user begins logging in");
			buildPopup(false);
		} else {
			Log.i("Options", "user is logged in");

			MultiLeaderBoardModel ml;
			try {
				ml = new MultiLeaderBoardModel();
				ml.setPlayer(currentUser.getString("username"));
			} catch (InternetConnectionException e) {
				e.fillInStackTrace();
				Log.i("Options", "triggering internet connection error screen");
				Intent intent = new Intent(this, ErrorScreen.class);
				intent.putExtra("error", R.layout.activity_connection_error);
				startActivity(intent);
				return;
			}
			ml.clearLeaderboard();

			InterfaceUtils.buildAlertDialog(this, R.string.clear_lb_title, R.string.clear_lbm_msg);

		}
	}

	/**
	 * Called when the user clicks the "Main Menu" button.
	 * @param view The button clicked
	 */
	public final void goToTitlePage(final View view) {
		Log.i("Options", "back to title page from options");
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}


	private void buildPopup(boolean dismisspsw) {
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.options_layout);

		// inflate either the login layout
		lp.buildLoginPopup(layoutInflater, parentLayout, dismisspsw);
	}

	/**
	 * Handles what happens when user clicks the "Forgot your password" link
	 * @param view Button that is pressed
	 */
	public final void forgotPassword(View view) {
		Log.i("Options", "user has forgotten password");
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.options_layout);

		// inflate the password layout
		lp.buildResetPopup(layoutInflater, parentLayout);
	}

	/**
	 * Handles what happens when user clicks the login button
	 * @param view Button that is pressed
	 */
	public void loginButton(final View view) {
		// Try to login
		String usernameString;
		try {
			usernameString = lp.loginButton();
		} catch (InternetConnectionException e) {
			e.fillInStackTrace();
			Log.i("Options", "triggering internet connection error screen");
			Intent intent = new Intent(this, ErrorScreen.class);
			intent.putExtra("error", R.layout.activity_connection_error);
			startActivity(intent);
			return;
		}
		// If login was successful, go to the multiplayer game
		if (!usernameString.equals("")) {
			exitLoginPopup(view);
			clearMulti(view);
		}
	}

	/**
	 * Exits the login popup window
	 * @param view the button clicked
	 */
	public void exitLoginPopup(View view) {
		Log.i("Options", "user exits log in");
		lp.exitLoginPopup();
	}

	/**
	 * Exits the password popup window
	 * @param view the button clicked
	 */
	public void exitPasswordPopup(View view) {
		Log.i("Options", "user exits password popup");
		buildPopup(true);
	}

	/**
	 * Handles what happens when user wants to reset password.
	 * @param view the button clicked
	 */
	public void resetPassword(View view) {
		Log.i("Options", "user resets password");
		// Sort through the reset info
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		lp.resetPassword(alertDialogBuilder);   
		// Go back to the login popup
		buildPopup(true);
	}

	//Set the switch to the correct position
	private final void setCorrectPosition(Switch mySwitch, String fileName){
		try{
			FileInputStream fis = openFileInput(fileName);
			mySwitch.setChecked(true);
		} catch (IOException e){
			e.fillInStackTrace();
			mySwitch.setChecked(false);
		}
	}

	/**
	 * Goes to the Registration page
	 * @param view the button clicked
	 */
	public void goToRegister(View view) {
		Log.i("Options", "proceeding to register page");
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

	// TODO remove repetition from title page / post game / leaderboard
}
