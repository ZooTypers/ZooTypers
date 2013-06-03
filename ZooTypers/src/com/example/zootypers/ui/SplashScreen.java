package com.example.zootypers.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.example.zootypers.R;


public class SplashScreen extends Activity{
	private final int SPLASH_DISPLAY_TIME = 2000;

	@Override
	protected final void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ZooTypers", "displaying splash screen");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
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
