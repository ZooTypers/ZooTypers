package com.example.zootypers.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zootypers.R;
import com.example.zootypers.core.ScoreEntry;

/**
 * Shows the user's score relative to other users
 * @author ZooTypers
 *
 */
public class RelativeUserScoreTab extends LeaderboardTab {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		// set the layout for the fragment and get the arguments for that are passed
		View relativeScoreView = inflater.inflate(R.layout.relative_score_layout, container, false);
		ScoreEntry[] seArray = (ScoreEntry[]) getArguments().getParcelableArray("scoreList");
		// set up the leaderboard
		int rank = getArguments().getInt("userRank");
		int relativeRank = getArguments().getInt("relativeRank");
		setupLBList(relativeScoreView, seArray, rank, relativeRank);
		return relativeScoreView;
	}
	
	/**
	 * Create a new instance of RelativeUserScoreTab with the scores as a param
	 * @param scores
	 * @return
	 */
	public static RelativeUserScoreTab newInstance(ScoreEntry[] seArray, int userRank,
			int relativeRank) {
		RelativeUserScoreTab spt = new RelativeUserScoreTab();
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putInt("userRank", userRank);
		args.putInt("relativeRank", relativeRank);
		args.putParcelableArray("scoreList", seArray);
		spt.setArguments(args);
		return spt;
	}

}
