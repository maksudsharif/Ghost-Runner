package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Player extends Entity {

	public Player() { // Don't call this constructor
		super();
	}

	public Player(float x, float y, GameView gameView) {
		super(x, y, gameView);
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);

	}

	@Override
	public void draw(Canvas c) {
		this.rect.set(
				(int) pos_x - (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) pos_y - (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);
		c.drawRect(rect, p);
//		c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
//				* Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
//				* Entity.SCALE), p);
	}
}
