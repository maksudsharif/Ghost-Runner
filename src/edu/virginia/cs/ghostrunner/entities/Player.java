package edu.virginia.cs.ghostrunner.entities;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Rect;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.virginia.cs.ghostrunner.R;
import edu.virginia.cs.ghostrunner.views.GameView;

public class Player extends Entity {
	protected static double SCALE = 0.035;

	public Player() { // Don't call this constructor
		super();
	}

	public Player(float x, float y, GameView gameView) {
		super(x, y, gameView);
		this.p = new Paint();
		this.p.setColor(Color.GRAY);
		this.p.setStyle(Style.FILL);
		
		bm = BitmapFactory.decodeResource(gameView.getContext().getResources(),
				R.drawable.ic_launcher);

	}

	@Override
	public void draw(Canvas c) {
		this.rect.set((int) pos_x
				- (int) (gameView.getWidthPixels() * Player.SCALE), (int) pos_y
				- (int) (gameView.getWidthPixels() * Player.SCALE),
				(int) (gameView.getWidthPixels() * Player.SCALE) + (int) pos_x,
				(int) (gameView.getWidthPixels() * Player.SCALE) + (int) pos_y);

		 c.drawRect(this.rect, this.p);
		
//		c.drawBitmap(bm, (float) (pos_x - gameView.getWidthPixels()
//				* Entity.SCALE), (float) (pos_y - gameView.getWidthPixels()
//				* Entity.SCALE), p);

	}
}
