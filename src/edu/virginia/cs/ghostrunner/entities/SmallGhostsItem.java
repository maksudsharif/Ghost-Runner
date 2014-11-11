package edu.virginia.cs.ghostrunner.entities;

import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SmallGhostsItem extends Item {
	private int timer;

	public SmallGhostsItem(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.pos_x = (int) (Math.random() * gameView.getWidthPixels());
		this.pos_y = 0;
		this.timer = 1000;
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
		this.p = new Paint(Color.WHITE);
	}

	@Override
	public void draw(Canvas c) {
		// For now, just update speed same as ghosts.
		this.pos_y += (int) (gameView.getWidthPixels() * Entity.SPEED)
				* gameView.getGhostspeedconstant();
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Entity.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);
		c.drawRect(this.rect, this.p);
	}

}
