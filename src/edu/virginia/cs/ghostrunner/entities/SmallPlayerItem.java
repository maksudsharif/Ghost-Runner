package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class SmallPlayerItem extends Item {
	
	private boolean hasBeenIntersected;
	Rect innerRect;
	private Paint p2;
	
	public SmallPlayerItem(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.p = new Paint();
		this.p.setColor(Color.WHITE);
		this.p.setStyle(Style.FILL);
		
		this.p2 = new Paint();
		this.p2.setColor(Color.BLACK);
		this.p2.setStyle(Style.FILL);
		
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);

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
		
//		c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
//				* Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
//				* Entity.SCALE), p);

	}
	@Override
	public void intersected() {
		//progressive implementation *note* can't remove
		Player.setSCALE(.98 * Player.getSCALE());
		// hard coded implementation
		//Player.SCALE = .030;
		this.hasBeenIntersected = true;
	}
	
	public boolean getIntersected() {
		return this.hasBeenIntersected;
	}
}
