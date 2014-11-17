package edu.virginia.cs.ghostrunner;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class GameOver extends Activity {
	private ArrayList<Integer> scores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game_over);
		load();
		// TODO: show scores somehow
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

	public void load() {
		SharedPreferences data = getSharedPreferences("data", 0);
		scores = new ArrayList<Integer>();
		String str = data.getString("scores", "");
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			scores.add(Integer.parseInt(st.nextToken()));
		}
	}
}
