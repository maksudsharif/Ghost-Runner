package edu.virginia.cs.ghostrunner.handlers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import edu.virginia.cs.ghostrunner.views.GameView;

public class MySensorListener implements SensorEventListener {

	private GameView gameView;

	public MySensorListener(GameView gameView) {
		this.gameView = gameView;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor mySensor = event.sensor;

		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			float x = event.values[0];
			float y = event.values[1];

			// Set the positions based on accelerometer values and bound those
			// values
			gameView.getPlayer()
					.setX(gameView.getPlayer().getX() - (int) x * 6);
			if (gameView.getPlayer().getX() > gameView.getMeasuredWidth()) {
				gameView.getPlayer().setX(0);
			}
			if (gameView.getPlayer().getX() < 0) {
				gameView.getPlayer().setX(gameView.getMeasuredWidth());
			}

			gameView.getPlayer()
					.setY(gameView.getPlayer().getY() + (int) y * 6);
			if (gameView.getPlayer().getY() > gameView.getMeasuredHeight() - (gameView.getPlayer().getRect().height() /2)) {
				gameView.getPlayer().setY(gameView.getMeasuredHeight() - (gameView.getPlayer().getRect().height() /2));
			}
			if (gameView.getPlayer().getY() < 0+ (gameView.getPlayer().getRect().height() /2)) {
				gameView.getPlayer().setY(0 + (gameView.getPlayer().getRect().height() /2));
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}
