package edu.virginia.cs.ghostrunner;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class GameOver extends Activity {
	private final String FILENAME = "scores_file";

	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mp = MediaPlayer.create(GameOver.this, R.raw.playerdied);
		mp.start();
		setContentView(R.layout.activity_game_over);
		
		//Load scores from file
		ArrayList<Integer> scores = load();
		Collections.sort(scores, Collections.reverseOrder());
		//Show scores somehow
		Log.v("GAME OVER", scores.toString());
		TextView scoreList = (TextView) findViewById(R.id.scoreList);
		scoreList.setText(scores.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_over, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Integer> load() {
		ArrayList<Integer> loaded = null;
		try {
			FileInputStream fi = openFileInput(FILENAME);
			ObjectInputStream os = new ObjectInputStream(fi);
			loaded = (ArrayList<Integer>) os.readObject();
			os.close();
			fi.close();
		} catch (Exception e) {
			Log.v("LOAD", "Score load failed");
			return null;
		}
		return loaded;

	}
	public void goBack(View button) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		Log.v("INTENT START", "intent started");
	}

}
