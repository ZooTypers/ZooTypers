package com.example.zootypers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SinglePlayerGame extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_player_game);
		
		// TODO : refactor into Singleplayer UI
		Drawable background = getResources().getDrawable(R.drawable.background_grassland);
		ViewGroup layout = (ViewGroup) findViewById(R.id.single_game_layout);
		layout.setBackground(background);
		
		Drawable animalImage = getResources().getDrawable(R.drawable.animal_giraffe);
		ImageView image = (ImageView) findViewById(R.id.animal_image);
		image.setImageDrawable(animalImage);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_single_player_game, menu);
		return true;
	}

	// dummy method for testing
	public void fillTexts(View view) {
		// testing that variables work
		String testString = "jamesiscool";
		TextView firstWord = (TextView) findViewById(R.id.word1);
		firstWord.setText(Html.fromHtml("<font color=green>" + testString + "</font>"));
		
		TextView secondWord = (TextView) findViewById(R.id.word2);
		secondWord.setText("Quetezecal");
		
		TextView thirdWord = (TextView) findViewById(R.id.word3);
		thirdWord.setText(Html.fromHtml("<font color=red>thisshouldbered</font>"));
		
		TextView fourthWord = (TextView) findViewById(R.id.word4);
		fourthWord.setText("hello");
		
		TextView fifthWord = (TextView) findViewById(R.id.word5);
		fifthWord.setText(Html.fromHtml("<font color=purple>gohuskies!</font>asdfadfsd"));
	}
}