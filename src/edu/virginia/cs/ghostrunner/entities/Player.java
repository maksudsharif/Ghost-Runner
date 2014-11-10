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
		rect.set((int) pos_x - 25, (int) pos_y - 25, 25 + (int) pos_x,
				25 + (int) pos_y);
		c.drawBitmap(bm, pos_x, pos_y, p);

	}
}
