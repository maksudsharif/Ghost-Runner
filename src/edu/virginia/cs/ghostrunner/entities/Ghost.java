package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Ghost extends Entity {

	public Ghost() {
		super();
	}

	public Ghost(GameView gameView) {
		this.gameView = gameView;
		Log.v("GHOST_CONSTRUCTOR", "DM WIDTH: "+gameView.getWidthPixels());
		int val = (int) (Math.random() * gameView.getWidthPixels());
		Log.v("GHOST_CONSTRUCTOR", "GHOST X PRESOLVE: "+val);
		this.pos_x = val;
		this.pos_y = 0;

		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);
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
		this.pos_y += 10;
		// set the ghost's position based on updated values
		this.rect.set((int) pos_x - 50, (int) pos_y - 50, 50 + (int) pos_x,
				50 + (int) pos_y);// this sets the size of the rectangle
		c.drawBitmap(bm, pos_x, pos_y, p);

	}

}
