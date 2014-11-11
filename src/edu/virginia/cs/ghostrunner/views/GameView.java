package edu.virginia.cs.ghostrunner.views;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import edu.virginia.cs.ghostrunner.GameOver;
import edu.virginia.cs.ghostrunner.entities.Entity;
import edu.virginia.cs.ghostrunner.entities.Ghost;
import edu.virginia.cs.ghostrunner.entities.Player;
import edu.virginia.cs.ghostrunner.handlers.SurfaceThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener {
	private Paint p;
	private Paint sPaint;
	
	private DisplayMetrics dm;
	// private Game game; //Not sure if this is needed anymore
	private SurfaceThread thread;

	private Player player;
	private ArrayList<Entity> entities; //Should contain ghosts/items/etc
	
	private int currentScore;
	private String score;
	
	private String difficulty;
	//changed with difficulty
	private double ghostspawnconstant;
	private double ghostfrequencyconstant;
	private double ghostspeedconstant;
	 
	
	
	private void init() {
		p = new Paint();
		//Game game = (Game) getContext(); // Not sure this even works and is
											// probably dangerous to assume the
											// context is a Game
		getHolder().addCallback(this); //Needed for SurfaceView to render
		setOnTouchListener(this);

		//setWillNotDraw(false); // Not needed unless you want SurfaceView to call
								// onDraw instead of calling own methods

		player = new Player(dm.widthPixels / 2, dm.heightPixels / 2, this);
		entities = new ArrayList<Entity>();
		
		currentScore = 0;
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
		difficulty = ((Activity)context).getIntent().getStringExtra("difficulty");
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		dm = context.getResources().getDisplayMetrics();
		difficulty = ((Activity)context).getIntent().getStringExtra("difficulty");
		init();
	}

	public GameView(Context context) {
		super(context);
		dm = context.getResources().getDisplayMetrics();
		difficulty = ((Activity)context).getIntent().getStringExtra("difficulty");
		Log.v("GV INTENT", "GameView diff: "+difficulty);
		init();
	}

	/*
	 * Getters and Setters
	 */
	public int size() {
		return entities.size();
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
	public String getDifficulty(){
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


	/*
	 * Other helper methods
	 */
	public void add(Entity g) {
		entities.add(g);
	}
	
	/*
	 * Bounds
	 */
	public void checkBounds() {
		Iterator<Entity> iter = entities.iterator();
		Rect playerRect;
		while (iter.hasNext()) {
			Entity tmp = iter.next();
			playerRect = player.getRect();
			/*
			 * Start GameOver Activity
			 */
			if (playerRect.intersect(tmp.getRect())) {
				stop(); // Stop Thread
				Log.v("STOP", "THREAD STOPPED");
				Intent intent = new Intent(getContext(), GameOver.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
			}
			/*
			 * Remove Ghosts logic
			 */
			if (tmp.getY() > dm.heightPixels) {
				iter.remove();
				Log.v("ENTITY", "ghost removed");
				currentScore++;
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

		// Check bounds
		checkBounds();

		// Draw player
		player.draw(c);
		
		//Draw Entities
		for (Entity e : entities) {
			e.draw(c);

		}
		
		//TODO: Draw score - MOVE THIS OUT TO A VIEW AND ADD IT TO ACTIVITY
		score = "Score: " + currentScore;
		c.drawText(score, 0, score.length(), (float) (this.getMeasuredWidth()
				/2) , (float) (.05 * this.getMeasuredHeight()), sPaint);

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
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("SURFACE", "Surface Destroyed");
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		// Probably should make this Synchronized
		// Check if the click lands within a ghost
		for (Entity e : entities) {
			if (e instanceof Ghost) {
				Rect tmp = e.getRect();
				if (tmp.contains(x, y)) {
					return performClick((Ghost) e);
				}
			}
		}
		v.performClick(); // Required for some reason
		return false;

	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	public boolean performClick(Ghost g) {
		super.performClick();
		entities.remove(g); // Possible synchronization problems
		currentScore += 5;
		return true;
	}
}
