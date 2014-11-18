package edu.virginia.cs.ghostrunner;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
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
	
	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Full-screen the Activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Create GameView
		gameView = new GameView(this);
		// Associate sensor
		sensorManager = ((SensorManager) getSystemService(Context.SENSOR_SERVICE));
		sensorListener = new MySensorListener(gameView);
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorListener, accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
		
		mp = MediaPlayer.create(Game.this, R.raw.smb3hammer);
		mp.setLooping(true);
		mp.start();

		setContentView(gameView);
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
	protected void onStop() {
		mp.stop();
		super.onStop();
	}

	public GameView getGameView() {
		return gameView;
	}

}
