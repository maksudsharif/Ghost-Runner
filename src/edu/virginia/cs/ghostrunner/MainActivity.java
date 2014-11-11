package edu.virginia.cs.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	private String extra;

	public enum Difficulty {
		EASY, MEDIUM, HARD
	}

	private Difficulty diff = Difficulty.EASY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		extra = "Easy";

	}

	public void startGame(View button) {
		Intent intent = new Intent(this, Game.class);
		intent.putExtra("difficulty", extra);
		startActivity(intent);
		Log.v("INTENT START", "intent started");
	}

	public void difficultyButtonClicked(View button) {
		button.animate().rotationX(360);
		button.refreshDrawableState();
		// TODO: Do something to change Difficulty
		if (diff == Difficulty.EASY) {
			diff = Difficulty.MEDIUM;
		} else if (diff == Difficulty.MEDIUM) {
			diff = Difficulty.HARD;
		} else
			diff = Difficulty.EASY;

		((Button) button).setText(String.valueOf(diff));
		extra = String.valueOf(((Button) button).getText());
	}
}
