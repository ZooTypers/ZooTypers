package com.example.zootypers.ui;



import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.parse.ParseUser;

/**
 * Represents a Multiplayer Tab in the Leaderboard
 * @author ZooTypers
 *
 */
public class MultiplayerTab extends LeaderboardTab {
	
	private ParseUser currentUser;
	private static View currentView;
	private LoginPopup lp;
	/**
	 * creates a view for the fragment using the multiplayer_tab layout
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		// set the layout for the fragment and get the arguments for that are passed
		View multiplayerView = inflater.inflate(R.layout.multiplayer_tab, container, false);
		ScoreEntry[] seArray = (ScoreEntry[]) getArguments().getParcelableArray("scoreList");
		// set up the leaderboard
		lp = new LoginPopup(currentUser);
		currentView = multiplayerView;
		setupLBList(multiplayerView, seArray);
		//relativeUserScore(multiplayerView);
		return multiplayerView;
	}
	
	public static void relativePositionSetup(String username) {
	}
//	public void relativeUserScore(final View view) {
//		Button relativeButton = (Button) view.findViewById(R.id.relative_score_button);
//		relativeButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				buildPopup(view, false);
//				System.out.println("HELLO");
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//	}
	/**
	 * Create a new instance of MultiplayerTab with the scores as a param
	 * @param seArray an array of scoreEntrys that have the score of each player
	 * @return
	 */
	public static MultiplayerTab newInstance(String username, ScoreEntry[] seArray) {
		MultiplayerTab mpt = new MultiplayerTab();
		Bundle args = new Bundle();
		args.putParcelableArray("scoreList", seArray);
		mpt.setArguments(args);
		return mpt;
	}

//	private void buildPopup(View view, boolean dismisspsw) {
//	    // set up the layout inflater to inflate the popup layout
//	    LayoutInflater layoutInflater =
//	    (LayoutInflater) getActivity().getBaseContext()
//	    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//	    // the parent layout to put the layout in
//	    ViewGroup parentLayout = (ViewGroup) view.findViewById(R.id.multiplayer_tab_layout);
//
//	    // inflate either the login layout
//	    lp.buildLoginPopup(layoutInflater, parentLayout, dismisspsw);
//	  }
	  
//	  /**
//	   * Handles what happens when user clicks the "Forgot your password" link
//	   * @param view Button that is pressed
//	   */
//	  public final void forgotPassword(View view) {
//	    // set up the layout inflater to inflate the popup layout
//	    LayoutInflater layoutInflater =
//	    (LayoutInflater) getActivity().getBaseContext()
//	    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//	    // the parent layout to put the layout in
//	    ViewGroup parentLayout = (ViewGroup) findViewById(R.id.multiplayer_tab_layout);
//
//	    // inflate the password layout
//	    lp.buildResetPopup(layoutInflater, parentLayout);
//	  }
//	  
//	  /**
//	   * Handles what happens when user clicks the login button
//	   * @param view Button that is pressed
//	   */
//	  public void loginButton(final View view) {
//	    // Try to login
//	    String usernameString = lp.loginButton();
//	    // If login was successful, go to the multiplayer game
//	    if (!usernameString.equals("")) {
//	      exitLoginPopup(view);
//	      clearMulti(view);
//	    }
//	  }
//
//	  /**
//	   * Exits the login popup window
//	   * @param view the button clicked
//	   */
//	  public void exitLoginPopup(View view) {
//	    lp.exitLoginPopup();
//	  }
//
//	  /**
//	   * Exits the password popup window
//	   * @param view the button clicked
//	   */
//	  public void exitPasswordPopup(View view) {
//	    buildPopup(true);
//	  }
	
}
