package edu.virginia.cs.ghostrunner.views;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.entities.AnimatedEntity;
import edu.virginia.cs.ghostrunner.entities.Entity;
import edu.virginia.cs.ghostrunner.entities.Ghost;
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

	private ArrayList<Entity> ghosts; // Should contain ghosts and friendly
										// ghosts
	private ArrayList<Item> items; // Should contain items

	private int currentScore;
	private int lastScore;
	private String score;

	private String difficulty;
	// changed with difficulty
	private double ghostspawnconstant;
	private double ghostfrequencyconstant;
	private double ghostspeedconstant;

	// scores
	private static double SCORECONSTANT = 1;
	private static ArrayList<Integer> scores = new ArrayList<Integer>();

	private void init() {
		// Test animations
		Bitmap ghost1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				getContext().getResources(), R.drawable.ghostanim), (int) (this
				.getMeasuredWidth() * 0.3),
				(int) (this.getMeasuredHeight() * 0.3), true);
		/*
		 * Animated ghost test
		 * ------------------------------------------------------------------------------------------------------------------
		 */
		aGhost = new AnimatedEntity(ghost1,
				this.getMeasuredWidth() / 2,  //pos_x
				this.getMeasuredHeight() / 2, //pos_y
				32,32, 						  //sprite width/height (doesn't really matter because it is resized later)
				5, 14, 						  //FPS of the animation, number of total "frames" or images per spritesheet
				this						  //GameView reference
				);
		/*
		 * ------------------------------------------------------------------------------------------- ---------------------
		 */
		p = new Paint();
		// Game game = (Game) getContext(); // Not sure this even works and is
		// probably dangerous to assume the
		// context is a Game
		getHolder().addCallback(this); // Needed for SurfaceView to render
		setOnTouchListener(this);

		// setWillNotDraw(false); // Not needed unless you want SurfaceView to
		// call
		// onDraw instead of calling own methods
		tf = Typeface.createFromAsset(getContext().getAssets(),
				"fonts/font.TTF");

		player = new Player(dm.widthPixels / 2, dm.heightPixels / 2, this);
		ghosts = new ArrayList<Entity>();
		items = new ArrayList<Item>();

		currentScore = 0;
		lastScore = 0;
		score = "";
		sPaint = new Paint();
		sPaint.setColor(Color.BLACK);
		sPaint.setTextSize(100);
		sPaint.setTextAlign(Paint.Align.CENTER);

		if (difficulty.equals("EASY")) {
			ghostspawnconstant = 1;
			ghostspeedconstant = 1;
			ghostfrequencyconstant = 1;
		}
		if (difficulty.equals("MEDIUM")) {
			ghostspawnconstant = 1.3;
			ghostspeedconstant = 1.5;
			ghostfrequencyconstant = 1;
		}
		if (difficulty.equals("HARD")) {
			ghostspawnconstant = 1.5;
			ghostspeedconstant = 2;
			ghostfrequencyconstant = .98;
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

	public double getGhostspawnconstant() {
		return ghostspawnconstant;
	}

	public void setGhostspawnconstant(double ghostspawnconstant) {
		this.ghostspawnconstant = ghostspawnconstant;
	}

	public double getGhostspeedconstant() {
		return ghostspeedconstant;
	}

	public void setGhostspeedconstant(double ghostspeedconstant) {
		this.ghostspeedconstant = ghostspeedconstant;
	}

	public double getGhostfrequencyconstant() {
		return ghostfrequencyconstant;
	}

	public void setGhostfrequencyconstant(double ghostfrequencyconstant) {
		this.ghostfrequencyconstant = ghostfrequencyconstant;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Entity> getGhosts() {
		return ghosts;
	}

	public static double getSCORECONSTANT() {
		return SCORECONSTANT;
	}

	public static void setSCORECONSTANT(double sCORECONSTANT) {
		SCORECONSTANT = sCORECONSTANT;
	}

	/*
	 * Other helper methods
	 */
	public void add(Entity e) {
		if (e instanceof Ghost) {
			ghosts.add(e);
		}
		if (e instanceof Item) {
			items.add((Item) e);
		}
	}

	/*
	 * Bounds
	 */
	public void checkBounds() {
		Iterator<Entity> iter = ghosts.iterator();
		Rect playerRect;
		while (iter.hasNext()) {
			Entity tmp = iter.next();
			playerRect = player.getRect();
			/*
			 * Start GameOver Activity
			 */
			if (playerRect.intersect(tmp.getRect())) {
				stop(); // Stop Thread
				if (currentScore != 0)
					scores.add(currentScore);
				Log.v("STOP", "THREAD STOPPED");
				Intent intent = new Intent(getContext(), GameOver.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putIntegerArrayListExtra("scores", scores);
				getContext().startActivity(intent);
			}

			/*
			 * Remove Ghosts logic
			 */
			if (tmp.getY() > dm.heightPixels) {
				iter.remove();
				Log.v("ENTITY", "ghost removed");
				currentScore += 1 * GameView.SCORECONSTANT;
			}
		}
		Iterator<Item> iter2 = items.iterator();
		while (iter2.hasNext()) {
			Item tmp2 = iter2.next();
			playerRect = player.getRect();
			/*
			 * Start the items intersected method
			 */
			if (playerRect.intersect(tmp2.getRect())) {
				tmp2.intersected();
				// iter2.remove();

			}
			/*
			 * remove item logic
			 */
			if (tmp2.getY() > dm.heightPixels) {
				iter2.remove();
				Log.v("ENTITY", "item removed");
			}
		}

	}

	public void stop() {
		thread.setRunning(false);
	}

	public void onDraw(Canvas c) {
		super.onDraw(c);

		// Draw Background
		c.drawColor(0xFFCC9900);

		//draw test animation entity
		aGhost.draw(c);
		// Check bounds
		checkBounds();

		// Draw player
		player.draw(c);

		// Draw Entities
		synchronized (ghosts) {
			for (Entity e : ghosts) {
				e.draw(c);

			}
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
		// Probably should make this Synchronized
		// Check if the click lands within a ghost
		for (Entity g : ghosts) {
			// if (e instanceof Ghost) { //not necessary anymore, handle ghosts
			// and items separately
			Rect tmp = g.getRect();
			if (tmp.contains(x, y)) {
				return performClick((Ghost) g);
			}
			// }
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
			thread.addScorePopUp((Ghost) e);
			ghosts.remove(e); // Possible synchronization problems
			currentScore += 5 * GameView.SCORECONSTANT;
			lastScore = 5;
		}
		return true;
	}
	public void resetConstants() {
		if (difficulty.equals("EASY")) {
			ghostspawnconstant = 1;
			ghostspeedconstant = 1;
			ghostfrequencyconstant = 1;
		}
		if (difficulty.equals("MEDIUM")) {
			ghostspawnconstant = 1.3;
			ghostspeedconstant = 1.5;
			ghostfrequencyconstant = 1;
		}
		if (difficulty.equals("HARD")) {
			ghostspawnconstant = 1.5;
			ghostspeedconstant = 2;
			ghostfrequencyconstant = .98;
		}
		GameView.setSCORECONSTANT(1);
		Player.setSCALE(0.035);
		Ghost.setSCALE(0.035);
		Ghost.setSPEED(.01);
	}
}
