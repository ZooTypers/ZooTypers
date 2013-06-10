package com.example.zootypers.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.example.zootypers.util.InterfaceUtils;
import com.example.zootypers.util.InternetConnectionException;
import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Controls all of the action happening in the Leaderboard UI, switching tabs,
 * populating the leaderboard, etc
 * 
 * @author ZooTypers
 *
 */
@SuppressLint("NewApi")
public class Leaderboard extends FragmentActivity {
	private LoginPopup lp;
	private ParseUser currentUser;
	private SingleLeaderBoardModel lb;
	private MultiLeaderBoardModel mlb;
	private Fragment mainCurrentFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		Log.i("Leaderboard", "entered leaderboard");

		// set the layout for the parent activity which contains the fragments
		setContentView(R.layout.activity_leaderboard);

		// Initialize the database according to whether it's a test or not.
		Log.d("Leaderboard: Using Test Database", "" +TitlePage.useTestDB);
		if (TitlePage.useTestDB) { //The Testing Database on Parse
			Parse.initialize(this, "E8hfMLlgnEWvPw1auMOvGVsrTp1C6eSoqW1s6roq",
			"hzPRfP284H5GuRzIFDhVxX6iR9sgTwg4tJU08Bez"); 
		} else { //The Real App Database on Parse
			Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
			"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 
		}

		lp = new LoginPopup(currentUser);

		// set up the action bar for the different tabs
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab singlePlayerTab = actionBar.newTab().setText(R.string.single_player_tab);
		ActionBar.Tab multiPlayerTab = actionBar.newTab().setText(R.string.multi_player_tab);
		ActionBar.Tab relativeUserScoreTab = actionBar.newTab().setText(R.string.relative_tab);

		// get the list of scores from the model and send it to each of the tabs

		lb = new SingleLeaderBoardModel(getApplicationContext());
		try {
			mlb = new MultiLeaderBoardModel();
		} catch (InternetConnectionException e) {
			e.fillInStackTrace();
			Log.i("Leaderboard", "triggering internet connection error screen");
			Intent intent = new Intent(this, ErrorScreen.class);
			intent.putExtra("error", R.layout.activity_connection_error_lb);
			startActivity(intent);
			return;
		}

		//need to get the username to pass into the leaderboard
		Fragment singlePlayerFragment = SingleplayerTab.newInstance(lb.getTopScores());
		Fragment multiPlayerFragment = MultiplayerTab.newInstance("", mlb.getTopScores());
		Fragment relativeUserScoreFragment = RelativeUserScoreTab.emptyNewInstance();

		singlePlayerTab.setTabListener(new LBTabListener(singlePlayerFragment, "singleplayer"));
		multiPlayerTab.setTabListener(new LBTabListener(multiPlayerFragment, "multiplayer"));
		relativeUserScoreTab.setTabListener(new LBTabListener(relativeUserScoreFragment, "relative"));

