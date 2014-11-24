package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Ghost extends Entity {
	
	protected static double SCALE = 0.035;
	protected static double SPEED = .01;
	String scoreDisplay;
	boolean DrawRect = true;
	int [] touchLocation;
	
	public Ghost() {
		super();
	}
	
	public Ghost(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.pos_x = (int) (Math.random() * gameView.getWidthPixels());
		this.pos_y = 0;
		this.p = new Paint();
		this.p.setColor(Color.BLACK);
		p.setStyle(Style.FILL);
		p.setStrokeWidth(3);
		p.setTextSize(gameView.getMeasuredHeight() * .03f);
		scoreDisplay = new String ();
		int [] touchLocation = new int [2];
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
	}
	
	
	

	@Override
	public void draw(Canvas c) {
		// update the ghosts position
		this.pos_y += (int) (gameView.getWidthPixels() * Ghost.SPEED) * gameView.getGhostSpeedConstant();
		// set the ghost's position based on updated values
		this.rect.set(
				(int) pos_x - (int) (gameView.getWidthPixels() * Ghost.SCALE),
				(int) pos_y - (int) (gameView.getWidthPixels() * Ghost.SCALE),
				(int) (gameView.getWidthPixels() * Ghost.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Ghost.SCALE) + (int) pos_y);// this sets the size of the rectangle
//		c.drawBitmap(bm, pos_x - 25, pos_y - 25, p);
		c.drawRect(this.rect, this.p);
	}
	public void drawString (Canvas c) {
		this.pos_y += (int) (gameView.getWidthPixels() * Ghost.SPEED) * gameView.getGhostSpeedConstant();
		c.drawText(scoreDisplay, touchLocation[0], touchLocation[1], this.p);
		c.drawRect(this.rect, this.p);
	}
	
	
	public static double getSPEED() {
		return SPEED;
	}

	public static void setSPEED(double sPEED) {
		SPEED = sPEED;
	}
	public static double getSCALE() {
		return SCALE;
	}

	public static void setSCALE(double sCALE) {
		SCALE = sCALE;
	}
	public float getScoreValue(){
		return 5.0f;
	}

	public String getScoreDisplay() {
		return scoreDisplay;
	}

	public void setScoreDisplay(String scoreDisplay) {
		this.scoreDisplay = scoreDisplay;
	}

	public boolean getDrawRect() {
		return DrawRect;
	}

	public void setDrawRect(boolean drawRect) {
		DrawRect = drawRect;
	}

	public int[] getTouchLocation() {
		return touchLocation;
	}

	public void setTouchLocation(int[] touchLocation) {
		this.touchLocation = touchLocation;
	}


}
