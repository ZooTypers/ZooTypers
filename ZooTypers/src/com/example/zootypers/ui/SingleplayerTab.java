package com.example.zootypers.ui;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
		Log.i("Leaderboard", "entered single player tab");

		super.onCreateView(inflater, container, savedInstanceState);

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
		return (SingleplayerTab) newInstanceHelper(seArray, new SingleplayerTab());
	}
}
