package com.example.zootypers.ui;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.SingleLeaderBoardModel;
import com.parse.Parse;

@SuppressLint("NewApi")
public class Leaderboard extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the layout for the parent activity which contains the fragments
		setContentView(R.layout.activity_leaderboard);

		// Initialize the database
		Parse.initialize(this, "Iy4JZxlewoSxswYgOEa6vhOSRgJkGIfDJ8wj8FtM",
		"SVlq5dqYQ4FemgUfA7zdQvdIHOmKBkc5bXoI7y0C"); 

		
		// set up the action bar for the different tabs
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayUseLogoEnabled(false);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab singlePlayerTab = actionBar.newTab().setText("Singleplayer");
		ActionBar.Tab multiPlayerTab = actionBar.newTab().setText("Multiplayer");
		ActionBar.Tab friendsLBTab = actionBar.newTab().setText("Friends");
		
		// get the list of scores from the model and send it to each of the tabs
		// NEEDS TO BE A MAP BECAUSE CANT SEND OBJECTS IN BUNDLES
		
		SingleLeaderBoardModel lb = new SingleLeaderBoardModel(getApplicationContext());	
		//need to get the username to pass into the leaderboard
		MultiLeaderBoardModel mlb = new MultiLeaderBoardModel("bbbb");
		
		
		Fragment singlePlayerFragment = SingleplayerTab.newInstance(lb.getTopScores());
		Fragment multiPlayerFragment = MultiplayerTab.newInstance(mlb.getTopScores());
		Fragment friendsLBFragment = FriendsLBTab.newInstance(mlb.getTopScores());
		
		singlePlayerTab.setTabListener(new LBTabListener(singlePlayerFragment));
		multiPlayerTab.setTabListener(new LBTabListener(multiPlayerFragment));
		friendsLBTab.setTabListener(new LBTabListener(friendsLBFragment));
		
		actionBar.addTab(singlePlayerTab);
		actionBar.addTab(multiPlayerTab);
		actionBar.addTab(friendsLBTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_leaderboard, menu);
		return true;
	}

	/**
	 * Class to handle the actions for each of the tabs in the action bar
	 * @author ZooTypers
	 *
	 */
	private class LBTabListener implements ActionBar.TabListener {
		
		private Fragment currentFragment;

		/**
		 * set the current fragment the class is listening on
		 * @param fragment
		 */
		public LBTabListener(Fragment fragment) {
			currentFragment = fragment;
		}
		
		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
			// Do nothing
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			// begin a fragment transaction and replace the current transaction
		
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
}
