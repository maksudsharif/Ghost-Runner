package edu.virginia.cs.ghostrunner;

import android.app.Activity;
import android.content.Context;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		
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
		super.onPause();
		sensorManager.unregisterListener(sensorListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(sensorListener, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

}
