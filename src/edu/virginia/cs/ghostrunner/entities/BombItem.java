package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class BombItem extends Item {
	Rect innerRect;
	private Paint p2;
	public BombItem(float x, float y, GameView gameView) {
		super(x, y, gameView);
		innerRect = new Rect();
		this.p = new Paint();
		this.p.setColor(Color.RED);
		this.p.setStyle(Style.FILL);
		this.p2 = new Paint();
		this.p.setColor(Color.BLACK);
		this.p.setStyle(Style.STROKE);
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
//		this.innerRect.set(this.rect);
		innerRect.inset((int) (this.rect.width() * .2), (int) (this.rect.height() * .2));
		c.drawRect(this.rect, this.p);
		c.drawRect(this.innerRect, this.p2);

		// c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
		// * Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
		// * Entity.SCALE), p);

	}

	@Override
	public void intersected() {
		for (Entity e : gameView.getSynced()) {
			gameView.getGhosts().remove(e);
			gameView.setCurrentScore(gameView.getCurrentScore() + 10);
		}
		for (Entity e : gameView.getItems()) {
			gameView.getItems().remove(e);
		}
		/*
		 * TODO: DISPLAY SOME MESSAGE LIKE "BOMB"
		 */
	}
}
