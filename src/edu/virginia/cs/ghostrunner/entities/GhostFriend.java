package edu.virginia.cs.ghostrunner.entities;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.util.Log;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class GhostFriend extends Item {
	Rect innerRect;
	private boolean acitvated = false;
	
	private Paint p2;
	
	public GhostFriend(float x, float y, GameView gameView) {
		super(x, y, gameView);
		innerRect = new Rect();
		this.p = new Paint();
		this.p.setColor(Color.parseColor("#FF8800"));
		this.p.setStyle(Style.FILL);
		this.p2 = new Paint();
		this.p2.setColor(Color.CYAN);
		this.p2.setStyle(Style.FILL);
	}
	
	public boolean isAcitvated() {
		return acitvated;
	}

	public void setAcitvated(boolean acitvated) {
		this.acitvated = acitvated;
	}


	@Override
	public void draw(Canvas c) {
		this.pos_y += (int) (gameView.getWidthPixels() * Entity.SPEED)
				* gameView.getGhostSpeedConstant();
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Entity.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);
		c.drawRect(this.rect, this.p);
		this.innerRect = this.rect;
		innerRect.inset((int) (this.rect.width() * .1), (int) (this.rect.width()* .1));
		c.drawRect(this.innerRect, this.p2);
		// c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
		// * Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
		// * Entity.SCALE), p);

	}
	public void drawActivated(Canvas c) {
		
		if (this.acitvated == true) {
			c.drawCircle(gameView.getPlayer().pos_x, gameView.getPlayer().pos_y, (int)(gameView.getPlayer().getRect().width() / 1.1), this.p);
		}
		
		
	}

	@Override
	public void intersected() {
		this.acitvated = true;
		this.p.setStyle(Style.STROKE);
	}
}
