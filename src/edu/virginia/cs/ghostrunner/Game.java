package edu.virginia.cs.ghostrunner;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import edu.virginia.cs.ghostrunner.handlers.mySensorListener;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Game extends Activity {
	private SensorManager sensorManager;
	private Sensor accelerometerSensor;
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
		accelerometerSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(new mySensorListener(gameView), accelerometerSensor,
				SensorManager.SENSOR_DELAY_GAME);
		setContentView(gameView);

		//thread = new GameThread(gameView, this);
		//thread.execute(gameView);
	}
	
	public GameView getGameView() {
		return gameView;
	}


	// TODO Implement for touch listener
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return false;

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
