package com.example.zootypers.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zootypers.R;

/**
 * Post game activity / UI for a multiplayer game, when the opponent has disconneted.
 */
public class PostGameScreenDisconnect extends PostGameScreenMulti {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i("Multiplayer", "opponent has disconnected!");

		onCreateHelper();
	}

	@Override
	protected void opponentDisplay() {
		// Do nothing
	}

}
