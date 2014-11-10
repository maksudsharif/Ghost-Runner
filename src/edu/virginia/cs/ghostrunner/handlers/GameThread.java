package edu.virginia.cs.ghostrunner.handlers;

import android.os.AsyncTask;
import edu.virginia.cs.ghostrunner.Game;
import edu.virginia.cs.ghostrunner.views.GameView;
/*
 * DON'T USE THIS CLASS, IT HAS BEEN REPLACED WITH SURFACETHREAD
 * I'M KEEPING THIS HERE JUST INCASE WE NEED TO REVERT.
 */
public class GameThread extends AsyncTask<GameView, GameView, Double> {

	/*
	 * @Override public void run() { while (run) { Canvas draw = null; try {
	 * draw = gv.getHolder().lockCanvas(); synchronized (gv.getHolder()) { if
	 * (draw != null) { gv.draw(draw); Log.v("GAME_THREAD_DRAW",
	 * "game thread draw complete"); } } sleep(40); } catch
	 * (InterruptedException e) {
	 * 
	 * } finally { if (draw != null) { gv.getHolder().unlockCanvasAndPost(draw);
	 * } } } }
	 */

	@SuppressWarnings("unused")
	private GameView gameView;
	@SuppressWarnings("unused")
	private Game game;

	public GameThread(GameView gameView, Game game) {
		super();
		this.gameView = gameView;
		this.game = game;
	}

	@Override
	protected Double doInBackground(GameView... params) {
		while (!this.isCancelled()) { // while not cancelled
			this.publishProgress(params);
			try {
				Thread.sleep(1000); // 1sec // sleep (wait) one second
			} catch (InterruptedException e) {

			}
		}
		return 0.0;
	}

	@Override
	protected void onProgressUpdate(GameView... params) {
		for (GameView dp : params) {
//			if (Math.random() > .98 && Math.random() > .80
//					&& dp.getEntities().size() < 12) {
//				dp.getEntities().add(new Ghost(0.0f, 0.0f, dp));
//			}
			dp.invalidate(); // re-draw (delete current and replace)
								// current image is no longer valid
		}
	}
}
