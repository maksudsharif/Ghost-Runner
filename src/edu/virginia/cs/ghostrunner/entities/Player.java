package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
<<<<<<< Updated upstream
import android.graphics.Rect;
=======
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
>>>>>>> Stashed changes
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Player extends Entity {

	public Player() { // Don't call this constructor
		super();
	}

	public Player(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.p = new Paint(Color.GRAY);
		this.p.setStyle(Style.FILL);
		this.p.setStrokeWidth(3);
		
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);

	}

	@Override
	public void draw(Canvas c) {
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Entity.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Entity.SCALE),
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Entity.SCALE) + (int) pos_y);
<<<<<<< Updated upstream
		 c.drawRect(rect, p);
		/*
		 * Uncomment below here and comment the line above here to try fixed version
		 */
		/*this.rect.set((int) pos_x, (int) pos_y,
				(int) (pos_x + bm.getWidth()), (int) (pos_y + bm.getHeight()));
		c.drawRect(rect, p);
		c.drawBitmap(bm, pos_x, pos_y, p);*/
=======
		c.drawRect(this.rect, this.p);
//		c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
//				* Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
//				* Entity.SCALE), p);
>>>>>>> Stashed changes
	}
}
