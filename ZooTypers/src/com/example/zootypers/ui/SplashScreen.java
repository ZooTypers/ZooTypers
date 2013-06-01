package com.example.zootypers.ui;

import com.example.zootypers.R;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class SplashScreen extends Activity{
	private final int SPLASH_DISPLAY_TIME = 1000;
	
	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					Intent mainIntent = new Intent(SplashScreen.this, TitlePage.class);
					SplashScreen.this.startActivity(mainIntent);
					
					SplashScreen.this.finish();
					
					overridePendingTransition(R.anim.fadein, R.anim.fadeout);
				}
		}, SPLASH_DISPLAY_TIME);

	}

	@Override
	public final boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
}
