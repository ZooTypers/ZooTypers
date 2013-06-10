package com.example.zootypers.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zootypers.core.ScoreEntry;

/**
 * Represents a Tab in the leaderboard layout
 * @author ZooTypers
 *
 */
public class LeaderboardTab extends Fragment {


	private final int MAX_LENGTH = 10;

	/**
	 * create a new view for the fragment
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		return container;
	}

	/**
	 * sets up the leaderboard list using the given array of entries
	 * @param view the leaderboard view
	 * @param seArray the list of entries to put in the leaderboard
	 */
	protected void setupLBList(View view, ScoreEntry[] seArray) {
		Log.i("Leaderboard", "setting up ranking list for single player or multiplayer");
		int size = seArray.length;
		if (seArray.length > 10) {
			size = MAX_LENGTH;
		}
		for (int i = 0; i < size; i++) {
			// get the text boxes with for the leaderboard
			TextView currentPlayerText = (TextView) getByStringId(view, 
					"player_name" + i);
			TextView currentScoreText = (TextView) getByStringId(view,
					"player_score" + i);

			// get the entries from the ScoreEntry
			ScoreEntry currentSE = seArray[i];
			String currentPlayer = currentSE.getName();
			String currentScore = currentSE.getScore() + "";

			// set the text fields
			currentPlayerText.setText(currentPlayer);
			currentScoreText.setText(currentScore);
		}
	}

	/**
	 * sets up the leaderboard list using the given array of entries, rank, and relativeRank.
	 * used for relative score fragment
	 * @param view the leaderboard view
	 * @param seArray the list of entries to put in the leaderboard
	 * @param rank the ranking position of the current user
	 * @param how many users before and after to show relative to the current user
	 */
	protected void setupLBList(View view, ScoreEntry[] seArray, int rank, int highestRank) {
		Log.i("Leaderboard", "setting up ranking list for relative scores");

		// find the position of the user to highlight

		int userPosition = rank - highestRank;
		int size = seArray.length;
		for (int j = 0; j < MAX_LENGTH; j++) {
			TextView currentRank = (TextView) getByStringId(view, "relative_rank" + j);
			String currentRankString = highestRank + "";
			if (j < size) {
				TextView currentPlayerText = (TextView) getByStringId(view, "player_name" + j);
				TextView currentScoreText = (TextView) getByStringId(view, "player_score" + j);

				// get the entries from the ScoreEntry
				ScoreEntry currentSE = seArray[j];
				String currentPlayer = currentSE.getName();
				String currentScore = currentSE.getScore() + "";
				if (j == userPosition) {
					// mark the current user
					currentPlayer = "<b>" + currentPlayer + "</b>";
					currentScore = "<b>" + currentScore + "</b>";
					currentRankString = "<b>" + highestRank + "</b>";
				}

				currentPlayerText.setText(Html.fromHtml(currentPlayer));
				currentScoreText.setText(Html.fromHtml(currentScore));
			}
			currentRank.setText(Html.fromHtml(currentRankString));
			highestRank++;
		}
	}
	/**
	 * @param id The id of the View to get as a String.
	 * @return The View object with that id
	 */
	private final View getByStringId(View view, final String id) {
		return view.findViewById(getResources().
				getIdentifier(id, "id", getActivity().getPackageName()));
	}

	/**
	 * Create a new instance of SingleplayerTab with the scores as a param
	 * @param scores
	 * @return
	 */
	protected static LeaderboardTab newInstanceHelper(ScoreEntry[] seArray, LeaderboardTab lbt) {
		// put the argument in a bundle that the fragment can use
		Bundle args = new Bundle();
		args.putParcelableArray("scoreList", seArray);
		lbt.setArguments(args);
		return lbt;
	}

}
