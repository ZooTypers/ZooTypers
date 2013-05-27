package com.example.zootypers.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zootypers.R;
import com.example.zootypers.core.ScoreEntry;

/**
 * Represents a Singleplayer Tab in the Leaderboard
 * @author ZooTypers
 *
 */
public class SingleplayerTab extends LeaderboardTab {
	
	/**
	 * creates a view for the fragment using the singleplayer_tab layout
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		// set the layout for the fragment and get the arguments for that are passed
		View singleplayerView = inflater.inflate(R.layout.singleplayer_tab, container, false);
		ScoreEntry[] seArray = (ScoreEntry[]) getArguments().getParcelableArray("scoreList");
		// set up the leaderboard
		setupLBList(singleplayerView, seArray);
		return singleplayerView;
	}
	
	/**
	 * Create a new instance of SingleplayerTab with the scores as a param
	 * @param scores
	 * @return
	 */
	public static SingleplayerTab newInstance(ScoreEntry[] seArray) {
		SingleplayerTab spt = new SingleplayerTab();
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putParcelableArray("scoreList", seArray);
		spt.setArguments(args);
		return spt;
	}
}
