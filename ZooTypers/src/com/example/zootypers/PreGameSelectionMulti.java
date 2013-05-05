package com.example.zootypers;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

/**
 *
 * UI / Activity for pre-game selection screen for a multiplayer game.
 * @author cdallas
 *
 */
public class PreGameSelectionMulti extends Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pregame_selection_multi);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pregame_selection_multi, menu);
        return true;
    }

}
