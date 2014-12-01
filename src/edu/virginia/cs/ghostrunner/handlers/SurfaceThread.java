package edu.virginia.cs.ghostrunner.handlers;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import edu.virginia.cs.ghostrunner.entities.BigGhostsItem;
import edu.virginia.cs.ghostrunner.entities.BigPlayerItem;
import edu.virginia.cs.ghostrunner.entities.BombItem;
import edu.virginia.cs.ghostrunner.entities.DoubleScoreItem;
import edu.virginia.cs.ghostrunner.entities.FastGhostsItem;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.GhostFriend;
import edu.virginia.cs.ghostrunner.entities.HalfScoreItem;
import edu.virginia.cs.ghostrunner.entities.SlowGhostsItem;
import edu.virginia.cs.ghostrunner.entities.SmallGhostsItem;
import edu.virginia.cs.ghostrunner.entities.SmallPlayerItem;
import edu.virginia.cs.ghostrunner.views.GameView;

public class SurfaceThread extends Thread {
	private SurfaceHolder sh;
	private GameView gameView;
	private boolean running = false;
	private Paint animPaint;

	public SurfaceThread(SurfaceHolder sh, GameView gameView) {
		this.sh = sh;
		this.gameView = gameView;
		animPaint = new Paint();
		animPaint.setColor(Color.BLACK);
	}

	public void setRunning(boolean b) {
		running = b;
	}

	@SuppressLint("WrongCall")
	public void run() {
		Canvas c;

		while (running) {
			c = null;
			try {
				c = sh.lockCanvas(null);
				synchronized (sh) {
					double spawn = gameView.getGhostSpawnConstant();
					double freq = gameView.getGhostFrequencyConstant();

					// update Animation Entities
					// gameView.aGhost.update(System.currentTimeMillis());

					// update ghosts
					if (gameView.size() < (15 * spawn)) {
						if (Math.random() > (.96 * freq)
								&& Math.random() > (.40 * freq)) {
							gameView.add(new Ghost(
									(float) (Math.random() * gameView
											.getWidthPixels()), -500, gameView));
						}
					}
					// Spawn Items
					if (gameView.getItems().size() < 7) {				
						//Test
						/*
						 * 
						 */
						if (Math.random() > .96 && Math.random() > .85) { // spawn
																			// some
																			// item
							/*
							 * handle the frequency each item spawns
							 */
							double rndItem = Math.random();
							float numItems = 10;
							if (rndItem < (1 / numItems)) {
								gameView.add(new SmallGhostsItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "SmallGhostsItem");
							}
							if (rndItem > (1 / numItems)
									&& rndItem < 2 * (1 / numItems)) {
								gameView.add(new BigGhostsItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "BigGhostsItem");
							}
							if (rndItem > 2 * (1 / numItems)
									&& rndItem < 3 * (1 / numItems)) {
								gameView.add(new BigPlayerItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "BigPlayerItem");
							}
							if (rndItem > 3 * (1 / numItems)
									&& rndItem < 4 * (1 / numItems)) {
								gameView.add(new SmallPlayerItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "SmallPlayerItem");
							}
							if (rndItem > 4 * (1 / numItems)
									&& rndItem < 5 * (1 / numItems)) {
								gameView.add(new FastGhostsItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "FastGhostsItem");
							}
							if (rndItem > 5 * (1 / numItems)
									&& rndItem < 6 * (1 / numItems)) {
								gameView.add(new SlowGhostsItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "SlowGhostsItem");
							}
							if (rndItem > 6 * (1 / numItems)
									&& rndItem < 7 * (1 / numItems)) {
								gameView.add(new DoubleScoreItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "DoubleScoreItem");
							}
							if (rndItem > 7 * (1 / numItems)
									&& rndItem < 8 * (1 / numItems)) {
								gameView.add(new HalfScoreItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "HalfScoreItem");
							}
							if (rndItem > 8 * (1 / numItems)
									&& rndItem < 9 * (1 / numItems)) {
								gameView.add(new BombItem((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "BombItem");
							}
							if (rndItem > 9 * (1 / numItems)
									&& rndItem < 10 * (1 / numItems)) {
								gameView.add(new GhostFriend((float) Math
										.random() * gameView.getWidthPixels(),
										0, gameView));
								Log.d("ItemAdded", "GhostFriend");
							}
						} 
					}

				}
				if (c != null)
					gameView.onDraw(c);
			} finally {
				if (c != null) {
					sh.unlockCanvasAndPost(c);
				}

			}
		}
	}
}
