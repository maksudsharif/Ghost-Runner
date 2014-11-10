package edu.virginia.cs.ghostrunner.views;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.virginia.cs.ghostrunner.GameOver;
import edu.virginia.cs.ghostrunner.entities.Entity;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.Player;
import edu.virginia.cs.ghostrunner.handlers.SurfaceThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private Paint p;
	private DisplayMetrics dm;
	// private Game game; //Not sure if this is needed anymore
	private SurfaceThread thread;

	private Player player;
	private ArrayList<Entity> entities;

	private void init() {
		p = new Paint();
		//Game game = (Game) getContext(); // Not sure this even works and is
											// probably dangerous to assume the
											// context is a Game
		getHolder().addCallback(this);
		Log.v("GAME_VIEW", "Metrics: " + dm.widthPixels + ", "
				+ dm.heightPixels);

		// setWillNotDraw(false);

		player = new Player(dm.widthPixels / 2, dm.heightPixels / 2, this);
		entities = new ArrayList<Entity>();
		// entities.add(new Ghost(0, 0, this));

		thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
	}

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		dm = context.getResources().getDisplayMetrics();
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		dm = context.getResources().getDisplayMetrics();

		init();
	}

	public GameView(Context context) {
		super(context);
		dm = context.getResources().getDisplayMetrics();
		init();
	}

	public Player getPlayer() {
		return player;
	}

	public Paint getPaint() {
		return p;
	}

	public void add(Ghost g) {
		entities.add(g);
	}

	public int size() {
		return entities.size();
	}

	public int getWidthPixels() {
		return dm.widthPixels;
	}

	public int getHeightPixels() {
		return dm.heightPixels;
	}

	public void checkBounds() {
		Iterator<Entity> iter = entities.iterator();
		while (iter.hasNext()) {
			Entity tmp = iter.next();
			if (player.getRect().intersect(tmp.getRect())) {
				stop();
				Log.v("STOP", "THREAD STOPPED");
				Intent intent = new Intent(getContext(), GameOver.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
			}
			if (tmp.getY() > dm.heightPixels) {
				iter.remove();
			}
		}
	}

	public void stop() {
		thread.setRunning(false);
	}

	public void onDraw(Canvas c) {
		super.onDraw(c);
		c.drawColor(0xFFCC9900);
		player.draw(c);
		for (Entity e : entities) {
			e.draw(c);
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (thread.getState() == Thread.State.TERMINATED) {
			thread = new SurfaceThread(getHolder(), this);
		}
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {

			}
		}
	}
}
