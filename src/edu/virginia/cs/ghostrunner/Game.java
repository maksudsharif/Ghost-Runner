package edu.virginia.cs.ghostrunner;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import edu.virginia.cs.ghostrunner.handlers.MySensorListener;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Game extends Activity {
	private SensorManager sensorManager;
	private Sensor accelerometerSensor;
	private MySensorListener sensorListener;
	private GameView gameView;
	private String difficulty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		
		//Get difficulty
		//difficulty = getIntent().getStringExtra("difficulty");
		
		Log.v("DIFF","DIFF: "+difficulty);
		
		Log.v("GAME", "Creating GameView.");
		gameView = new GameView(this);
		Log.v("GAME", "GameView created.");
		
		sensorManager = ((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		sensorListener = new MySensorListener(gameView);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorListener, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
		setContentView(gameView);
	}
	
	public GameView getGameView() {
		return gameView;
	}

	@Override
	protected void onPause() {
		Log.v("Activity", "onPause() called Game Activity");
		super.onPause();
		sensorManager.unregisterListener(sensorListener);
	}

	@Override
	protected void onResume() {
		Log.v("Activity", "onResume() called Game Activity");
		super.onResume();
		sensorManager.registerListener(sensorListener, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
		gameView.resetConstants();
	}
	@Override
	protected void onStop(){
		super.onStop();
		ArrayList<Integer> scores = gameView.getScores();
		StringBuilder values = new StringBuilder();
		for(Integer i : scores){
			values.append(i).append(",");
		}
		SharedPreferences data = getSharedPreferences("data", 0);
		Editor ed = data.edit();
		ed.putString("scores", values.toString());
		ed.commit();
	}

}
