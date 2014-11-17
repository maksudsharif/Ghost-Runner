package edu.virginia.cs.ghostrunner.views;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import edu.virginia.cs.ghostrunner.GameOver;
import edu.virginia.cs.ghostrunner.entities.AnimatedEntity;
import edu.virginia.cs.ghostrunner.entities.Entity;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.GhostFriend;
import edu.virginia.cs.ghostrunner.entities.Item;
import edu.virginia.cs.ghostrunner.entities.Player;
import edu.virginia.cs.ghostrunner.handlers.SurfaceThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,
		OnTouchListener {
	private Paint p;
	private Paint sPaint;
	private Typeface tf;

	private DisplayMetrics dm;
	// private Game game; //Not sure if this is needed anymore
	private SurfaceThread thread;

	private Player player;
	public AnimatedEntity aGhost;

	private CopyOnWriteArrayList<Entity> ghosts;

	private CopyOnWriteArrayList<Item> items; // Should contain items

	private final String FILENAME = "scores_file";
	private int currentScore;
	private int lastScore;
	private String score;

	private String difficulty;
	// changed with difficulty
	private double ghostSpawnConstant;
	private double ghostFrequencyConstant;
	private double ghostSpeedConstant;

	// scores
	private double scoreConstant = 1;
	private static ArrayList<Integer> scores = new ArrayList<Integer>();

	private void init() {
		scores = load();
		if (scores == null) {
			scores = new ArrayList<Integer>();
		}

		p = new Paint();

		getHolder().addCallback(this); // Needed for SurfaceView to render
		setOnTouchListener(this);

		// setWillNotDraw(false);

		tf = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/font.TTF");

		player = new Player(dm.widthPixels / 2, dm.heightPixels / 2, this);
		ghosts = new CopyOnWriteArrayList<Entity>();
		items = new CopyOnWriteArrayList<Item>();

		currentScore = 0;
		lastScore = 0;
		score = "";
		sPaint = new Paint();
		sPaint.setColor(Color.BLACK);
		sPaint.setTextSize(100);
		sPaint.setTextAlign(Paint.Align.CENTER);

		if (difficulty.equals("EASY")) {
			ghostSpawnConstant = 1;
			ghostSpeedConstant = 1;
			ghostFrequencyConstant = 1;
		}
		if (difficulty.equals("MEDIUM")) {
			ghostSpawnConstant = 1.3;
			ghostSpeedConstant = 1.5;
			ghostFrequencyConstant = 1;
		}
		if (difficulty.equals("HARD")) {
			ghostSpawnConstant = 1.5;
			ghostSpeedConstant = 2;
			ghostFrequencyConstant = .98;
		}

		thread = new SurfaceThread(getHolder(), this);
		setFocusable(true);
	}

	/*
	 * Constructors
	 */
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		dm = context.getResources().getDisplayMetrics();
		difficulty = ((Activity) context).getIntent().getStringExtra(
				"difficulty");
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		dm = context.getResources().getDisplayMetrics();
		difficulty = ((Activity) context).getIntent().getStringExtra(
				"difficulty");
		init();
	}

	public GameView(Context context) {
		super(context);
		dm = context.getResources().getDisplayMetrics();
		difficulty = ((Activity) context).getIntent().getStringExtra(
				"difficulty");
		Log.v("GV INTENT", "GameView diff: " + difficulty);
		init();
	}

	/*
	 * Getters and Setters
	 */
	public int size() {
		return ghosts.size();
	}

	public ArrayList<Integer> getScores() {
		return scores;
	}

	public int getWidthPixels() {
		return dm.widthPixels;
	}

	public int getHeightPixels() {
		return dm.heightPixels;
	}

	public Player getPlayer() {
		return player;
	}

	public Paint getPaint() {
		return p;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public double getGhostSpawnConstant() {
		return ghostSpawnConstant;
	}

	public void setGhostSpawnConstant(double ghostSpawnConstant) {
		this.ghostSpawnConstant = ghostSpawnConstant;
	}

	public double getGhostSpeedConstant() {
		return ghostSpeedConstant;
	}

	public void setGhostSpeedConstant(double ghostSpeedConstant) {
		this.ghostSpeedConstant = ghostSpeedConstant;
	}

	public double getGhostFrequencyConstant() {
		return ghostFrequencyConstant;
	}

	public void setGhostfrequencyconstant(double ghostFrequencyConstant) {
		this.ghostFrequencyConstant = ghostFrequencyConstant;
	}

	public CopyOnWriteArrayList<Item> getItems() {
		return items;
	}

	public CopyOnWriteArrayList<Entity> getGhosts() {
		return ghosts;
	}

	public CopyOnWriteArrayList<Entity> getSynced() {
		return ghosts;
	}

	public double getScoreConstant() {
		return scoreConstant;
	}

	public void setScoreConstant(double scoreConstant) {
		this.scoreConstant = scoreConstant;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	/*
	 * Other helper methods
	 */
	public void add(Entity e) {
		if (e instanceof Ghost) {
			ghosts.add(e);
		}
		if (e instanceof GhostFriend) {
			items.add((Item) e);
		}
		if (e instanceof Item) {
			items.add((Item) e);
		}
	}

	public void save() {
		try {
			FileOutputStream fo = getContext().openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fo);
			//Only show keep top 5
			if(scores.size() > 0){
				Collections.sort(scores);
				scores.remove(0);
			}

			os.writeObject(scores);
			os.close();
			fo.close();
		} catch (Exception e) {
			Log.v("SAVE", "Score save failed");
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Integer> load() {
		ArrayList<Integer> loaded = null;
		try {
			FileInputStream fi = getContext().openFileInput(FILENAME);
			ObjectInputStream os = new ObjectInputStream(fi);
			loaded = (ArrayList<Integer>) os.readObject();
			os.close();
			fi.close();
		} catch (Exception e) {
			Log.v("LOAD", "Score load failed");
			return null;
		}
		return loaded;

	}

	/*
	 * Bounds
	 */
	public void checkBounds() {
		// Iterator<Entity> iter = ghosts.iterator();

		Rect playerRect;
		/*
		 * while (iter.hasNext()) { Entity tmp = iter.next(); playerRect =
		 * player.getRect();
		 * 
		 * Start GameOver Activity
		 * 
		 * if (playerRect.intersect(tmp.getRect())) { stop(); // Stop Thread if
		 * (currentScore != 0) scores.add(currentScore); Log.v("STOP",
		 * "THREAD STOPPED"); Intent intent = new Intent(getContext(),
		 * GameOver.class); intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		 * intent.putIntegerArrayListExtra("scores", scores);
		 * getContext().startActivity(intent); }
		 * 
		 * 
		 * Remove Ghosts logic
		 * 
		 * if (tmp.getY() > dm.heightPixels) { iter.remove(); Log.v("ENTITY",
		 * "ghost removed"); currentScore += 1 * GameView.SCORECONSTANT; } }
		 */

		// Sync fix
		playerRect = player.getRect();
		for (Entity e : ghosts) {
			if (playerRect.intersect(e.getRect())) {
				stop();
				scores.add(currentScore);
				save();
				Intent intent = new Intent(getContext(), GameOver.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
			}

			if (e.getY() > dm.heightPixels) {
				ghosts.remove(e);
				Log.v("ENTITY", "ghost removed");
				currentScore += 2 * scoreConstant;
				lastScore = (int) (2 * scoreConstant);
			}
		}
		for (Item i : items) {
			playerRect = player.getRect();
			if (playerRect.intersect(i.getRect())) {
				i.intersected();
			}
			if (i.getY() > dm.heightPixels) {
				items.remove(i);
				Log.v("ENTITY", "item removed");
			}
		}

		// Iterator<Item> iter2 = items.iterator();
		// while (iter2.hasNext()) {
		// Item tmp2 = iter2.next();
		// playerRect = player.getRect();
		// /*
		// * Start the items intersected method
		// */
		// if (playerRect.intersect(tmp2.getRect())) {
		// tmp2.intersected();
		// // iter2.remove();
		//
		// }
		// /*
		// * remove item logic
		// */
		// if (tmp2.getY() > dm.heightPixels) {
		// iter2.remove();
		// Log.v("ENTITY", "item removed");
		// }
		// }

	}

	public void stop() {
		thread.setRunning(false);
	}

	public void onDraw(Canvas c) {
		super.onDraw(c);

		// Draw Background
		c.drawColor(Color.parseColor("#34495e"));

		// Check bounds
		checkBounds();

		// Draw player
		player.draw(c);

		// Draw Entities

		/*
		 * for (Entity e : ghosts) { e.draw(c); }
		 */

		for (Entity e : ghosts) {
			e.draw(c);
		}

		for (Item i : items) {
			i.draw(c);
		}

		score = "Score: " + currentScore;

		sPaint.setTextSize(35f);
		sPaint.setTypeface(tf);
		c.drawText(score, 0, score.length(),
				(float) (this.getMeasuredWidth() - score.length() * 15),
				(float) (Entity.SCALE * this.getMeasuredHeight()), sPaint);
		if (lastScore >= 0) {
			c.drawText("+ " + lastScore,
					(float) (this.getMeasuredWidth() - score.length() * 15),
					(float) (Entity.SCALE * this.getMeasuredHeight()) + 30,
					sPaint);
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!thread.isAlive() || thread == null) {
			thread = new SurfaceThread(getHolder(), this);
		}
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("SURFACE", "Surface Destroyed");
		stop();
		if (currentScore != 0)
			scores.add(currentScore);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		Log.v("TOUCH", "touch registered");
		// Probably should make this Synchronized
		// Check if the click lands within a ghost
		for (Entity g : ghosts) {
			Rect tmp = g.getRect();
			if (tmp.contains(x, y)) {
				return performClick((Ghost) g);
			}
		}
		for (Item i : items) {
			Rect tmp = i.getRect();
			if (tmp.contains(x, y)) {
				return performClick(i);
			}
		}

		v.performClick(); // Required for some reason
		return false;
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	public boolean performClick(Entity e) {
		super.performClick();
		if (e instanceof Ghost) {
			/*
			 * Make the string appear, make the ghost's rect 0x0
			 */
			ghosts.remove(e);
			Log.v("REMOVE", "ghost removed touch");
			currentScore += 5 * scoreConstant;
			lastScore = 5;
		}
		return true;
	}

	public void resetConstants() {
		if (difficulty.equals("EASY")) {
			ghostSpawnConstant = 1;
			ghostSpeedConstant = 1;
			ghostFrequencyConstant = 1;
		}
		if (difficulty.equals("MEDIUM")) {
			ghostSpawnConstant = 1.3;
			ghostSpeedConstant = 1.5;
			ghostFrequencyConstant = 1;
		}
		if (difficulty.equals("HARD")) {
			ghostSpawnConstant = 1.5;
			ghostSpeedConstant = 2;
			ghostFrequencyConstant = .98;
		}
		setScoreConstant(1);
		Player.setSCALE(0.035);
		Ghost.setSCALE(0.035);
		Ghost.setSPEED(.01);
	}
}