		actionBar.addTab(singlePlayerTab);
		actionBar.addTab(multiPlayerTab);
		actionBar.addTab(relativeUserScoreTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_leaderboard, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		Log.i("Leaderboard", "back to title page from leaderboard");
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}
	/**
	 * Called when the user wants to view his/her score relative to other players
	 * @param view the button clicked
	 */
	public void relativeUserScore() {
		// set up the Parse database and have the user log in if not already
		currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Log.i("Leaderboard", "user begins logging in");
			buildPopup(false);
		} else {
			Log.i("Leaderboard", "user is logged in");
			
			// make a new MultiLeaderBoardModel with the given username
			try {
				mlb.setPlayer(currentUser.getString("username"));
			} catch (InternetConnectionException e) {
				e.fillInStackTrace();
				Log.i("Leaderboard", "triggering internet connection error screen");
				Intent intent = new Intent(this, ErrorScreen.class);
				intent.putExtra("error", R.layout.activity_connection_error_lb);
				startActivity(intent);
				return;
			}
			int userRank = mlb.getRank();
			// inform the user that he/she has no scores yet
			
			if (userRank <= 0) {
				InterfaceUtils.buildAlertDialog(this, R.string.no_scores_title, R.string.no_scores_msg);
				return;
			}
			int highestRank = mlb.getHighestRelScoreRank();
			// get the relative position of the user with the passed in NUM_RELATIVE
			ScoreEntry[] relativeEntrys = mlb.getRelativeScores();

			// add the relativeScore tab
			Fragment currentFragment = RelativeUserScoreTab.newInstance(relativeEntrys, userRank, highestRank);
			FragmentTransaction fst = getSupportFragmentManager().beginTransaction();
			fst.replace(R.id.leaderboard_layout, currentFragment);
			fst.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fst.commit();
		}
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
			Log.i("Leaderboard", "triggering internet connection error screen");
			Intent intent = new Intent(this, ErrorScreen.class);
			intent.putExtra("error", R.layout.activity_connection_error_lb);
			startActivity(intent);
			return;
		}
		// If login was successful, go to the multiplayer game
		if (!usernameString.equals("")) {
			exitLoginPopup(view);
			relativeUserScore();
		}
	}

	/**
	 * Builds a popup window for the login popup
	 * @param dismisspsw If the password popup is currently being displayed
	 * (and therefore should be dismissed)
	 */
	private void buildPopup(boolean dismisspsw) {
		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext()
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.leaderboard_layout);

		// inflate either the login layout
		lp.buildLoginPopup(layoutInflater, parentLayout, dismisspsw);
	}

	/**
	 * Exits the login popup window
	 * @param view the button clicked
	 */
	public void exitLoginPopup(View view) {
		Log.i("Leaderboard", "user exits log in");
		lp.exitLoginPopup();
		// add the relativeScore tab
		FragmentTransaction fst = getSupportFragmentManager().beginTransaction();
		fst.replace(R.id.leaderboard_layout, mainCurrentFragment);
		fst.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fst.commit();
	}

	/**
	 * Exits the password popup window
	 * @param view the button clicked
	 */
	public void exitPasswordPopup(View view) {
		Log.i("Leaderboard", "user exits password popup");
		buildPopup(true);
		FragmentTransaction fst = getSupportFragmentManager().beginTransaction();
		fst.replace(R.id.leaderboard_layout, mainCurrentFragment);
		fst.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fst.commit();
	}

	/**
	 * Handles what happens when user clicks the "Forgot your password" link
	 * @param view Button that is pressed
	 */
	public final void forgotPassword(View view) {
		Log.i("Leaderboard", "user has forgotten password");

		// set up the layout inflater to inflate the popup layout
		LayoutInflater layoutInflater =
		(LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

		// the parent layout to put the layout in
		ViewGroup parentLayout = (ViewGroup) findViewById(R.id.leaderboard_layout);

		// inflate the password layout
		lp.buildResetPopup(layoutInflater, parentLayout);
	}

	/**
	 * Handles what happens when user wants to reset password.
	 * @param view the button clicked
	 */
	public void resetPassword(View view) {
		Log.i("Leaderboard", "user resets password");
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
		Log.i("Leaderboard", "proceeding to register page");
		Intent registerIntent = new Intent(this, RegisterPage.class);
		startActivity(registerIntent);
	}

	public void goToMain(View view) {
		Log.i("Leaderboard", "back to title page from leaderboard");
		Intent intent = new Intent(this, TitlePage.class);
		startActivity(intent);
	}
	
	/**
	 * Class to handle the actions for each of the tabs in the action bar
	 * @author ZooTypers
	 *
	 */
	private class LBTabListener implements ActionBar.TabListener {

		private Fragment currentFragment;
		private String tag;
		/**
		 * set the current fragment the class is listening on
		 * @param fragment
		 */
		public LBTabListener(Fragment fragment, String tag) {
			currentFragment = fragment;
			this.tag = tag;
		}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// Do nothing
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			// begin a fragment transaction and replace the current transaction

			if (tag.equals("relative")) {
				mainCurrentFragment = currentFragment;
				relativeUserScore();
				return;
			}
			FragmentTransaction fst = getSupportFragmentManager().beginTransaction();
			fst.replace(R.id.leaderboard_layout, currentFragment);
			fst.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			fst.commit();
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// Do nothing
		}
	}

	// method used for testing. Would not be in the actual game
	public SingleLeaderBoardModel getSingleLeaderboard() {
		return lb;
	}

	// method used for testing. Would not be in the actual game
	public MultiLeaderBoardModel getMultiLeaderboard() {
		return mlb;
	}
}
