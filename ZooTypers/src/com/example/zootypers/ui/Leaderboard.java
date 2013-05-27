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
import com.example.zootypers.core.ScoreEntry;

@SuppressLint("NewApi")
public class Leaderboard extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the layout for the parent activity which contains the fragments
		setContentView(R.layout.activity_leaderboard);
		
		// set up the action bar for the different tabs
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab singlePlayerTab = actionBar.newTab().setText("Singleplayer");
		ActionBar.Tab multiPlayerTab = actionBar.newTab().setText("Multiplayer");
		ActionBar.Tab friendsLBTab = actionBar.newTab().setText("Friends");
		
		// get the list of scores from the model and send it to each of the tabs
		// NEEDS TO BE A MAP BECAUSE CANT SEND OBJECTS IN BUNDLES
		
		// Used for testing
		ScoreEntry[] se = new ScoreEntry[6];
		se[0] = new ScoreEntry("Lindsey", 10);
		se[1] = new ScoreEntry("Oak", 10);
		se[2] = new ScoreEntry("Chelsea", 10);
		se[3] = new ScoreEntry("David", 10);
		se[4] = new ScoreEntry("Wing", 10);
		se[5] = new ScoreEntry("Bryan", 10);
		
		ScoreEntry[] se2 = new ScoreEntry[1];
		se2[0] = new ScoreEntry("James", 100);

		Fragment singlePlayerFragment = SingleplayerTab.newInstance(se2);
		Fragment multiPlayerFragment = MultiplayerTab.newInstance(se);
		Fragment friendsLBFragment = FriendsLBTab.newInstance(se);
		
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
			fst.addToBackStack(null);
		    fst.commit();
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			// Do nothing
		}
	}
}

