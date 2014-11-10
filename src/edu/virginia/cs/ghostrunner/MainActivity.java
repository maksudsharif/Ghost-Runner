package edu.virginia.cs.ghostrunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {
	private String difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		difficulty = "Easy";
		
	}
	public void startGame(View button) {
		Intent intent = new Intent(this, Game.class);
		intent.putExtra("difficulty", difficulty);
		startActivity(intent);
		Log.v("INTENT START", "intent started");
	}
}
