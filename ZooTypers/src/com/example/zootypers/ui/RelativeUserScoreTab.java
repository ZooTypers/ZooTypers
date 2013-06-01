package com.example.zootypers.ui;




import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zootypers.R;
import com.example.zootypers.core.MultiLeaderBoardModel;
import com.example.zootypers.core.ScoreEntry;
import com.example.zootypers.util.InternetConnectionException;
import com.parse.ParseUser;

/**
 * Shows the user's score relative to other users
 * @author ZooTypers
 *
 */
public class RelativeUserScoreTab extends LeaderboardTab {
	
	ParseUser currentUser;
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
		if (rank != -1 && relativeRank != -1 && seArray.length != 0) {
			// means that it is not an empty instanc
			setupLBList(relativeScoreView, seArray, rank, relativeRank);
		}
		return relativeScoreView;
	}
	
	/**
	 * Create a new empty instance of RelativeUserScoreTab with the scores as a param
	 * @return the new RelativeUserScoreTab with the arguments
	 */
	public static RelativeUserScoreTab emptyNewInstance() {
		RelativeUserScoreTab spt = new RelativeUserScoreTab();
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putInt("userRank", -1);
		args.putInt("relativeRank", -1);
		args.putParcelableArray("scoreList", new ScoreEntry[0]);
		spt.setArguments(args);
		return spt;
	}
	
	/**
	 * Create a new instance of RelativeUserScoreTab with the scores as a param
	 * @param seArray the array used to fill the relative leaderboard
	 * @param userRank the rank of the user to find the relative Rank
	 * @param relativeRank how much to find the relative score of the user by
	 * @return the new RelativeUserScoreTab with the arguments
	 */
	public static RelativeUserScoreTab newInstance(ScoreEntry[] seArray, int userRank,
			int relativeRank) {
		RelativeUserScoreTab spt = new RelativeUserScoreTab();
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putInt("userRank", userRank);
		args.putInt("relativeRank", relativeRank);
		args.putParcelableArray("scoreList", seArray);
		args.putBoolean("first", true);
		spt.setArguments(args);
		return spt;
	}

}
