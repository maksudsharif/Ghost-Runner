package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class DoubleScoreItem extends Item {
	
	private boolean hasBeenIntersected;
	
	public DoubleScoreItem(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.p = new Paint();
		this.p.setColor(Color.argb(256, 47, 174, 67));
		this.p.setStyle(Style.FILL);

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

		// c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
		// * Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
		// * Entity.SCALE), p);

	}

	@Override
	public void intersected() {
		if (gameView.getScoreConstant() < 2)
			gameView.setScoreConstant(gameView.getScoreConstant() * 2);
		this.hasBeenIntersected = true;
	}
	
	public boolean getIntersected() {
		return this.hasBeenIntersected;
	}
}
