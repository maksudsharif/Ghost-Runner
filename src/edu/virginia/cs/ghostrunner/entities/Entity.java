package edu.virginia.cs.ghostrunner.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.virginia.cs.ghostrunner.views.GameView;

public abstract class Entity {
	protected Rect rect;
	protected float pos_x;
	protected float pos_y;
	protected GameView gameView;
	protected Bitmap bm;
	protected Paint p;
	protected String difficulty;
	protected static double SCALE = 0.035;
	protected static double SPEED = .01;

	protected Entity() {
	} // Don't call this constructor

	protected Entity(float x, float y, GameView gameView) {
		rect = new Rect(0, 0, 0, 0);
		pos_x = x;
		pos_y = y;
		this.gameView = gameView;
		p = null;
		difficulty = gameView.getDifficulty();
	}

	public Rect getRect() {
		return rect;
	}

	protected Bitmap getBitmap() {
		return bm;
	}

	public float getX() {
		return pos_x;
	}

	public float getY() {
		return pos_y;
	}

	public void setX(float x) {
		this.pos_x = x;
	}

	public void setY(float y) {
		this.pos_y = y;
	}

	public abstract void draw(Canvas c);

}
