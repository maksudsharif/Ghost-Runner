package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Ghost extends Entity {

	public Ghost() {
		super();
	}

	public Ghost(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.pos_x = (int) (Math.random() * gameView.getWidthPixels());
		this.pos_y = 0;

		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
	}

	@Override
	public void draw(Canvas c) {
		// update the ghosts position
		this.pos_y += 5;
		// set the ghost's position based on updated values
		this.rect.set((int) pos_x - 25, (int) pos_y - 25, 25 + (int) pos_x,
				25 + (int) pos_y);// this sets the size of the rectangle
		c.drawBitmap(bm, pos_x, pos_y, p);

	}

}
