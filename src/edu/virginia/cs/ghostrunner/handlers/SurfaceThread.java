package edu.virginia.cs.ghostrunner.handlers;

import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import edu.virginia.cs.ghostrunner.entities.BigGhostsItem;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.SmallGhostsItem;
import edu.virginia.cs.ghostrunner.views.GameView;

public class SurfaceThread extends Thread {
	private SurfaceHolder sh;
	private GameView gameView;
	private boolean running = false;
	private String difficulty;
	private ArrayList<Float[]> scorePopUp;
	private Paint animPaint;

	public SurfaceThread(SurfaceHolder sh, GameView gameView) {
		this.sh = sh;
		this.gameView = gameView;
		difficulty = gameView.getDifficulty();
		scorePopUp = new ArrayList<Float[]>();
		animPaint = new Paint();
		animPaint.setColor(Color.BLACK);
	}

	public void addScorePopUp(Ghost g) {
		Float[] tmp = new Float[3];
		tmp[0] = g.getX();
		tmp[1] = g.getY();
		tmp[2] = g.getScoreValue();
		scorePopUp.add(tmp);
	}

	public void updateAnim(long delta) {
		if (delta > 1) {
			synchronized (scorePopUp) {
				Iterator<Float[]> iter = scorePopUp.iterator();
				while (iter.hasNext()) {
					iter.next();
					iter.remove();
				}
			}
		}
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
					// update ghosts

					if (gameView.size() < (15 * gameView
							.getGhostspawnconstant())) {
						if (Math.random() > (.96 * gameView
								.getGhostfrequencyconstant())
								&& Math.random() > (.40 * gameView
										.getGhostfrequencyconstant())) {
							gameView.add(new Ghost(
									(float) (Math.random() * gameView
											.getWidthPixels()), 0, gameView

									));
						}
<<<<<<< Updated upstream
					}
					// Spawn Items
					if (gameView.getItems().size() < 3) {
						// if (Math.random() > .40 && Math.random() > .40) {
						gameView.add(new SmallGhostsItem((float) Math.random()
								* gameView.getWidthPixels(), 0, gameView));
						Log.d("ItemAdded", "SmallGhostsItem");
						// }
					}

=======
						//Spawn Items
						if (gameView.getItems().size() < 3) {
							if (Math.random() > .96 && Math.random() > .90) { //spawn some item
								if (Math.random() > .90) {
									gameView.add(new SmallGhostsItem ((float) Math.random() * gameView.getWidthPixels(), 0, gameView));
									Log.d("ItemAdded" , "SmallGhostsItem");
								}
								if (Math.random() < .90) {
									gameView.add(new BigGhostsItem ((float) Math.random() * gameView.getWidthPixels(), 0, gameView));
									Log.d("ItemAdded" , "BigGhostsItem");
								}
							//}
							}
						}
				
>>>>>>> Stashed changes
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
