package com.example.zootypers.ui;




import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zootypers.R;
import com.example.zootypers.core.ScoreEntry;
import com.parse.ParseUser;

/**
 * A Fragment Tab that shows the Friends Leaderboard
 * @author ZooTypers
 *
 */
public class FriendsLBTab extends LeaderboardTab {
	
	/**
	 * creates a view for the fragment using the friends_lb_tab layout
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		// set the layout for the fragment and get the arguments for that are passed
		View friendsView = inflater.inflate(R.layout.friends_lb_tab, container, false);
		ScoreEntry[] seArray = (ScoreEntry[]) getArguments().getParcelableArray("scoreList");
		// set up the leaderboard
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			Leaderboard.buildFriendPopup(false, getActivity());
		}
		setupLBList(friendsView, seArray);
		return friendsView;
	}

	/**
	 * Create a new instance of FriendsLBTab with the scores as a param
	 * @param seArray an array of scoreEntrys that have the score of each player
	 * @return
	 */
	public static FriendsLBTab newInstance(ScoreEntry[] seArray) {
		FriendsLBTab spt = new FriendsLBTab();
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putParcelableArray("scoreList", seArray);
		spt.setArguments(args);
		return spt;
	}
}
