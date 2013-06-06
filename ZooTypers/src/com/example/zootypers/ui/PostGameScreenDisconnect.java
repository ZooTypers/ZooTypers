package com.example.zootypers.ui;

import android.os.Bundle;
import android.util.Log;

/**
 * Post game activity / UI for a multiplayer game, when the opponent has disconneted.
 */
public class PostGameScreenDisconnect extends PostGameScreenMulti {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("Multiplayer", "opponent disconnected");

		onCreateHelper();
	}

	@Override
	protected void opponentDisplay() {
		// Do nothing
	}

}
